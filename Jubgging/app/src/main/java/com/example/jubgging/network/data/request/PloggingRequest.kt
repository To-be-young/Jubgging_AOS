package com.example.jubgging.network.data.request

import com.example.jubgging.network.PloggingSend

data class PloggingRequest(
    val userId: String,
    val distance: Double,
    val activity_time: String,
    val pathway: List<PloggingSend>
)