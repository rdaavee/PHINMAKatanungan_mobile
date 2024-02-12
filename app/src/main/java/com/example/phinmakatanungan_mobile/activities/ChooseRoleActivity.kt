package com.example.phinmakatanungan_mobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.phinmakatanungan_mobile.R

class ChooseRoleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_role)


        findViewById<ImageView>(R.id.iv_backBtnRole).setOnClickListener {
            startActivity(Intent(this@ChooseRoleActivity, LoginActivity::class.java))
        }

        findViewById<CardView>(R.id.cv_studentRole).setOnClickListener {
            startActivity(Intent(this@ChooseRoleActivity, StudentSignUpActivity::class.java))
        }

        findViewById<CardView>(R.id.cv_teacherRole).setOnClickListener {
            startActivity(Intent(this@ChooseRoleActivity, TeacherSignUpActivity::class.java))
        }
    }
}