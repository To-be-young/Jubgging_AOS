package com.example.jubgging.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}