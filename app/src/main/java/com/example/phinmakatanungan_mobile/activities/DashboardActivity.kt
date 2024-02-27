package com.example.phinmakatanungan_mobile.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient

class DashboardActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // initialize sharedPreferences
        sharedPreferences = getSharedPreferences("myPreference", MODE_PRIVATE)
    }

    private fun getAuthToken(): String {
        val authToken = sharedPreferences.getString("authToken", "")
        return "Bearer $authToken"
    }
}