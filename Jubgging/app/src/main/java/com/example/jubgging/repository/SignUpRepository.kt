package com.example.jubgging.repository

import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.LoginResponse
import com.google.android.gms.common.api.Api
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SignUpRepository {
    fun signUp(signUpRequest: SignUpRequest): Single<BaseResponse<String>>
    fun checkNicknameOverlap(nickname: String): Single<BaseResponse<Boolean>>
    fun login(loginRequest: LoginRequest):Single<BaseResponse<LoginResponse>>
    fun checkEmailOverlap(email:String):Single<BaseResponse<Boolean>>
}

class SignUpRepositoryImpl : SignUpRepository {
    override fun signUp(signUpRequest: SignUpRequest): Single<BaseResponse<String>> {
        return ApiClient.api.signUp(signUpRequest).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun checkNicknameOverlap(nickname: String): Single<BaseResponse<Boolean>> {
        return ApiClient.api.checkNicknameOverlap(nickname).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun login(loginRequest: LoginRequest): Single<BaseResponse<LoginResponse>> {
        return ApiClient.api.login(loginRequest).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun checkEmailOverlap(email: String): Single<BaseResponse<Boolean>> {
        return  ApiClient.api.checkEmailOverlap(email).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }

}