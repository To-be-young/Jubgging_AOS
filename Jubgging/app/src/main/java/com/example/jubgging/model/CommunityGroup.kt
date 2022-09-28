package com.example.jubgging.model

data class CommunityGroup(
    val postId: Int,
    val title: String,
    val userId: Int,
    val creationDate: String,
    val content: String,
    val qualification: String,
    val gatheringTime: String,
    val endingTime: String,
    val gatheringPlace: String,
    val capacity: Int,
    val participant: Int,
    val etc: String,
    val postImage: String,
    val recruiting: Boolean,
)
