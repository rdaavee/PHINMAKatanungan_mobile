package com.example.phinmakatanungan_mobile.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Base64

object PHINMAClient {

    private val AUTH = "Basic " + Base64.encodeToString("testuser:123456".toByteArray(), Base64.NO_WRAP)

    private const val BASE_URL = "http://192.168.100.66/mobileapi/public/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->

            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)

        }.build()

    val instance: PHINMAApi by lazy{

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(PHINMAApi::class.java)
    }

}