package com.example.jubgging.repository

import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.UserNicknameEmailResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface UserRepository {
    fun getUserNicknameEmail(): Single<BaseResponse<UserNicknameEmailResponse>>
}
class UserRepositoryImpl:UserRepository{
    override fun getUserNicknameEmail(): Single<BaseResponse<UserNicknameEmailResponse>> {
        return ApiClient.api.getUserNicknameEmail().subscribeOn(Schedulers.computation()).observeOn(
            AndroidSchedulers.mainThread()).map { it }
    }
}