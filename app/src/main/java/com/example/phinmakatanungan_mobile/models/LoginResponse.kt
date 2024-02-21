package com.example.phinmakatanungan_mobile.models

import android.os.Message

data class LoginResponse(

    val error: Boolean,
    val message: String,
    val user: UserData
)
