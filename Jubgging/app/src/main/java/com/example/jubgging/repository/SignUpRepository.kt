package com.example.jubgging.repository

import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.network.data.response.BaseResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SignUpRepository {
    fun signUp(signUpRequest: SignUpRequest): Single<BaseResponse<String>>
}

class SignUpRepositoryImpl : SignUpRepository {
    override fun signUp(signUpRequest: SignUpRequest): Single<BaseResponse<String>> {
        return ApiClient.api.signUp(signUpRequest).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

}