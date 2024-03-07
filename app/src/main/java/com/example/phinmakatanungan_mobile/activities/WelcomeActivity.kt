package com.example.phinmakatanungan_mobile.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.phinmakatanungan_mobile.R
import com.example.phinmakatanungan_mobile.api.PHINMAClient

class WelcomeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences
    private fun getAuthToken() : String {

        return sharedPreferences.getString("authToken", "") ?: ""

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if a valid token is present in SharedPreferences
        sharedPreferences = getSharedPreferences("myPreference", MODE_PRIVATE)

        PHINMAClient.setSharedPreferences(sharedPreferences)


        val authToken = getAuthToken()
        if (authToken.isNotEmpty()) {
            // Redirect to DashboardActivity
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()  // Finish LoginActivity to prevent the user from navigating back
        }else {
                // Log the absence of a token
                Log.d("WelcomeActivity", "No token detected")
        }


        setContentView(R.layout.activity_welcome)


        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        }

        findViewById<TextView>(R.id.tv_signup2).setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, ChooseRoleActivity::class.java))
        }
    }
}