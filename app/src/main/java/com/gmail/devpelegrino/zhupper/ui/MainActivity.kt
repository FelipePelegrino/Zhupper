package com.gmail.devpelegrino.zhupper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gmail.devpelegrino.zhupper.R
import com.gmail.devpelegrino.zhupper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() = binding.run {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_request_trip -> {
                    navController.navigate(R.id.fragment_request_trip)
                    true
                }

                R.id.nav_trip_history -> {
                    navController.navigate(R.id.fragment_trip_history)
                    true
                }

                else -> false
            }
        }
    }
}
