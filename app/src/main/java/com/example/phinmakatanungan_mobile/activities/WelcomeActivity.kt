package com.example.phinmakatanungan_mobile.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.phinmakatanungan_mobile.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        }

        findViewById<TextView>(R.id.tv_signup2).setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, ChooseRoleActivity::class.java))
        }
    }
}