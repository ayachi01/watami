package com.example.myapplication

import ApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DatabaseHelper {
    private const val BASE_URL = " https://active-fox-exactly.ngrok-free.app/RiddleGameAdmin/"

    private val gson = GsonBuilder()
        .setLenient() // Enable lenient mode
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}