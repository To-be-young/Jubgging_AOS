package com.example.jubgging.repository

import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PloggingResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PloggingRepository {
    fun sendPloggingResult(PloggingRequest : PloggingRequest) : Single<BaseResponse<PloggingResponse>>
}

class PloggingRepositoryImpl : PloggingRepository {

    override fun sendPloggingResult(ploggingRequest : PloggingRequest) : Single<BaseResponse<PloggingResponse>> {
        return ApiClient.api.sendPloggingResult(ploggingRequest).subscribeOn(Schedulers.computation()).observeOn(
            AndroidSchedulers.mainThread()).map { it }
    }
}