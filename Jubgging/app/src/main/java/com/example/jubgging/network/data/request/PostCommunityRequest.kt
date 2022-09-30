package com.example.jubgging.network.data.request

data class PostCommunityRequest (
    val title: String,
    val userId: Int,
    val content: String,
    val qualification: String,
    val gatheringTime: String,
    val endingTime: String,
    val gatheringPlace: String,
    val capacity: Int,
    val etc: String,
    val postImage: String,
)
