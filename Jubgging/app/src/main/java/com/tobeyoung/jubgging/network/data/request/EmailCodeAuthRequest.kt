package com.tobeyoung.jubgging.network.data.request

data class EmailCodeAuthRequest(
    val email:String = "",
    val code:String = ""
)
