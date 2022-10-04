package com.example.jubgging.model

data class CommunityGroup(
    val postId: Int?,
    val title: String?,
    val email: String?,
    val nickname:String?,
    val creationDate: String?,
    val content: String?,
    val qualificationCount:Int?,
    val qualification: ArrayList<String>?,
    val gatheringTime: String?,
    val endingTime: String?,
    val gatheringPlace: String?,
    val capacity: Int?,
    val participant: Int?,
    val etc: String?,
    val postImage: String?,
)
