package com.example.jubgging.model

import com.google.gson.annotations.SerializedName

data class Communities(
    val totalPage: Int,
    val totalElements: Int,
    val pageSize: Int,
    val page: Int,
    @SerializedName("content")
    val content:List<CommunityGroup>,
)
