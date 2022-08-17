package com.example.jubgging.network

import com.example.jubgging.network.c.C
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(ApiInterceptor())
    }.build()
    var gson = GsonBuilder().setLenient().create()
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(C.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    val api: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}

interface ServerResult {
    fun isSuccess(): Boolean
    fun code(): String
    fun errorMessage(): String?
}