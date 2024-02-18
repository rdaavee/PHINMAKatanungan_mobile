package com.example.phinmakatanungan_mobile.api

import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PHINMAClient {

    private const val BASE_URL = "http://192.168.100.66:8000/api/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", getAuthToken())
                .build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // Add logging interceptor
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun getAuthToken(): String {
        val credentials = "root:"
        val encodedCredentials = Base64.encode(credentials.toByteArray(), Base64.NO_WRAP)
        return "Basic " + String(encodedCredentials)
    }

    val instance: PHINMAApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(PHINMAApi::class.java)
    }
}
