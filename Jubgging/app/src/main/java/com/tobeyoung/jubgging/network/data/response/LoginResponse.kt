package com.tobeyoung.jubgging.network.data.response

data class LoginResponse(
    val grantType:String= "",
    val accessToken:String = "",
    val refreshToken:String = "",
    val accessTokenExpireDate:String= ""
)
