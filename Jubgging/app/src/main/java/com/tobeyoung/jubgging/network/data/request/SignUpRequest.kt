package com.tobeyoung.jubgging.network.data.request

data class SignUpRequest(
    val userId:String = "",
    val password:String = "",
    val nickname:String = "",
)
