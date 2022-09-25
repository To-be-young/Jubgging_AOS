package com.example.jubgging.network

import android.util.Log
import com.example.jubgging.viewmodel.SignUpViewModel.Companion.token
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        Log.d("X-AUTH-TOKEN",token)

        if(token.isNotEmpty()){
            builder.addHeader("X-AUTH-TOKEN", token)
        }else{
            Log.d("TAG", "intercept: token null")
        }

        return chain.proceed(builder.build())
    }

}