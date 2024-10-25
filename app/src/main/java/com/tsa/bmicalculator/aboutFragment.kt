package com.tsa.bmicalculator

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textStyle(view)

        // Call setup function for each button and its corresponding layout
        setupExpandableSection(view, R.id.bmi_formula_header, R.id.bmi_formula_content)
        setupExpandableSection(view, R.id.faq_bmi_header, R.id.faq_content)
        setupExpandableSection(view, R.id.improve_bmi_header, R.id.improve_bmi_content)
        setupExpandableSection(view, R.id.health_risks_bmi_header, R.id.health_risks_bmi_content)
        setupExpandableSection(view, R.id.side_effects_header, R.id.side_effects_content)
        setupExpandableSection(view, R.id.history_bmi_header, R.id.history_bmi_content)
        setupExpandableSection(view, R.id.developer_info_header, R.id.developer_info_content)
        setupExpandableSection(view, R.id.terms_conditions_header, R.id.terms_conditions_content)
        setupExpandableSection(view, R.id.legal_info_header, R.id.legal_info_content)
        setupExpandableSection(view, R.id.privacy_policy_header, R.id.privacy_policy_content)
        setupExpandableSection(view, R.id.copyright_header, R.id.copyright_content)
    }

    // Helper function to setup each expandable section using Button
    private fun setupExpandableSection(view: View, buttonId: Int, contentId: Int) {
        val button = view.findViewById<Button>(buttonId)
        val content = view.findViewById<View>(contentId) // Can be TextView or any other layout

        // Set initial visibility of content to GONE
        content.visibility = View.GONE

        // Set the button's onClickListener to toggle visibility
        button.setOnClickListener {
            if (content.visibility == View.GONE) {
                content.visibility = View.VISIBLE
                val fadeIn = AlphaAnimation(0f, 1f)
                fadeIn.duration = 900
                content.startAnimation(fadeIn)
            } else {
                val fadeOut = AlphaAnimation(1f, 0f)
                fadeOut.duration = 150
                content.startAnimation(fadeOut)
                content.visibility = View.GONE
            }
        }
    }

    private fun textStyle(view: View) {

        //BMI FORMULA------------------------------------------
        val bmiformula = """
        <h2>BMI Formula</h2>
        <p>
            <b>Body Mass Index (BMI)</b> is a simple formula used to calculate whether a person has a healthy weight for their height.<br/>
            Formula: <b>BMI = Weight in kilograms / (Height in meters)^2</b>
        </p>
        """.trimIndent()

        val bmiformulaContent: TextView = view.findViewById(R.id.bmi_formula_content)
        bmiformulaContent.text = Html.fromHtml(bmiformula, Html.FROM_HTML_MODE_LEGACY)


        //FAQs-------------------------------------------------
        val bmifaqs = """
        <h2>BMI FAQs</h2>
        <ol>
            <li>
                <b>How <i>accurate</i> is <b>BMI</b>?</b><br/>
                ► BMI is a general indicator and may not be accurate for athletes.
            </li>
            <li>
                <b>What is a <b>normal BMI range</b>?</b><br/>
                ► The normal BMI range is between <i><b>18.5 and 24.9</b></i>.
            </li>
        </ol>
    """.trimIndent()

        val bmifaqcontent: TextView = view.findViewById(R.id.faq_content)
        bmifaqcontent.text = Html.fromHtml(bmifaqs, Html.FROM_HTML_MODE_LEGACY)


        // IMPROVE BMI------------------------------------------
        val bmiImprovementTips = """
        <h2>How to Improve BMI</h2>
        <p>To improve BMI, focus on diet, exercise, and healthy habits.</p>

        <h3>1. Balanced Diet</h3>
        <ul>
            <li><b>Whole grains:</b> Choose oats, brown rice.</li>
            <li><b>Protein:</b> Lean meats, beans for muscle.</li>
            <li><b>Healthy fats:</b> Avocado, nuts, olive oil.</li>
        </ul>

        <h3>2. Regular Exercise</h3>
        <ul>
            <li><b>Cardio:</b> Walk, run, or cycle daily.</li>
            <li><b>Strength:</b> Build muscle to boost metabolism.</li>
        </ul>

        <h3>3. Lifestyle Habits</h3>
        <ul>
            <li><b>Hydrate:</b> Drink water to aid digestion.</li>
            <li><b>Sleep:</b> 7-8 hours nightly to regulate hunger.</li>
        </ul>
        """.trimIndent()

        val improvebmi: TextView = view.findViewById(R.id.improve_bmi_content)
        improvebmi.text = Html.fromHtml(bmiImprovementTips, Html.FROM_HTML_MODE_LEGACY)


        //SIDE EFFECT----------------------------------------
        val lowBmiHighBmiInfo = """
        <h2>BMI Information</h2>
        <h3>Low BMI</h3>
        <p>Weak immune system, nutrient deficiencies, etc.</p>
        <h3>High BMI</h3>
        <p>Heart disease, diabetes, high blood pressure, etc.</p>
        """.trimIndent()

        val lowBmiHighBmiTextView: TextView = view.findViewById(R.id.side_effects_content)
        lowBmiHighBmiTextView.text = Html.fromHtml(lowBmiHighBmiInfo, Html.FROM_HTML_MODE_LEGACY)


        //RISK--------------------------------------------------
        val bmiRisks = """
        <h2>BMI Risks</h2>
        <p>A BMI below 18.5 indicates underweight and may lead to nutrient deficiencies.</p>
        <p>A BMI above 24.9 indicates overweight or obesity, which can lead to conditions like heart disease, diabetes, and hypertension.</p>
        """.trimIndent()

        val bmiRisksTextView: TextView = view.findViewById(R.id.health_risks_bmi_content)
        bmiRisksTextView.text = Html.fromHtml(bmiRisks, Html.FROM_HTML_MODE_LEGACY)


        // Privacy Statement---------------------------------------
        val privacyStatement = """
        <h3>Privacy Statement</h3>
        We respect your privacy. This app does <b>not collect any personal data</b> from users.<br/>
        Please refer to our <i>full privacy policy</i> for more details.
        """.trimIndent()

        val privacyTextView: TextView = view.findViewById(R.id.privacy_policy_content)
        privacyTextView.text = Html.fromHtml(privacyStatement, Html.FROM_HTML_MODE_LEGACY)


        // Copyright Notice----------------------------------------
        val copyrightNotice = """
        <h3>Copyright Notice</h3>
        © 2024 <b>Chetan Awari</b>. All rights reserved.<br/>
        This app is the intellectual property of the developer and <b>cannot be distributed without permission</b>.
        """.trimIndent()

        val copyrightTextView: TextView = view.findViewById(R.id.copyright_content)
        copyrightTextView.text = Html.fromHtml(copyrightNotice, Html.FROM_HTML_MODE_LEGACY)


        // Terms and Conditions Statement---------------------------
        val termsAndConditions = """
        <h3>Terms and Conditions</h3>
        By using this app, you agree to the terms and conditions outlined.<br/>
        <i>This app provides general health information</i> and is not a substitute for medical advice.
        """.trimIndent()

        val termsTextView: TextView = view.findViewById(R.id.terms_conditions_content)
        termsTextView.text = Html.fromHtml(termsAndConditions, Html.FROM_HTML_MODE_LEGACY)


        // BMI History Context------------------------------------------
        val bmiHistory = """
        <h3>About BMI</h3>
        The <b>Body Mass Index (BMI)</b> was developed by Adolphe Quetelet in the early 19th century as a tool to assess the health of populations based on their physical measurements.
        """.trimIndent()

        val bmiHistoryTextView: TextView = view.findViewById(R.id.history_bmi_content)
        bmiHistoryTextView.text = Html.fromHtml(bmiHistory, Html.FROM_HTML_MODE_LEGACY)


        //Developer Info-------------------------------------------------
        val developerInfo = """
        <h2>Developer Information</h2>
        <p><b>Developed by:</b> Chetan Awari</p>
        <p>Passionate Android Developer with expertise in Kotlin and mobile app development.</p>
        <p>Always eager to learn and implement new technologies to improve user experience.</p>
        """.trimIndent()

        val developerInfoContent: TextView = view.findViewById(R.id.developer_info_content)
        developerInfoContent.text = Html.fromHtml(developerInfo, Html.FROM_HTML_MODE_LEGACY)


        //legal Info---------------------------------------------
        val legalInfo = """
        <h2>Legal Information</h2>
        <p>By using this app, you agree to our <b>Terms and Conditions</b> and <b>Privacy Policy</b>.</p>
        <p>This app does not collect personal data and provides general health information. It is <i>not a substitute</i> for professional medical advice.</p>
        <p>Refer to our full privacy policy for further details on data handling.</p>
        """.trimIndent()

        val legalInfoContent: TextView = view.findViewById(R.id.legal_info_content)
        legalInfoContent.text = Html.fromHtml(legalInfo, Html.FROM_HTML_MODE_LEGACY)


    }
}





