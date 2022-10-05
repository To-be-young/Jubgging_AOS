package com.tobeyoung.jubgging.network.data.response

data class PloggingResponse(
    val totalPage : Int,
    val totalElments : Int,
    val pageSize : Int,
    val page : Int,
    val content : List<PloggingReceive>
)
