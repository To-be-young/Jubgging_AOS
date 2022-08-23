package com.example.jubgging.network.data.response
import com.example.jubgging.network.ServerResult

open class BaseResponse <T>(
    var code: Int = -1,
    var message: String? = null,
    val data: T
): ServerResult {
    override fun isSuccess(): Boolean {
        return code == 0
    }

    override fun code():String{
        return code.toString()
    }

    override fun errorMessage():String?{
        return message
    }
}
