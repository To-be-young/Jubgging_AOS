package com.example.jubgging.network.data.response

data class CommunityListResponse<T>(
    val totalPage: Int,
    val totalElements: Int,
    val pageSize: Int,
    val page: Int,
    val content:T,
)
