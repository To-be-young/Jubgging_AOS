package com.tobeyoung.jubgging.repository

import com.tobeyoung.jubgging.model.UserInfo

import com.tobeyoung.jubgging.network.ApiClient
import com.tobeyoung.jubgging.network.data.response.BaseResponse
import com.tobeyoung.jubgging.network.data.response.UserNicknameEmailResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface UserRepository {
    fun getUserInfo(): Single<BaseResponse<UserInfo>>
    fun getUserNicknameEmail(): Single<BaseResponse<UserNicknameEmailResponse>>

}

class UserRepositoryImpl : UserRepository {
    override fun getUserInfo(): Single<BaseResponse<UserInfo>> {
        return ApiClient.api.getUserInfo().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun getUserNicknameEmail(): Single<BaseResponse<UserNicknameEmailResponse>> {
        return ApiClient.api.getUserNicknameEmail().subscribeOn(Schedulers.computation()).observeOn(
            AndroidSchedulers.mainThread()).map { it }
    }
}