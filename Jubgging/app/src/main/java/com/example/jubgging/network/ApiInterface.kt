package com.example.jubgging.network

import com.example.jubgging.model.Communities
import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.data.request.EmailCodeAuthRequest
import com.example.jubgging.network.data.request.EmailRequest
import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.CommunityListResponse
import com.example.jubgging.network.data.response.LoginResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import org.checkerframework.common.reflection.qual.GetClass
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @POST("api/sign/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<BaseResponse<String>>

    @POST("api/user/user-nickname/exists")
    fun checkNicknameOverlap(@Query("nickname") nickName:String) :Single<BaseResponse<Boolean>>

    @POST("api/sign/login")
    fun login(@Body loginRequest: LoginRequest):Single<BaseResponse<LoginResponse>>

    @POST("api/user/user-emails/exists")
    fun checkEmailOverlap(@Query("email") email:String) :Single<BaseResponse<Boolean>>

    @POST("api/sign/email")
    fun sendEmailCode(@Body emailRequest:EmailRequest):Single<BaseResponse<Boolean>>

    @POST("/api/sign/refreshCode")
    fun reSendEmailCode(@Body emailRequest:EmailRequest):Single<BaseResponse<Boolean>>


    @POST("api/sign/verifyCode")
    fun verifyEmailCode(@Body emailCodeAuthRequest:EmailCodeAuthRequest):Single<BaseResponse<Boolean>>

    @GET("api/community/get-postList")
    suspend fun getCommunityList(@Query("page") page:Int):Response<BaseResponse<Communities>>

}
