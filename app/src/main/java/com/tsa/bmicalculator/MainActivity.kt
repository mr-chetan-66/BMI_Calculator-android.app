package com.tsa.bmicalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Welcome to BMI Calculator!", Toast.LENGTH_SHORT).show()
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstTime", false)
            editor.apply()
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            loadFragment(HomeFragment())
        }

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_about -> AboutFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_setting -> SettingFragment()
                else -> HomeFragment()
            }
            loadFragment(selectedFragment)
            true
        }

        handleBackPress()
    }

    private var homeFragment: HomeFragment? = null

    private fun loadFragment(newFragment: Fragment) {
        val fragmentToLoad: Fragment = if (newFragment is HomeFragment && homeFragment != null) {
            homeFragment!! // Safe to use !! here because we checked for null
        } else {
            if (newFragment is HomeFragment) homeFragment = newFragment
            newFragment // This is guaranteed to be non-null
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.fragment_container, fragmentToLoad)
            .commit()
    }

    private fun handleBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()

                // If back button is pressed within 2 seconds, exit the app
                if (currentTime - backPressedTime <= 2000) {
                    finish() // Exit the app
                } else {
                    // Show a Toast asking for confirmation
                    Toast.makeText(
                        this@MainActivity,
                        "Press back again to exit",
                        Toast.LENGTH_SHORT
                    ).show()
                    backPressedTime = currentTime
                }
            }
        })
    }
}
