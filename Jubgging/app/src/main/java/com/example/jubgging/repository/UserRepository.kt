package com.example.jubgging.repository

import com.example.jubgging.model.UserInfo
import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.network.data.response.BaseResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface UserRepository {
    fun getUserInfo(): Single<BaseResponse<UserInfo>>
}

class UserRepositoryImpl : UserRepository {
    override fun getUserInfo(): Single<BaseResponse<UserInfo>> {
        return ApiClient.api.getUserInfo().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }
}