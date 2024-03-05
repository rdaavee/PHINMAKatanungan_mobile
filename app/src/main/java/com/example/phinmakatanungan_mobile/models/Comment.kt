package com.example.phinmakatanungan_mobile.models

import com.google.gson.annotations.SerializedName

data class Comment(
    val commentID: String,
    val content: String,
    val userID: String,
    val postID: Int,
    val upvote: Int,
    @SerializedName("user")val userData: UserData
)
