package com.example.pagedusers.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me"

inline fun <reified SERVICE> createService(retrofit: Retrofit): SERVICE {
    return retrofit.create(SERVICE::class.java)
}

fun createRetrofitInstance(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()