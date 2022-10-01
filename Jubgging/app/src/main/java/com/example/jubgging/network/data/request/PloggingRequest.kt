package com.example.jubgging.network.data.request

import com.example.jubgging.model.PloggingRequest

data class PloggingRequest(
    val userId: String,
    val distance: Double,
    val activity_time: String,
    val pathway: List<PloggingRequest>
)