package com.example.jubgging.network

import android.util.Log
import com.example.jubgging.viewmodel.SignUpViewModel.Companion.token
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        Log.d("X-AUTH-TOKEN",token)

        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY2NDI4MjQ5NywiZXhwIjo0ODE3ODgyNDk3fQ.PXqUvHOPz-bGG9O79mH9m4n0zYG6dGOteBob1M2iY98"
        if(token.isNotEmpty()){
            builder.addHeader("X-AUTH-TOKEN", token)
        }else{
            Log.d("TAG", "intercept: token null")
        }

        return chain.proceed(builder.build())
    }

}