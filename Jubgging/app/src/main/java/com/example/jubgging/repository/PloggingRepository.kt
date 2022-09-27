package com.example.jubgging.repository

import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PathwayResponse
import com.example.jubgging.network.data.response.PloggingResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Query

interface PloggingRepository {
    fun plogging_req(PloggingRequest : PloggingRequest) : Single<BaseResponse<PloggingResponse>>
    fun plogging_res(page : Int) : Single<BaseResponse<PloggingResponse>>
}

class PloggingRepositoryImpl : PloggingRepository {

    override fun plogging_req(ploggingRequest : PloggingRequest) : Single<BaseResponse<PloggingResponse>> {
        return ApiClient.api.plogging_req(ploggingRequest).subscribeOn(Schedulers.computation()).observeOn(
            AndroidSchedulers.mainThread()).map { it }
    }

    override fun plogging_res(page : Int) : Single<BaseResponse<PloggingResponse>> {
        return ApiClient.api.plogging_res(page).subscribeOn(Schedulers.computation()).observeOn(
            AndroidSchedulers.mainThread()).map { it }
    }

}