package com.example.jubgging.adapter

import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.jubgging.R

object BindingConversions {

    //editText들 이전 값과 새로 입력한 값이 다를 때 처리 필요함, 회원가입 시!!
    //이메일 인증 및 중복 검사 구현 필요

    @JvmStatic
    @BindingAdapter("setNoticeTextView", "setEmailText")
    fun checkEmailNullRegex(button: Button, textView: TextView, userIdText: String) {
        if (userIdText.isNotEmpty()) {
            if (matchEmailRegex(userIdText)) {
                button.isEnabled = true
                button.setTextColor(button.context.getColor(R.color.green_blue))
                textView.text = "이메일 중복 버튼을 눌러 검사를 시행해주세요."
                textView.setTextColor(textView.context.getColor(R.color.green_blue))

            } else {
                button.isEnabled = false
                button.setTextColor(button.context.getColor(R.color.brownish_grey))
                textView.text = "올바른 이메일 형식이 아닙니다. 다시 입력해주세요."
                textView.setTextColor(textView.context.getColor(R.color.red))
            }
        } else {
            button.isEnabled = false
            button.setTextColor(button.context.getColor(R.color.brownish_grey))
        }
    }

    @JvmStatic
    @BindingAdapter("setEmailCodeText")
    fun checkEmailAuthCodeNull(button: Button, emailCodeText: String) {
        if (emailCodeText.isNotEmpty()) {
            button.isEnabled = true
            button.setTextColor(button.context.getColor(R.color.green_blue))
        } else {
            button.isEnabled = false
            button.setTextColor(button.context.getColor(R.color.brownish_grey))
        }
    }

    @JvmStatic
    @BindingAdapter("setPwdRegex")
    fun setPwdNullRegex(noticeTv: TextView, pwd: String) {
        if (pwd.isNotEmpty()) {
            if (matchPwdRegex(pwd)) {
                noticeTv.text = "사용가능한 비밀번호입니다."
                noticeTv.setTextColor(noticeTv.context.getColor(R.color.green_blue))
            } else {
                noticeTv.text = "영문자, 특수문자, 숫자를 포함해 8자리 이상 20자리 미만"
                noticeTv.setTextColor(noticeTv.context.getColor(R.color.red))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setPwdText", "setPwdChkText")
    fun setPwdMatches(noticeTv: TextView, pwd: String, pwdChk: String) {
        if (pwdChk.isNotEmpty()) {
            if (pwd == pwdChk) {
                noticeTv.text = "통과하였습니다."
                noticeTv.setTextColor(noticeTv.context.getColor(R.color.green_blue))

            } else {
                noticeTv.text = "비밀번호와 일치하지 않습니다."
                noticeTv.setTextColor(noticeTv.context.getColor(R.color.red))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setPwdText", "setPwdChkText", "setEmailAuthFlag")
    fun setAccountBtnEnable(
        button: Button,
        userPwdText: String,
        userPwdChkText: String,
        flag: Boolean,
    ) {
        //추후 다시 수정 예정
        if (flag) {
            if (userPwdText.isNotEmpty() && userPwdChkText.isNotEmpty()) {
                if (userPwdText == userPwdChkText && matchPwdRegex(userPwdText)) {
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
    @BindingAdapter("setInputNickname")
    fun setEnableOverlapBtn(overlapBtn: Button, nickname: String) {
        if (nickname.isNotBlank()) {
            overlapBtn.isEnabled = true
            overlapBtn.setTextColor(overlapBtn.context.getColor(R.color.green_blue))
        } else {
            overlapBtn.isEnabled = false
            overlapBtn.setTextColor(overlapBtn.context.getColor(R.color.brownish_grey))
        }
    }


    @JvmStatic
    @BindingAdapter("setPassAuthFlag", "setOverlapFlag")
    fun setEnableSignUpFinBtn(finBtn: Button, passAuthFlag: Boolean, overlapFlag: Boolean) {
        if (passAuthFlag && !overlapFlag) {
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

    private fun matchEmailRegex(email: String): Boolean {
        val patterns = Patterns.EMAIL_ADDRESS
        return email.matches(patterns.toRegex())
    }

    private fun matchPwdRegex(pwd: String): Boolean {
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*#?&])[A-Za-z\\d\$@\$!%*#?&]{8,20}"
        return pwd.matches(regex.toRegex())
    }
}


