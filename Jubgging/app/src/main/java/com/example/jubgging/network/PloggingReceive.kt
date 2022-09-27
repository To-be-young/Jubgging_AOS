package com.example.jubgging.network

data class PloggingReceive(
    val recordId : Int,
    val userId : Int,
    val date : String,
    val distance : Double,
    val activityTime : String
)