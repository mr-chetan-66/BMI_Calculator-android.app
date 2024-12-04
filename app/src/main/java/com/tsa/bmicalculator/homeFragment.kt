package com.tsa.bmicalculator

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.tsa.bmicalculator.databinding.FragmentHomeBinding
import java.text.DecimalFormat


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val imageSwitchViewModel: ImageSwitchViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupObservers()
        setupListeners()
        return binding.root
    }

    private fun setupObservers() {
        imageSwitchViewModel.imageResource.observe(viewLifecycleOwner) { imageRes ->
            fadeAndSwitchImage(imageRes)
        }
    }

    private fun setupListeners() {
        binding.calculatebutton.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val weightInput = binding.weighttext.text.toString()
        val feetInput = binding.feettext.text.toString()
        val inchesInput = binding.inchtext.text.toString()

        if (weightInput.isBlank() || feetInput.isBlank() || inchesInput.isBlank()) {
            Toast.makeText(requireContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
            return
        }

        val weight = weightInput.toIntOrNull()
        val feet = feetInput.toIntOrNull()
        val inches = inchesInput.toIntOrNull()

        if (weight == null || feet == null || inches == null || weight <= 0 || feet < 0 || inches < 0) {
            Toast.makeText(requireContext(), "Invalid Input Format.", Toast.LENGTH_SHORT).show()
            return
        }

        val totalInches = (feet * 12) + inches
        val heightMeter = totalInches * 0.0254
        val bmi = weight / (heightMeter * heightMeter)

        if (bmi != -1.0) {
            val age = binding.agetext.text.toString().toIntOrNull()

            if (age == null || age <= 0 || age > 110) {
                Toast.makeText(requireContext(), "Please enter a valid age.", Toast.LENGTH_SHORT).show()
            } else {
                if (age < 18) {
                    displayMinor(bmi)
                } else {
                    calculateWeightToGainOrLose(bmi, heightMeter, weight)
                }
            }
        }
    }
    private fun formatBMI(bmi: Double): String {
        val decimalFormat = DecimalFormat("0.00")
        return decimalFormat.format(bmi)
    }

    private fun calculateWeightToGainOrLose(bmi: Double, heightMeters: Double, weightKg: Int) {
        val bmiResult = formatBMI(bmi)
        imageSwitchViewModel.updateImageBasedOnBmi(bmiResult.toFloat())
        val normalBmiMin = 18.5
        val normalBmiMax = 24.9
        val resultString :String
        val weightForNormalBmiMin = normalBmiMin * (heightMeters * heightMeters)
        val weightForNormalBmiMax = normalBmiMax * (heightMeters * heightMeters)
        when {
            bmi < normalBmiMin -> {
                val weightToGain = weightForNormalBmiMin - weightKg
                binding.resultview.setTextColor(Color.RED)
                resultString="You are underweight.\n%.2f kg gain to reach a healthy BMI.".format(weightToGain)
                sharedViewModel.setBmi(bmi,weightToGain)
            }

            bmi > normalBmiMax -> {
                val weightToLose = weightKg - weightForNormalBmiMax
                binding.resultview.setTextColor(Color.RED)
                resultString="You are overweight.\n%.2f kg lose to reach a normal BMI.".format(weightToLose)
                sharedViewModel.setBmi(bmi,(-1)*weightToLose)
            }

            else -> {
                binding.resultview.setTextColor(Color.GREEN)
                resultString="$bmiResult - BMI is in the normal range!\nYou have a healthy Weight"
                sharedViewModel.setBmi(bmi,99.99)
            }
        }
        binding.resultview.text = resultString
    }

    private fun displayMinor(bmi: Double) {
        val bmiResult = formatBMI(bmi)
        imageSwitchViewModel.underage()
        val resultString = when {
            binding.maleradio.isChecked -> "$bmiResult - As you are under 18, please consult your doctor for a healthy range for boys!"
            binding.femaleradio.isChecked -> "$bmiResult - As you are under 18, please consult your doctor for a healthy range for girls!"
            else -> {
                Toast.makeText(requireContext(), "Please select your gender.", Toast.LENGTH_SHORT).show()
                return
            }
        }
        binding.resultview.setTextColor(Color.RED)
        binding.resultview.text = resultString
    }

    private fun fadeAndSwitchImage(imageRes: Int) {
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 250 }
        binding.imageview.startAnimation(fadeOut)
        Glide.with(binding.imageview.context)
            .load(imageRes)
            .into(binding.imageview)
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 250 }
        binding.imageview.startAnimation(fadeIn)
    }

    private fun cleanupImageSwitching() {
        imageSwitchViewModel.cleanup()
    }

    override fun onPause() {
        super.onPause()
        cleanupImageSwitching()
    }

    override fun onResume() {
        super.onResume()
        if (!imageSwitchViewModel.isImageSwitchingActive()) {
            imageSwitchViewModel.startImageSwitching()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanupImageSwitching()
    }
}

class ImageSwitchViewModel : ViewModel() {
    private val handler = Handler(Looper.getMainLooper())
    private var currentImageIndex = 0
    private val _imageResource = MutableLiveData<Int>()
    val imageResource: LiveData<Int> = _imageResource
    private var isImageSwitchingStarted = false

    // Images for switching
    private val images = arrayOf(
        R.drawable.girl_yoga_profile,
        R.drawable.girl_workout_profile,
        R.drawable.boy_yoga_profile,
        R.drawable.bmi_range,
        R.drawable.bmi_scale,
        R.drawable.boy_getting_ready,
        R.drawable.weight
    )

    init {
        startImageSwitching()
    }

    fun startImageSwitching() {
        if (!isImageSwitchingStarted) {
            isImageSwitchingStarted = true
            switchImages()
        }
    }

    private fun switchImages() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                _imageResource.value = images[currentImageIndex]
                currentImageIndex = (currentImageIndex + 1) % images.size
                handler.postDelayed(this, 2000)
            }
        }, 2000)
    }

    fun stopImageSwitching() {
        isImageSwitchingStarted = false
        handler.removeCallbacksAndMessages(null)
    }

    fun resumeImageSwitchingAfterDelay(delayMillis: Long) {
        handler.postDelayed({
            startImageSwitching() // Restart the image switching after the delay
        }, delayMillis)
    }
    fun underage(){
        _imageResource.value = R.drawable.underage // Underweight
        stopImageSwitching() // Stop switching for underweight
        resumeImageSwitchingAfterDelay(2500)
    }

    fun updateImageBasedOnBmi(bmi: Float) {
        when {
            bmi < 18.5 -> {
                _imageResource.value = R.drawable.healthy_food_image // Underweight
                stopImageSwitching() // Stop switching for underweight
                resumeImageSwitchingAfterDelay(2500) // Resume after 5 seconds
            }
            bmi in 18.5..24.9 -> {
                // Normal BMI, continue switching images
                startImageSwitching() // Ensure switching continues for normal BMI
                _imageResource.value = images[currentImageIndex] // Set the current image
            }
            else-> {
                _imageResource.value = R.drawable.gym_direction_profile// Overweight
                stopImageSwitching() // Stop switching for overweight
                resumeImageSwitchingAfterDelay(2500) // Resume after 5 seconds
            }
        }
    }

    fun isImageSwitchingActive(): Boolean {
        return isImageSwitchingStarted
    }

    fun cleanup() {
        handler.removeCallbacksAndMessages(null)
    }

    override fun onCleared() {
        super.onCleared()
        cleanup()
    }
}