package com.tobeyoung.jubgging.network.data.response

data class CommunityJoinListResponse<T>(
    val totalPage: Int,
    val totalElements: Int,
    val pageSize: Int,
    val page: Int,
    val content:List<CommunityJoinResponse<T>>,
)
