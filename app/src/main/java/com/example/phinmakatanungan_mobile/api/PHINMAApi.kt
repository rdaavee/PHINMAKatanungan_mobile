package com.example.phinmakatanungan_mobile.api

import com.example.phinmakatanungan_mobile.models.AnnouncementResponse
import com.example.phinmakatanungan_mobile.models.CommentResponse
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import com.example.phinmakatanungan_mobile.models.LoginResponse
import com.example.phinmakatanungan_mobile.models.Post
import com.example.phinmakatanungan_mobile.models.PostResponse
import com.example.phinmakatanungan_mobile.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PHINMAApi {

    @POST("verifyToken") // Adjust the endpoint URL as per your API
    fun verifyToken(@Header("Authorization") authToken: String): Call<DefaultResponse>

    //registration
    @FormUrlEncoded
    @POST("store")
    @Headers("Accept: application/json")
    fun createUser(
        @Field("user_id") userID:String,
        @Field("user_role") role: String,
        @Field("first_name") firstName:String,
        @Field("middle_name") middleName:String,
        @Field("last_name") lastName:String,
        @Field("gender") selectedGender:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("year_level") year:String,
        @Field("course_id") course:String,
        @Field("department_id") departmentID:String,
        @Field("school_id") campus:String
    ):retrofit2.Call<DefaultResponse>

    @FormUrlEncoded
    @POST("storeteacher")
    @Headers("Accept: application/json")
    fun createTeacher(
        @Field("user_id") userID:String,
        @Field("user_role") role: String,
        @Field("first_name") firstName:String,
        @Field("middle_name") middleName:String,
        @Field("last_name") lastName:String,
        @Field("gender") selectedGender:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("course_id") courseID:String,
        @Field("department_id") departmentID:String,
        @Field("school_id") campus:String
    ):retrofit2.Call<DefaultResponse>

    //login
    @FormUrlEncoded
    @POST("userlogin")
    @Headers("Accept: application/json")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ):retrofit2.Call<LoginResponse>

    //profile
    @GET("profile")
    @Headers("Accept: application/json")
    fun getUserProfile(@Header("Authorization") authToken: String): Call<UserData>

    //post
    @FormUrlEncoded
    @POST("post")
    @Headers("Accept: application/json")
    fun createPost(
        @Header("Authorization") authToken: String,
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("privacy") privacy: String
    ): Call<DefaultResponse>
    @GET("getposts")
    fun getPosts(@Header("Authorization")authToken:String,@Query("query")query: String? = null): Call<PostResponse>

    @FormUrlEncoded
    @POST("storecomment")
    @Headers("Accept: application/json")
    fun addComment(
        @Field("user_id") userID: String,
        @Field("content") content: String,
        @Field("post_id") postID: String,
    ):retrofit2.Call<DefaultResponse>

    //announcements
    @GET("getannouncements")
    @Headers("Accept: application/json")
    fun getAnnounce(@Header("Authorization")authToken: String): Call<AnnouncementResponse>

    @GET("getcomments/{post_id}")
    @Headers("Accept: application/json")
    fun getComments(@Path("post_id")postID: String): Call<CommentResponse>



}