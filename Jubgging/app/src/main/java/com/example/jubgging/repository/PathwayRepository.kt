package com.example.jubgging.repository

import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PathwayResponse
import com.example.jubgging.network.data.response.PloggingResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PathwayRepository {

    fun pathway(recordId : Int) : Single<BaseResponse<List<PathwayResponse>>>
}

class PathwayRepositoryImpl : PathwayRepository {

    override fun pathway(recordId : Int) : Single<BaseResponse<List<PathwayResponse>>> {
        return ApiClient.api.pathway(recordId).subscribeOn(Schedulers.computation()).observeOn(
            AndroidSchedulers.mainThread()).map { it }
    }
}