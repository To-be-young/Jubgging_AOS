package com.example.jubgging.repository

import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.CommunityListResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface CommunityRepository {
    fun getCommunityGroupList(page:Int):Single<BaseResponse<CommunityListResponse<List<CommunityGroup>>>>


}

class CommunityRepositoryImpl:CommunityRepository{
    override fun getCommunityGroupList(page: Int): Single<BaseResponse<CommunityListResponse<List<CommunityGroup>>>> {
        return ApiClient.api.getCommunityList(page).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }    }

}