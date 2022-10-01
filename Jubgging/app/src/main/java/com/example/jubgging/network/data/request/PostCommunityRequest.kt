package com.example.jubgging.network.data.request

import com.google.gson.annotations.SerializedName

data class PostCommunityRequest (
    val title: String,
    @SerializedName("userId")
    val userId: String,
    val content: String,
    val qualification: String,
    val gatheringTime: String,
    val endingTime: String,
    val gatheringPlace: String,
    val capacity: Int,
    val etc: String,
    val postImage: String,
)
