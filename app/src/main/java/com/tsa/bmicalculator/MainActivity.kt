package com.tsa.bmicalculator

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Welcome to BMI Calculator!", Toast.LENGTH_SHORT).show()

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Load the default fragment on startup
        if (savedInstanceState == null) {
            loadFragment(HomeFragment(), addToBackStack = false)
        }

        // Sync bottom navigation selection when back stack changes
        supportFragmentManager.addOnBackStackChangedListener {
            syncBottomNavigationWithFragment()
        }

        // Bottom Navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment? = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_about -> AboutFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_setting -> SettingFragment()
                else -> null
            }

            if (selectedFragment != null) {
                // Flush backstack when navigating to HomeFragment
                if (item.itemId == R.id.nav_home) {
                    flushBackStack() // Clear the backstack
                    loadFragment(selectedFragment, addToBackStack = false) // Load HomeFragment without adding to backstack
                } else {
                    loadFragment(selectedFragment, addToBackStack = true) // Add other fragments to the backstack
                }
            }
            true
        }

        // Handle back button behavior with OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

                if (currentFragment is HomeFragment && supportFragmentManager.backStackEntryCount == 0) {
                    // Require double press to exit if on Home fragment
                    if (doubleBackToExitPressedOnce) {
                        finish() // Finish activity if double back pressed
                    } else {
                        doubleBackToExitPressedOnce = true
                        Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()

                        // Reset flag after delay
                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackToExitPressedOnce = false
                        }, 2000) // 2 seconds delay
                    }
                } else {
                    // Normal back navigation for other fragments
                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack() // Go back to the previous fragment
                    } else {
                        finish()
                    }
                }
            }
        })
    }

    private fun loadFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        // Set animations for fragment transitions
        transaction.setCustomAnimations(
            R.anim.slide_in_right, // enter animation
            R.anim.slide_out_left, // exit animation
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }

        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun flushBackStack() {
        val fragmentManager = supportFragmentManager
        // Clear the backstack
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun syncBottomNavigationWithFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        val selectedItemId = when (currentFragment) {
            is HomeFragment -> R.id.nav_home
            is AboutFragment -> R.id.nav_about
            is ProfileFragment -> R.id.nav_profile
            is SettingFragment -> R.id.nav_setting
            else -> return
        }

        // Update only if the selected item has changed
        if (bottomNavigationView.selectedItemId != selectedItemId) {
            bottomNavigationView.selectedItemId = selectedItemId
        }
    }
}
