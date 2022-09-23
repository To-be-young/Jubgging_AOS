package com.example.retrofit2_pr.network.data.response

import com.example.jubgging.network.ServerResult

open class BaseResponse<T>(
    var code: Int = -1,
    var success: Boolean = false,
    var msg: String? = null,
    val data: T,
) : ServerResult {
    override fun isSuccess(): Boolean {
        return success && code == 0
    }

    override fun code(): String {
        return code.toString()
    }

    override fun errorMessage(): String? {
        return codeMessage(code)
    }

}
private fun codeMessage(code: Int): String {
    var message:String = ""
    when(code){
        1002 -> message = "잘못된 인증코드 입니다. 인증코드를 확인해주세요."
    }

    return message
}
