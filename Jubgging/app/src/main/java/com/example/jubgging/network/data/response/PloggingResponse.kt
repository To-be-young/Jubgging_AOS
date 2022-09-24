package com.example.jubgging.network.data.response

import com.google.gson.annotations.SerializedName

data class PloggingResponse (

    @SerializedName("success")
    val success : Boolean,

    @SerializedName("code")
    val code : Int,

    @SerializedName("msg")
    val msg : String,

    @SerializedName("data")
    val data : Data? = null
){
    data class Data(
        @SerializedName("recordId")
        val recordId : Int,

        @SerializedName("userId")
        val userId : String,

        @SerializedName("date")
        val date : String,

        @SerializedName("distance")
        val distance : Double,

        @SerializedName("activityTime")
        val activityTime : String
    )
}