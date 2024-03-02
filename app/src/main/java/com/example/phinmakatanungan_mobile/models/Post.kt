package com.example.phinmakatanungan_mobile.models

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val likes_count: BigInteger,
    val comments_count: BigInteger,
    @SerializedName("user") val user: UserData
)