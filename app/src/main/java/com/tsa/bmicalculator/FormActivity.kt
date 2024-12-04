package com.tsa.bmicalculator

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.content.Intent
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)  // Replace with your actual layout

        val etName = findViewById<EditText>(R.id.etName)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val gender = when (rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> "Unknown"
            }

            if (name.isEmpty()) {
                etName.error = "Name is required"
                return@setOnClickListener
            }

            // Save name and gender to SharedPreferences
            val sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("gender", gender)
            editor.apply()

            // Navigate to MainActivity after saving the data
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Optionally, finish FormActivity so the user can't go back
        }
    }
}

