package com.example.jubgging.network

import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PloggingResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("api/plogging/finish")
    fun plogging(@Body ploggingRequest : PloggingRequest) : Single<BaseResponse<PloggingResponse>>

}