package com.tobeyoung.jubgging.network.data.request

import com.google.gson.annotations.SerializedName

data class PostCommunityRequest (
    @SerializedName("userId")
    val userId: String,
    @SerializedName("title")
    val title: String,
    val content: String,
    val qualification: ArrayList<String>,
    val gatheringTime: String,
    val endingTime: String,
    val gatheringPlace: String,
    val capacity: Int,
    val etc: String,
    val postImage: String,
)
