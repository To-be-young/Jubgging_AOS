package com.example.jubgging.network

import com.example.jubgging.model.Communities
import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.data.request.*
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.CommunityJoinResponse
import com.example.jubgging.network.data.response.LoginResponse
import com.example.jubgging.network.data.response.UserNicknameEmailResponse
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
    fun checkNicknameOverlap(@Query("nickname") nickName:String) :Single<BaseResponse<Boolean>>

    @POST("api/sign/login")
    fun login(@Body loginRequest: LoginRequest):Single<BaseResponse<LoginResponse>>

    @POST("api/user/user-emails/exists")
    fun checkEmailOverlap(@Query("email") email:String) :Single<BaseResponse<Boolean>>

    @POST("api/sign/email")
    fun sendEmailCode(@Body emailRequest:EmailRequest):Single<BaseResponse<Boolean>>

    @POST("/api/sign/refreshCode")
    fun reSendEmailCode(@Body emailRequest:EmailRequest):Single<BaseResponse<Boolean>>

    @GET("api/user/get-user-nick")
    fun getUserNicknameEmail():Single<BaseResponse<UserNicknameEmailResponse>>

    @POST("api/sign/verifyCode")
    fun verifyEmailCode(@Body emailCodeAuthRequest:EmailCodeAuthRequest):Single<BaseResponse<Boolean>>

    @GET("api/community/get-postList")
    suspend fun getCommunityList(@Query("page") page:Int):Response<BaseResponse<Communities>>

    @POST("api/community/posting")
    fun postCommunity(@Body postCommunityRequest: PostCommunityRequest):Single<BaseResponse<CommunityGroup>>

    @GET("api/community/get-post")
    fun getCommunityDetail(@Query("postId") postId:Int):Single<BaseResponse<CommunityGroup>>
    @POST("api/community/join-community")
    fun joinCommunity(@Query("postId") postId: Int):Single<BaseResponse<CommunityJoinResponse<CommunityGroup>>>
}
