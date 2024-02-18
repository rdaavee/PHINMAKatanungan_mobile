package com.example.phinmakatanungan_mobile.api

import android.telecom.Call
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PHINMAApi {

    @FormUrlEncoded
    @POST("storeStudent")
    fun createUser(
        @Field("student_id") studentID:String,
        @Field("first_name") firstName:String,
        @Field("middle_name") middleName:String,
        @Field("last_name") lastName:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("course_id") course:String,
        @Field("year_level") year:String,
        @Field("school_id") school:String
    ):retrofit2.Call<DefaultResponse>

    @FormUrlEncoded
    @POST("createteacher")
    fun createTeacher(

        @Field("email") email:String,
        @Field("password") password:String,
        @Field("name") name:String,
        @Field("department") department:String

    ):retrofit2.Call<DefaultResponse>
}