package com.example.myapplication

import ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DatabaseHelper {
    private const val BASE_URL = "https://active-fox-exactly.ngrok-free.app/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}