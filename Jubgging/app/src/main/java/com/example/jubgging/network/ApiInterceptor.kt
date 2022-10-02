package com.example.jubgging.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if(accessToken.isNotEmpty()){
            builder.addHeader("X-AUTH-TOKEN", accessToken)
        }else{
            Log.d("TAG", "intercept: token null")
        }
        return chain.proceed(builder.build())
    }

}