package com.example.jubgging.repository

import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.PostCommunityRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.CommunityJoinResponse
import com.example.jubgging.view.CommunityJoinActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

interface CommunityRepository {
    fun postCommunity(postCommunityRequest: PostCommunityRequest): Single<BaseResponse<CommunityGroup>>
    fun getCommunityDetail(postId: Int): Single<BaseResponse<CommunityGroup>>
    fun joinCommunity(postId: Int): Single<BaseResponse<CommunityJoinResponse<CommunityGroup>>>

}

class CommunityRepositoryImpl : CommunityRepository {
    override fun postCommunity(postCommunityRequest: PostCommunityRequest): Single<BaseResponse<CommunityGroup>> {
        return ApiClient.api.postCommunity(postCommunityRequest)
            .subscribeOn(io.reactivex.schedulers.Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun getCommunityDetail(postId: Int): Single<BaseResponse<CommunityGroup>> {
        return ApiClient.api.getCommunityDetail(postId)
            .subscribeOn(io.reactivex.schedulers.Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun joinCommunity(postId: Int): Single<BaseResponse<CommunityJoinResponse<CommunityGroup>>>{
        return ApiClient.api.joinCommunity(postId)
            .subscribeOn(io.reactivex.schedulers.Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }
}