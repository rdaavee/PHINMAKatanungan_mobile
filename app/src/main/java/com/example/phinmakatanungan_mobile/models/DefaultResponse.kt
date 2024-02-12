package com.example.phinmakatanungan_mobile.models

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)
