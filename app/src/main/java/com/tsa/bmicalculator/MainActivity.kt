package com.tsa.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Load the default fragment on startup
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_about -> selectedFragment = AboutFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
                R.id.nav_setting -> selectedFragment=SettingFragment()
            }

            loadFragment(selectedFragment)
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        return if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right, // enter animation
                    R.anim.slide_out_left, // exit animation
                    R.anim.slide_in_left,  // popEnter animation (when coming back)
                    R.anim.slide_out_right
                )
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
            true
        } else {
            false
        }
    }
}
