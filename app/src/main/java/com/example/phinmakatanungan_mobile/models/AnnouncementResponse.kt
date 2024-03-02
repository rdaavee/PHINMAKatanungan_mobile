package com.example.phinmakatanungan_mobile.models

import com.google.gson.annotations.SerializedName

data class AnnouncementResponse(
    @SerializedName("announcements")
    val announcements: List<Announcement>
)
