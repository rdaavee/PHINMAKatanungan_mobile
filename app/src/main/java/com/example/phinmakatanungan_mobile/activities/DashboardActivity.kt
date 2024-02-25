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

        findViewById<Button>(R.id.btnSignOut).setOnClickListener {
            signOut()
        }
    }
    private fun signOut() {
        Log.d("SignOut", "Current token before removal: " + sharedPreferences.getString("authToken", "Not Found"))
        // remove the token from SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        Log.d("SignOut", "Token after removal: " + sharedPreferences.getString("authToken", "Not Found"))

        // redirect back to WelcomeActivity and clear the activity stack
        val intent = Intent(this@DashboardActivity, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()  // finish DashboardActivity to prevent the user from navigating back
    }

    private fun getAuthToken(): String {
        val authToken = sharedPreferences.getString("authToken", "")
        return "Bearer $authToken"
    }
}