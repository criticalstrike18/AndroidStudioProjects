package com.example.cropwise.utils

import com.example.cropwise.data.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

object RetrofitInstance {
    val api : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(utils.Crop)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    val api2 : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(utils.Price)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    val api3 : ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(utils.Extra)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiInterface::class.java)
    }
}