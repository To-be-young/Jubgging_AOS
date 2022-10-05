package com.tobeyoung.jubgging.model

data class HistoryGroup(
    val recordId : Int,
    val userId : Int,
    val date : String,
    val distance : Double,
    val activityTime : String
)