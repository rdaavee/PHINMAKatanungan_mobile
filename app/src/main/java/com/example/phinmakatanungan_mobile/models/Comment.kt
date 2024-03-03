package com.example.phinmakatanungan_mobile.models

data class Comment(
    val commentID: String,
    val content: String,
    val userID: String,
    val postID: Int,
    val upvote: Int
)
