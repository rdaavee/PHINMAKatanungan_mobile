package com.example.phinmakatanungan_mobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.phinmakatanungan_mobile.R

class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        findViewById<ImageView>(R.id.iv_backBtnCreate).setOnClickListener {
            startActivity(Intent(this@CreatePostActivity, DashboardActivity::class.java))
        }

        findViewById<TextView>(R.id.tv_createPost).setOnClickListener {
            startActivity(Intent(this@CreatePostActivity, DashboardActivity::class.java))
        }


    }
}