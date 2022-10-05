package com.tobeyoung.jubgging.repository

import com.tobeyoung.jubgging.model.CommunityGroup
import com.tobeyoung.jubgging.network.ApiClient
import com.tobeyoung.jubgging.network.data.request.PostCommunityRequest
import com.tobeyoung.jubgging.network.data.response.BaseResponse
import com.tobeyoung.jubgging.network.data.response.CommunityJoinResponse
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