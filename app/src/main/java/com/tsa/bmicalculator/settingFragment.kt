package com.tsa.bmicalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

class SettingFragment : Fragment() {

    private lateinit var dataPrivacyLayout: View
    private lateinit var privacyDetailsTextView: TextView
    private lateinit var dataPrivacyArrow: ImageView

    private lateinit var termsAndConditionsLayout: View
    private lateinit var termsAndConditionsTextView: TextView
    private lateinit var termsAndConditionsArrow: ImageView

    private lateinit var helpCenterLayout: View
    private lateinit var helpCenterTextView: TextView
    private lateinit var helpCenterArrow: ImageView

    private lateinit var contactSupportLayout: View
    private lateinit var contactSupportTextView: TextView
    private lateinit var contactSupportArrow: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize all sections dynamically
        initSection(
            view, R.id.dataPrivacyLayout, R.id.privacyDetailsTextView, R.id.dataPrivacyArrow
        )
        initSection(
            view, R.id.termsAndConditionsLayout, R.id.termsAndConditionsTextView, R.id.termsAndConditionsArrow
        )
        initSection(
            view, R.id.helpCenterLayout, R.id.helpCenterTextView, R.id.helpCenterArrow
        )
        initSection(
            view, R.id.contactSupportLayout, R.id.contactSupportTextView, R.id.contactSupportArrow
        )
    }

    // Helper function to initialize a section with layout, text, and arrow
    private fun initSection(view: View, layoutId: Int, textViewId: Int, arrowId: Int) {
        val layout = view.findViewById<View>(layoutId)
        val textView = view.findViewById<TextView>(textViewId)
        val arrow = view.findViewById<ImageView>(arrowId)

        layout.setOnClickListener {
            toggleVisibilityWithRotation(textView, arrow)
        }
    }

    // Function to toggle visibility and rotate the arrow
    private fun toggleVisibilityWithRotation(view: View, arrow: ImageView) {
        view.isVisible = !view.isVisible

        val rotationAngle = if (view.isVisible) 90f else 0f
        arrow.animate().rotation(rotationAngle).setDuration(200).start()
    }
}
