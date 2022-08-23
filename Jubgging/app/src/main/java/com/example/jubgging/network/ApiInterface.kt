package com.example.jubgging.network

import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.network.data.response.BaseResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("api/sign/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<BaseResponse<String>>

}