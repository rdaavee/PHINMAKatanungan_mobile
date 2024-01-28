package com.example.phinmakatanungan_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OnboardingActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding2)

        findViewById<Button>(R.id.btnNext2).setOnClickListener {
            startActivity(Intent(this@OnboardingActivity2, OnBoardingActivity3::class.java))
        }
    }
}