package com.example.jubgging.network.data.request

import com.example.jubgging.model.PloggingModel

data class PloggingRequest(
    val userId: String,
    val distance: Double,
    val activity_time: String,
    val pace : String,
    val pathway: List<PloggingModel>

)