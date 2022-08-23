package com.example.jubgging.network.data.request

data class SignUpRequest(
    val name:String = "",
    val userId:String = "",
    val password:String = "",
    val nickname:String = "",
    val phoneNumber:String = "",
    val age:Int = 0,
    val gender:String = "" //MALE,FEMALE,NONE
)
