package com.tobeyoung.jubgging.repository

import com.tobeyoung.jubgging.network.ApiClient
import com.tobeyoung.jubgging.network.data.response.BaseResponse
import com.tobeyoung.jubgging.network.data.response.PathwayResponse
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