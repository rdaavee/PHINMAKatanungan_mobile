package com.example.phinmakatanungan_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<TextView>(R.id.tv_signup2).setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

        findViewById<Button>(R.id.btnLogin2).setOnClickListener {
            startActivity(Intent(this@LoginActivity, DashboardFragment::class.java))
        }
    }
}