package com.example.phinmakatanungan_mobile.models

import com.google.gson.annotations.SerializedName

data class CommentResponse (
    @SerializedName("comments")
    val comments: List<Comment>

)