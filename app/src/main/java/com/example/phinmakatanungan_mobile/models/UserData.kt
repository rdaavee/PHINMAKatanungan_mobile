package com.example.phinmakatanungan_mobile.models

data class UserData(
    val student_id: Int,
    val first_name: String,
    val middle_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val year_level: String,
    val course_id: String,
    val school_id: String
)
