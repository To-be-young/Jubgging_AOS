package com.example.jubgging.adapter

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.example.jubgging.R

object BindingConversions {
    @JvmStatic
    @BindingAdapter("setEmailText", "setPwdText", "setPwdChkText", "setEmailAuthFlag")
    fun setAccountBtnEnable(
        button: Button,
        userIdText: String,
        userPwdText: String,
        userPwdChkText: String,
        flag: Boolean,
    ) {
        //추후 다시 수정 예정
        if (userIdText.isNotEmpty() && flag) {
            if (userPwdText.isNotEmpty() && userPwdChkText.isNotEmpty()) {
                if (userPwdText == userPwdChkText) {
                    button.isEnabled = true
                    button.setTextColor(button.context.getColor(R.color.green_blue))
                } else {
                    button.isEnabled = false
                    button.setTextColor(button.context.getColor(R.color.brownish_grey))
                }
            } else {
                button.isEnabled = false
                button.setTextColor(button.context.getColor(R.color.brownish_grey))
            }
        } else {
            button.isEnabled = false
            button.setTextColor(button.context.getColor(R.color.brownish_grey))
        }
    }

    //인증번호 발송 버튼 활성화 (O)
    //i) not Blank
    //ii) passed regex
    //iii) btn 활성화
    @JvmStatic
    @BindingAdapter("setInputPhoneNumber")
    fun setEnableSendSmsBtn(sendCodeBtn: Button, inputPhoneNum: String) {
        if (inputPhoneNum.isNotBlank() && matchPhoneNumberRegex(inputPhoneNum)) {
            sendCodeBtn.isEnabled = true
            sendCodeBtn.setTextColor(sendCodeBtn.context.getColor(R.color.green_blue))
        } else {
            sendCodeBtn.isEnabled = false
            sendCodeBtn.setTextColor(sendCodeBtn.context.getColor(R.color.brownish_grey))
        }
    }

    //인증확인 editText & btn 활성화
    //i) smsCode sent -> editText 활성화
    //ii) 6자리 regex pass -> btn 활성화

    //인증 과정이 끝난 뒤 입력값이 바뀌게 되면 다시 원상 복구

    // 결과 성공에 따라 다음단계 성공 btn 활성화

    @JvmStatic
    @BindingAdapter("setSentCodeFlag", "setInputCode")
    fun setEnableAuthBtn(authBtn: Button, sentCodeFlag: Boolean, text: String) {
        if (text.isNotBlank() && text.length == 6 && sentCodeFlag) {
            authBtn.isEnabled = true
            authBtn.setTextColor(authBtn.context.getColor(R.color.green_blue))
        } else {
            authBtn.isEnabled = false
            authBtn.setTextColor(authBtn.context.getColor(R.color.brownish_grey))
        }
    }

    @JvmStatic
    @BindingAdapter("setPassAuthFlag")
    fun setEnableSecondFinBtn(finBtn: Button, passAuthFlag: Boolean) {
        if (passAuthFlag) {
            finBtn.isEnabled = true
            finBtn.setTextColor(finBtn.context.getColor(R.color.green_blue))
        } else {
            finBtn.isEnabled = false
            finBtn.setTextColor(finBtn.context.getColor(R.color.brownish_grey))
        }
    }




    private fun matchPhoneNumberRegex(phoneNumber: String): Boolean {
        val regex = "^\\d{3}-\\d{4}-\\d{4}\$"
        return phoneNumber.matches(regex.toRegex())
    }
}


