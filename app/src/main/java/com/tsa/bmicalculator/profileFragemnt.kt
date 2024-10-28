package com.tsa.bmicalculator

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var greetingTextView: TextView
    private lateinit var currentBmiTextView: TextView
    private lateinit var requiredChangeTextView: TextView
    private lateinit var healthTipsTextView: TextView
    private lateinit var lastCheckDateTextView: TextView
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var typewriterJob: Job? = null
    private val texts = listOf(
        "Hii Chetan !"
    )

    private val dailyFacts = listOf(
        "Drink plenty of water every day!",
        "Regular exercise can improve mental health.",
        "Getting enough sleep is essential for overall health.",
        "A balanced diet keeps you fit and energetic.",
        "Stretch daily to maintain flexibility."
    )
    private lateinit var factTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        greetingTextView = view.findViewById(R.id.account_name)
        currentBmiTextView = view.findViewById(R.id.current_bmi_no)
        requiredChangeTextView = view.findViewById(R.id.weight_change_no)
        healthTipsTextView = view.findViewById(R.id.health_tips_content)
        factTextView = view.findViewById(R.id.news_content) // Replace with your TextView ID
        lastCheckDateTextView = view.findViewById(R.id.latest_date)

        startTypewriterEffect()

        // Show a new daily fact
        displayBmiInfo()
        showNewDailyFact()

        return view
    }

    private fun startTypewriterEffect() {
        greetingTextView.text = ""
        var textIndex = 0

        // Cancel any existing job if running
        typewriterJob?.cancel()

        // Start a new coroutine
        typewriterJob = lifecycleScope.launch {
            while (true) {
                val text = texts[textIndex]
                typeText(text)

                // Move to the next text and wrap around if needed
                textIndex = (textIndex + 1) % texts.size
                delay(1500) // Delay between each full text display (adjust as needed)
            }
        }
    }

    private suspend fun typeText(text: String) {
        greetingTextView.text = "" // Clear previous text
        for (char in text) {
            greetingTextView.append(char.toString())
            delay(130) // Adjust delay to control typing speed
        }
    }

    private fun displayBmiInfo() {
        // Retrieve BMI value calculated from Home Fragment (or placeholder for now)
        currentBMI()
        weightChange()
        lastDate()
    }

    private fun lastDate() {
        sharedViewModel.lastCheckDate.observe(viewLifecycleOwner) { date ->
            if (date.isNotBlank()) {
                lastCheckDateTextView.setTextColor(Color.WHITE)
                lastCheckDateTextView.text = "$date"
            } else {
                lastCheckDateTextView.text = "XX-XX-XXXX"
            }
        }
    }

    private fun weightChange() {
        sharedViewModel.weightNeeded.observe(viewLifecycleOwner) { change ->
            if (change != null) {
                if (change == 99.99) {
                    requiredChangeTextView.text = "N/A"
                } else if (change >= 0) {
                    requiredChangeTextView.setTextColor(Color.GREEN)
                    animateNumber(requiredChangeTextView, 0f, change.toFloat())
                } else {
                    requiredChangeTextView.setTextColor(Color.RED)
                    animateNumber(requiredChangeTextView, 0f, change.toFloat())
                }
            } else {
                requiredChangeTextView.text = "N/A"
            }
        }
    }

    private fun currentBMI() {
        sharedViewModel.bmi.observe(viewLifecycleOwner) { bmiValue ->
            displayHealthTips(bmiValue)
            if (bmiValue != null) {
                when {
                    bmiValue < 18.4 -> {
                        currentBmiTextView.setTextColor(Color.RED)
                    }

                    bmiValue > 24.9 -> {
                        currentBmiTextView.setTextColor(Color.RED)
                    }

                    else -> {
                        currentBmiTextView.setTextColor(Color.GREEN)
                    }
                }
                currentBmiTextView.text = "%.2f".format(bmiValue)
            } else {
                currentBmiTextView.text = "N/A"
            }
        }
    }

    private fun displayHealthTips(currentBmi: Double?) {
        val healthTip = when {
            currentBmi == null -> "Calculate BMI to receive personalized tips."

            currentBmi < 18.5 -> """
            • Underweight: Increase calorie intake.
              - Focus on nutrient-dense foods (nuts, avocados).
              - Add protein (lean meats, dairy, legumes).
              - Eat frequent small meals and snacks.
              - Consider smoothies or shakes for extra calories.
        """.trimIndent()

            currentBmi > 24.9 -> """
            • Healthy weight: Stay active.
              - Maintain a balanced diet with fruits and vegetables.
              - Incorporate strength training to build muscle.
              - Stay hydrated and avoid excessive sugar.
              - Regular check-ups to monitor health.
        """.trimIndent()

            currentBmi < 29.9 -> """
            • Overweight: Exercise regularly.
              - Aim for at least 150 minutes of moderate exercise weekly.
              - Focus on portion control and mindful eating.
              - Include more whole foods (grains, fruits, veggies).
              - Limit processed foods and sugary drinks.
        """.trimIndent()

            else -> """
            • Obese: Seek professional advice.
              - Consult a healthcare provider for a tailored plan.
              - Consider a balanced diet and regular exercise.
              - Track food intake and activity levels.
              - Support groups can help with accountability.
        """.trimIndent()
        }
        healthTipsTextView.setTextColor(Color.WHITE)
        healthTipsTextView.text = healthTip
    }


    private fun showNewDailyFact() {
        // Get last shown index from SharedPreferences
        val sharedPrefs = requireContext().getSharedPreferences("DailyFacts", Context.MODE_PRIVATE)
        val lastFactIndex = sharedPrefs.getInt("last_fact_index", -1)

        // Calculate next fact index
        val nextFactIndex = (lastFactIndex + 1) % dailyFacts.size

        // Show the fact and save the new index
        factTextView.text = dailyFacts[nextFactIndex]
        sharedPrefs.edit().putInt("last_fact_index", nextFactIndex).apply()
    }

    private fun animateNumber(textView: TextView, start: Float, end: Float) {
        val animator = ValueAnimator.ofFloat(start, end)
        animator.duration = 1100 // 1 second animation duration
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            if (end >= 0) {
                textView.text =
                    String.format("+%.2f kg", animatedValue) // Format to 1 decimal place
            } else {
                textView.text = String.format("%.2f kg", animatedValue) // Format to 1 decimal place
            }
        }
        animator.start()
    }

    override fun onPause() {
        super.onPause()
        typewriterJob?.cancel() // Stop typing effect
    }

}
