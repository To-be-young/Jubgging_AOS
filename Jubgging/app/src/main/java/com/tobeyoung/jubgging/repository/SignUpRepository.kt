package com.tobeyoung.jubgging.repository

import com.tobeyoung.jubgging.network.ApiClient
import com.tobeyoung.jubgging.network.data.request.EmailCodeAuthRequest
import com.tobeyoung.jubgging.network.data.request.EmailRequest
import com.tobeyoung.jubgging.network.data.request.LoginRequest
import com.tobeyoung.jubgging.network.data.request.SignUpRequest
import com.tobeyoung.jubgging.network.data.response.BaseResponse
import com.tobeyoung.jubgging.network.data.response.LoginResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.computation

interface SignUpRepository {

    fun signUp(signUpRequest: SignUpRequest): Single<BaseResponse<String>>
    fun checkNicknameOverlap(nickname: String): Single<BaseResponse<Boolean>>
    fun login(loginRequest: LoginRequest):Single<BaseResponse<LoginResponse>>
    fun checkEmailOverlap(email:String):Single<BaseResponse<Boolean>>
    fun sendEmailCode(emailRequest: EmailRequest):Single<BaseResponse<Boolean>>
    fun reSendEmailCode(emailRequest: EmailRequest):Single<BaseResponse<Boolean>>
    fun verifyEmailCode(emailCodeAuthRequest: EmailCodeAuthRequest):Single<BaseResponse<Boolean>>
}

class SignUpRepositoryImpl : SignUpRepository {

    override fun signUp(signUpRequest: SignUpRequest): Single<BaseResponse<String>> {
        return ApiClient.api.signUp(signUpRequest).subscribeOn(computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun checkNicknameOverlap(nickname: String): Single<BaseResponse<Boolean>> {
        return ApiClient.api.checkNicknameOverlap(nickname).subscribeOn(computation())
            .observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun login(loginRequest: LoginRequest): Single<BaseResponse<LoginResponse>> {
        return ApiClient.api.login(loginRequest).subscribeOn(computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun checkEmailOverlap(email: String): Single<BaseResponse<Boolean>> {
        return  ApiClient.api.checkEmailOverlap(email).subscribeOn(computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun sendEmailCode(emailRequest: EmailRequest): Single<BaseResponse<Boolean>> {
        return  ApiClient.api.sendEmailCode(emailRequest).subscribeOn(computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun reSendEmailCode(emailRequest: EmailRequest): Single<BaseResponse<Boolean>> {
        return  ApiClient.api.reSendEmailCode(emailRequest).subscribeOn(computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }

    override fun verifyEmailCode(emailCodeAuthRequest: EmailCodeAuthRequest): Single<BaseResponse<Boolean>> {
        return ApiClient.api.verifyEmailCode(emailCodeAuthRequest).subscribeOn(computation()).observeOn(AndroidSchedulers.mainThread()).map { it }
    }
}