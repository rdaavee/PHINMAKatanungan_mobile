package com.example.phinmakatanungan_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OnBoardingActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding3)

        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            startActivity(Intent(this@OnBoardingActivity3, DashboardFragment::class.java))


        }
    }
}