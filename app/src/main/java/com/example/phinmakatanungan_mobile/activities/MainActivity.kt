package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.phinmakatanungan_mobile.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: SharedPrefsViewModel
    private lateinit var dashboardFragment: DashboardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        initializeViewModel()

        // Setup navigation using Navigation Component
        setupNavigation()

        // Initialize auth token
        initializeAuthToken()
        clearSharedPreferences()
    }


    private fun clearSharedPreferences() {
        val sharedPreferences = getSharedPreferences("filter", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Clear all data
        editor.apply()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(SharedPrefsViewModel::class.java)
    }

    private fun setupNavigation() {
        // Find the NavHostFragment and NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Find the BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Setup bottom navigation with NavController
        bottomNavigationView.setupWithNavController(navController)
    }
    private fun initializeAuthToken() {
        sharedPreferences = getSharedPreferences("myPreference", MODE_PRIVATE)
        val authToken = sharedPreferences.getString("authToken", "")
        if (!authToken.isNullOrEmpty()) {
            viewModel.authToken = "Bearer $authToken"
        } else {
            Log.e("MainActivity", "Auth token is null or empty.")
        }
    }

    fun getAuthToken(): String? {
        return viewModel.authToken
    }
}