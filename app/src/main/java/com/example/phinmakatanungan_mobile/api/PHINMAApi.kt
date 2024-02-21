package com.example.phinmakatanungan_mobile.api

import android.telecom.Call
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import com.example.phinmakatanungan_mobile.models.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface PHINMAApi {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("store")
    fun createUser(
        @Field("student_id") studentID:String,
        @Field("first_name") firstName:String,
        @Field("middle_name") middleName:String,
        @Field("last_name") lastName:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("year_level") year:String,
        @Field("course_id") course:String,
        @Field("school_id") camp:String
    ):retrofit2.Call<DefaultResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("createteacher")
    fun createTeacher(

        @Field("teacher_id") teacherID:String,
        @Field("first_name") firstName:String,
        @Field("middle_name") middleName:String,
        @Field("last_name") lastName:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("department_id") departmentID:String,
        @Field("school_id") school:String

    ):retrofit2.Call<DefaultResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("userlogin")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String

    ):retrofit2.Call<LoginResponse>
}