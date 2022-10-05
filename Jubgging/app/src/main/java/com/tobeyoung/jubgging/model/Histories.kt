package com.tobeyoung.jubgging.model

import com.google.gson.annotations.SerializedName

data class Histories(
    val totalPage : Int,
    val totalElments : Int,
    val pageSize : Int,
    val page : Int,

    @SerializedName("content")
    val content : List<HistoryGroup>
)