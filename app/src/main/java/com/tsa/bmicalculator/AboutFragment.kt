package com.tsa.bmicalculator

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dynamically apply text content and styles
        applyTextStyles(view)

        // Setup expandable sections
        val expandableSections = listOf(
            R.id.bmi_formula_header to R.id.bmi_formula_content,
            R.id.faq_bmi_header to R.id.faq_content,
            R.id.improve_bmi_header to R.id.improve_bmi_content,
            R.id.health_risks_bmi_header to R.id.health_risks_bmi_content,
            R.id.side_effects_header to R.id.side_effects_content,
            R.id.history_bmi_header to R.id.history_bmi_content,
            R.id.developer_info_header to R.id.developer_info_content,
            R.id.terms_conditions_header to R.id.terms_conditions_content,
            R.id.legal_info_header to R.id.legal_info_content,
            R.id.privacy_policy_header to R.id.privacy_policy_content,
            R.id.copyright_header to R.id.copyright_content
        )

        expandableSections.forEach { (headerId, contentId) ->
            setupExpandableSection(view, headerId, contentId)
        }
    }

    // Helper function to handle expandable sections
    private fun setupExpandableSection(view: View, headerId: Int, contentId: Int) {
        val headerButton = view.findViewById<MaterialButton>(headerId)
        val contentLayout = view.findViewById<View>(contentId)

        // Initially hide the content
        contentLayout.isVisible = false

        headerButton.setOnClickListener {
            // Toggle visibility with smooth animation
            val isVisibleNow = !contentLayout.isVisible
            contentLayout.animate()
                .alpha(if (isVisibleNow) 1f else 0f)
                .setDuration(300)
                .withEndAction { contentLayout.isVisible = isVisibleNow }
        }
    }

    // Function to apply HTML content dynamically to TextViews
    private fun applyTextStyles(view: View) {
        val sectionsContent = mapOf(
            R.id.bmi_formula_content to """<h2>BMI Formula</h2>
            <p>
            <b>Body Mass Index (BMI)</b> is a simple formula used to calculate whether a person has a healthy weight for their height.<br/>
                    Formula: <b>BMI = Weight in kilograms / (Height in meters)^2</b>
        </p>
        """,
            R.id.faq_content to """
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
            """,
            R.id.improve_bmi_content to """
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
            """,
            R.id.health_risks_bmi_content to """
                 <h2>BMI Risks</h2>
        <p>A BMI below 18.5 indicates underweight and may lead to nutrient deficiencies.</p>
        <p>A BMI above 24.9 indicates overweight or obesity, which can lead to conditions like heart disease, diabetes, and hypertension.</p>
            """,
            R.id.side_effects_content to """
                <h2>Side Effects</h2>
                <p>Includes weak immunity for low BMI and diabetes for high BMI.</p>
            """,
            R.id.history_bmi_content to """
                <h2>History</h2>
        The <b>Body Mass Index (BMI)</b> was developed by Adolphe Quetelet in the early 19th century as a tool to assess the health of populations based on their physical measurements.
            """,
            R.id.developer_info_content to """
                <h2>Developer Information</h2>
        <p><b>Developed by:</b> Chetan Awari</p>
        <p>Passionate Android Developer with expertise in Kotlin and mobile app development.</p>
        <p>Always eager to learn and implement new technologies to improve user experience.</p>
            """,
            R.id.terms_conditions_content to """
               <h3>Terms and Conditions</h3>
        By using this app, you agree to the terms and conditions outlined.<br/>
        <i>This app provides general health information</i> and is not a substitute for medical advice.
            """,
            R.id.legal_info_content to """
                <<h2>Legal Information</h2>
        <p>By using this app, you agree to our <b>Terms and Conditions</b> and <b>Privacy Policy</b>.</p>
        <p>This app does not collect personal data and provides general health information. It is <i>not a substitute</i> for professional medical advice.</p>
        <p>Refer to our full privacy policy for further details on data handling.</p>
            """,
            R.id.privacy_policy_content to """
                 <h3>Privacy Statement</h3>
        We respect your privacy. This app does <b>not collect any personal data</b> from users.<br/>
        Please refer to our <i>full privacy policy</i> for more details.
            """,
            R.id.copyright_content to """
                <h3>Copyright Notice</h3>
        © 2024 <b>Chetan Awari</b>. All rights reserved.<br/>
        This app is the intellectual property of the developer and <b>cannot be distributed without permission</b>.
            """
        )

        sectionsContent.forEach { (viewId, htmlContent) ->
            view.findViewById<TextView>(viewId)?.apply {
                text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_COMPACT)
            }
        }
    }
}
