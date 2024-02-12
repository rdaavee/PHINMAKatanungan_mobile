package com.example.phinmakatanungan_mobile.api

import android.telecom.Call
import com.example.phinmakatanungan_mobile.models.DefaultResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PHINMAApi {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(

        @Field("email") email:String,
        @Field("password") password:String,
        @Field("name") name:String,
        @Field("studnumber") studnumber:String

        ):retrofit2.Call<DefaultResponse>
}