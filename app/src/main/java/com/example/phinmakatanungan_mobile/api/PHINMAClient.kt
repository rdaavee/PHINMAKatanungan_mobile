package com.example.phinmakatanungan_mobile.api

import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PHINMAClient {

    private lateinit var sharedPreferences: SharedPreferences

    // Initialize the instance lazily
    val instance: PHINMAApi by lazy { createInstance() }

    // Set SharedPreferences before using the instance
    fun setSharedPreferences(pref: SharedPreferences) {
        sharedPreferences = pref
    }

    private fun getAuthToken(): String {
        if (!::sharedPreferences.isInitialized) {
            throw IllegalStateException("SharedPreferences has not been initialized. Call setSharedPreferences() before using PHINMAClient.")
        }
        val authToken = sharedPreferences.getString("authToken", "")
        return "Bearer $authToken"
    }

    private fun createInstance(): PHINMAApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", getAuthToken())
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(PHINMAApi::class.java)
    }
    private const val BASE_URL = "http://192.168.100.89:8000/api/"
//    private const val BASE_URL = "http://192.168.42.194:8000/api/" // ran's ip
//    private const val BASE_URL = "http://192.168.100.2:8000/api/" // johann's ip
}
