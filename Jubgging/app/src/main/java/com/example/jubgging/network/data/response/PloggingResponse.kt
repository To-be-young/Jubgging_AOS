package com.example.jubgging.network.data.response

import com.google.gson.annotations.SerializedName

data class PloggingResponse(
    val recordId: Int,
    val userId: String,
    val date: String,
    val distance: Double,
    val activityTime: String
)
