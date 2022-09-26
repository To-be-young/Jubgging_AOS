package com.example.jubgging.network

import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.LoginResponse
import com.example.jubgging.network.data.response.PloggingResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @POST("api/plogging/finish")
    fun plogging_req(@Body ploggingRequest : PloggingRequest) : Single<BaseResponse<PloggingResponse>>

    @GET("api/plogging/log_list")
    fun plogging_res() : Single<BaseResponse<List<PloggingResponse>>>

    @POST("api/sign/login")
    fun login(@Body loginRequest: LoginRequest):Single<BaseResponse<LoginResponse>>

}