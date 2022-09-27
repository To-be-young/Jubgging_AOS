package com.example.jubgging.network.data.response

import com.example.jubgging.network.PloggingReceive

data class PloggingResponse(
    val totalPage : Int,
    val totalElments : Int,
    val pageSize : Int,
    val page : Int,
    val content : List<PloggingReceive>
)
