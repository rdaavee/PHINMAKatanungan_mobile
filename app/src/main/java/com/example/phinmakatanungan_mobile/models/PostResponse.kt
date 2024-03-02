package com.example.phinmakatanungan_mobile.models

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("posts")
    val posts: List<Post>
)
