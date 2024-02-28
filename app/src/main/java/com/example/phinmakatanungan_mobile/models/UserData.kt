package com.example.phinmakatanungan_mobile.models

data class UserData(
    val teacher_id: String,
    val student_id: String,
    val first_name: String,
    val middle_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val year_level: String,
    val course_id: String,
    val school_id: String,
    val role: String,
)
