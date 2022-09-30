package com.example.jubgging.repository

import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.PostCommunityRequest
import com.example.jubgging.network.data.response.BaseResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

interface CommunityRepository {
    fun  postCommunity(postCommunityRequest: PostCommunityRequest):Single<BaseResponse<CommunityGroup>>
}
class CommunityRepositoryImpl:CommunityRepository{
    override fun postCommunity(postCommunityRequest: PostCommunityRequest): Single<BaseResponse<CommunityGroup>> {
        return ApiClient.api.postCommunity(postCommunityRequest).subscribeOn(io.reactivex.schedulers.Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }
}