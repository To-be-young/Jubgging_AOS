package com.example.jubgging.network

import com.example.jubgging.model.Histories
import com.example.jubgging.model.UserInfo
import com.example.jubgging.network.data.request.*
import com.example.jubgging.network.data.response.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("api/sign/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<BaseResponse<String>>

    @POST("api/user/user-nickname/exists")
    fun checkNicknameOverlap(@Query("nickname") nickName: String): Single<BaseResponse<Boolean>>

    @POST("api/sign/login")
    fun login(@Body loginRequest: LoginRequest): Single<BaseResponse<LoginResponse>>

    @POST("api/user/user-emails/exists")
    fun checkEmailOverlap(@Query("email") email: String): Single<BaseResponse<Boolean>>

    @POST("api/sign/email")
    fun sendEmailCode(@Body emailRequest: EmailRequest): Single<BaseResponse<Boolean>>

    @POST("/api/sign/refreshCode")
    fun reSendEmailCode(@Body emailRequest: EmailRequest): Single<BaseResponse<Boolean>>

    @POST("api/sign/verifyCode")
    fun verifyEmailCode(@Body emailCodeAuthRequest: EmailCodeAuthRequest): Single<BaseResponse<Boolean>>

    @GET("api/user/get-user-info")
    fun getUserInfo(): Single<BaseResponse<UserInfo>>

    @GET("api/user/get-user-nick")
    fun getUserNicknameEmail(): Single<BaseResponse<UserNicknameEmailResponse>>


    @POST("api/plogging/finish")
    fun sendPloggingResult(@Body ploggingRequest: PloggingRequest): Single<BaseResponse<PloggingResponse>>

    @GET("api/plogging/log_list")
    suspend fun getPloggingList(@Query("page") page: Int): Response<BaseResponse<Histories>>

    @GET("api/plogging/log_pathway")
    fun pathway(@Query("recordId") recordId: Int): Single<BaseResponse<List<PathwayResponse>>>
}
