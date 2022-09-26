package com.example.jubgging.repository

import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.LoginResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SignUpRepository {
    fun login(loginRequest: LoginRequest):Single<BaseResponse<LoginResponse>>
}

class SignUpRepositoryImpl : SignUpRepository {
    override fun login(loginRequest: LoginRequest): Single<BaseResponse<LoginResponse>> {
        return ApiClient.api.login(loginRequest).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }

}