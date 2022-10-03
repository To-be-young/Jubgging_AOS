package com.example.jubgging.network.data.response

import com.google.gson.annotations.SerializedName

data class UserNicknameEmailResponse(
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("userId")
    val userId: String = "",
)