package com.example.phinmakatanungan_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OnboardingActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding1)

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            startActivity(Intent(this@OnboardingActivity1, OnboardingActivity2::class.java))

        }
    }
}