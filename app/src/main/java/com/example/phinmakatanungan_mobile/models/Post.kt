package com.example.phinmakatanungan_mobile.models

import java.math.BigInteger

data class Post(
    val id: Int,
    val name: String,
    val course: String,
    val title: String,
    val content: String,
    val likes_count: BigInteger,
    val comments_count: BigInteger
)