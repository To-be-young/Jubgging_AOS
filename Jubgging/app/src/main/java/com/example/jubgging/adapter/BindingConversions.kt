package com.example.jubgging.adapter

import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.jubgging.R

object BindingConversions {

    // SignupAccount 관련
    //email null, regex 확인 -> email 중복 버튼 enable, notice
    @JvmStatic
    @BindingAdapter("setEmailNtTv", "setEmailText")
    fun checkEmailNullRegex(emailCheckBtn: Button, emailNoticeTv: TextView, userEmail: String) {
        if (userEmail.isNotEmpty()) {
            //email is not null
            if (matchEmailRegex(userEmail)) {
                //email matches regex
                emailCheckBtn.isEnabled = true
                emailCheckBtn.setTextColor(emailCheckBtn.context.getColor(R.color.green_blue))
                emailNoticeTv.text = "이메일 중복 버튼을 눌러 검사를 시행해주세요."
                emailNoticeTv.setTextColor(emailNoticeTv.context.getColor(R.color.green_blue))
            } else {
                //email is not match regex
                emailCheckBtn.isEnabled = false
                emailCheckBtn.setTextColor(emailCheckBtn.context.getColor(R.color.brownish_grey))
                emailNoticeTv.text = "올바른 이메일 형식이 아닙니다. 다시 입력해주세요."
                emailNoticeTv.setTextColor(emailNoticeTv.context.getColor(R.color.red))
            }
        } else {
            //email is null
            emailCheckBtn.isEnabled = false
            emailCheckBtn.setTextColor(emailCheckBtn.context.getColor(R.color.brownish_grey))
        }
    }


    //pwd null, regex 확인 -> noticeTv에 상태 출력
    @JvmStatic
    @BindingAdapter("setPwdRegex")
    fun setPwdNullRegex(pwdNoticeTv: TextView, pwd: String) {
        //pwd is not null
        if (pwd.isNotEmpty()) {
            if (matchPwdRegex(pwd)) {
                //pwd matches regex
                pwdNoticeTv.text = "사용가능한 비밀번호입니다."
                pwdNoticeTv.setTextColor(pwdNoticeTv.context.getColor(R.color.green_blue))
            } else {
                //pwd is not match regex
                pwdNoticeTv.text = "영문자, 특수문자, 숫자를 포함해 8자리 이상 20자리 미만"
                pwdNoticeTv.setTextColor(pwdNoticeTv.context.getColor(R.color.red))
            }
        }
    }

    //pwd == pwdChk match 확인
    @JvmStatic
    @BindingAdapter("setPwdText", "setPwdChkText")
    fun setPwdMatches(pwdChkNoticeTv: TextView, pwd: String, pwdChk: String) {
        //pwdChk is not null
        if (pwdChk.isNotEmpty()) {
            if (pwd == pwdChk) {
                //pwd matches pwdChk
                pwdChkNoticeTv.text = "통과하였습니다."
                pwdChkNoticeTv.setTextColor(pwdChkNoticeTv.context.getColor(R.color.green_blue))

            } else {
                //pwd is not match pwdChk
                pwdChkNoticeTv.text = "비밀번호와 일치하지 않습니다."
                pwdChkNoticeTv.setTextColor(pwdChkNoticeTv.context.getColor(R.color.red))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setPwdText", "setPwdChkText","setEmailAuthFlag","setNicknameFlag")
    fun setAccountFinBtnEnable(
        accountFinBtn: Button,
        userPwd: String,
        userPwdChk: String,
        emailAuthFlag: Int,
        nicknameFlag:Int
    ) {
        //emailAuthFlag => email 인증이 성공했는지 여부
        if (emailAuthFlag == 0 &&nicknameFlag == 0) {
            //email 인증 성공
            if (userPwd.isNotEmpty() && userPwdChk.isNotEmpty()) {
                //pwd, pwdChk is not null, match Regex
                if (userPwd == userPwdChk && matchPwdRegex(userPwd)) {
                    accountFinBtn.isEnabled = true
                    accountFinBtn.setTextColor(accountFinBtn.context.getColor(R.color.green_blue))
                } else {
                    accountFinBtn.isEnabled = false
                    accountFinBtn.setTextColor(accountFinBtn.context.getColor(R.color.brownish_grey))
                }
            } else {
                accountFinBtn.isEnabled = false
                accountFinBtn.setTextColor(accountFinBtn.context.getColor(R.color.brownish_grey))
            }
        } else if (emailAuthFlag == 1 && nicknameFlag == 1) {
            //email 인증 실패
            accountFinBtn.isEnabled = false
            accountFinBtn.setTextColor(accountFinBtn.context.getColor(R.color.brownish_grey))
        }
    }

/*

    //SignupAuth 관련
    //send
    @JvmStatic
    @BindingAdapter("setPhoneNumberNoticeTv", "setInputPhoneNumber")
    fun setEnablePnumCodeSendBtn(
        pnumSendCodeBtn: Button,
        pnumNoticeTv: TextView,
        phoneNumber: String,
    ) {
        if (phoneNumber.isNotBlank()) {
            //phoneNumber is Not null
            if (matchPhoneNumberRegex(phoneNumber)) {
                //phoneNumber matches Regex
                pnumSendCodeBtn.isEnabled = true
                pnumSendCodeBtn.setTextColor(pnumSendCodeBtn.context.getColor(R.color.green_blue))

                pnumNoticeTv.text = "인증 버튼을 눌러 인증 과정을 완료하세요."
                pnumNoticeTv.setTextColor(pnumNoticeTv.context.getColor(R.color.green_blue))
            } else {
                //phoneNumber is not match Regex
                pnumSendCodeBtn.isEnabled = false
                pnumSendCodeBtn.setTextColor(pnumSendCodeBtn.context.getColor(R.color.brownish_grey))

                pnumNoticeTv.text = "전화번호 형식에 맞지 않습니다. 정확한 번호를 입력해주세요."
                pnumNoticeTv.setTextColor(pnumNoticeTv.context.getColor(R.color.red))
            }
        }
    }
*/


    //pnumAuthCode 전송 여부, userInput null 여부에 따른 pnumAuthBtn enable 관리
/*    @JvmStatic
    @BindingAdapter("setSentCodeFlag", "setPnumAuthCode")
    fun setEnablePnumAuthBtn(pnumAuthBtn: Button, sentCodeFlag: Boolean, pnumAuthCode: String) {
        // pnum-auth code is not null, matches Regex and pnumAuthCode was sent
        if (pnumAuthCode.isNotBlank() && pnumAuthCode.length == 6 && sentCodeFlag) {
            pnumAuthBtn.isEnabled = true
            pnumAuthBtn.setTextColor(pnumAuthBtn.context.getColor(R.color.green_blue))
        } else {
            // pnum-auth code is null, is not match Regex, pnumAuthCode was not sent
            pnumAuthBtn.isEnabled = false
            pnumAuthBtn.setTextColor(pnumAuthBtn.context.getColor(R.color.brownish_grey))
        }
    }*/

    //userInput null 여부에 따른 nickname 중복 검사 btn enable 관리
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

    //닉네임 중복 여부, pnum auth 과정 완료 여부에 따른 signupFinBtn enable 관리
    @JvmStatic
    @BindingAdapter("setPassAuthFlag", "setOverlapFlag")
    fun setEnableSignUpFinBtn(finBtn: Button, passAuthFlag: Int, overlapFlag: Int) {
        if (passAuthFlag == 0 && overlapFlag == 0) {
            finBtn.isEnabled = true
            finBtn.setTextColor(finBtn.context.getColor(R.color.green_blue))
        } else {
            finBtn.isEnabled = false
            finBtn.setTextColor(finBtn.context.getColor(R.color.brownish_grey))
        }
    }

    //email, pnum Auth 공통
    //codeSent여부에 따른 Editext 및 button enable 관리
    @JvmStatic()
    @BindingAdapter("setCodeSentFlag", "setUserInputEt", "setCodeEt")
    fun setEnableCodeSent(
        codeSendBtn: Button,
        codeSentFlag: Boolean,
        userInput: EditText,
        codeEt: EditText,
    ) {
        if (codeSentFlag) {
            codeSendBtn.isEnabled = false
            codeSendBtn.setTextColor(codeSendBtn.context.getColor(R.color.brownish_grey))
            userInput.isEnabled = false
            codeEt.isEnabled = true
        } else {
            codeSendBtn.isEnabled = true
            codeSendBtn.setTextColor(codeSendBtn.context.getColor(R.color.green_blue))
            userInput.isEnabled = true
            codeEt.isEnabled = false
        }
    }

    //code 발송 후 Timeout 시 UI enable 관리
    @JvmStatic
    @BindingAdapter("setCodeSentBtn",
        "setCodeEt",
        "setAuthNtTv",
        "setTimeoutFlag",
        "setCodeSentFlag")
    fun setEnableTimeout(
        authBtn: Button,
        codeSendBtn: Button,
        codeEt: EditText,
        authNtTv: TextView,
        timeoutFlag: Boolean,
        codeSentFlag: Boolean,
    ) {
        if (timeoutFlag && codeSentFlag) {
            codeSendBtn.isEnabled = true
            codeSendBtn.setTextColor(codeSendBtn.context.getColor(R.color.green_blue))
            authBtn.isEnabled = false
            authBtn.setTextColor(authBtn.context.getColor(R.color.brownish_grey))

            codeEt.isEnabled = false
            authNtTv.text = "입력시간이 초과되었습니다. 인증요청을 재시도 해주세요."
            authNtTv.setTextColor(authNtTv.context.getColor(R.color.red))

        } else if (!timeoutFlag && codeSentFlag) {
            codeSendBtn.isEnabled = false
            codeSendBtn.setTextColor(codeSendBtn.context.getColor(R.color.brownish_grey))
            authBtn.isEnabled = true
            authBtn.setTextColor(authBtn.context.getColor(R.color.green_blue))
            codeEt.isEnabled = true
            authNtTv.text = ""
            authNtTv.setTextColor(authNtTv.context.getColor(R.color.green_blue))
        }

    }

    //인증 성공 여부 enable 관리
    @JvmStatic
    @BindingAdapter("setCodeAuthBtn", "setCodeNtTv", "setPassAuthFlag")
    fun setEnablePassAuth(
        codeEt: EditText,
        codeAuthBtn: Button,
        codeNtTv: TextView,
        passAuthFlag: Int,
    ) {
        if (codeEt.text.isNotEmpty()) {
            when (passAuthFlag) {
                0 -> {
                    //인증 성공 시
                    codeNtTv.text = "인증에 성공하였습니다."
                    codeNtTv.setTextColor(codeNtTv.context.getColor(R.color.green_blue))
                    codeAuthBtn.isEnabled = false
                    codeAuthBtn.setTextColor(codeAuthBtn.context.getColor(R.color.brownish_grey))
                    codeEt.isEnabled = false
                }
                1 -> {
                    //인증 실패 시
                    codeNtTv.text = "인증번호가 틀렸습니다. 다시 입력해주세요."
                    codeNtTv.setTextColor(codeNtTv.context.getColor(R.color.red))
                    codeAuthBtn.isEnabled = true
                    codeAuthBtn.setTextColor(codeAuthBtn.context.getColor(R.color.green_blue))
                    codeEt.isEnabled = true
                }
                2 -> {
                    codeNtTv.text = "동일한 기기에서 너무 많은 요청이 수신되었습니다. 나중에 다시 시도하세요."
                    codeNtTv.setTextColor(codeNtTv.context.getColor(R.color.red))
                    codeAuthBtn.isEnabled = true
                    codeAuthBtn.setTextColor(codeAuthBtn.context.getColor(R.color.green_blue))
                    codeEt.isEnabled = true
                }
                else -> {
                    codeNtTv.text = ""
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setEOverlapBtn", "setEmailEt", "setEOverlapFlag")
    fun setEnableEOverlap(
        overlapNtTv: TextView,
        overlapBtn: Button,
        userInputEt: EditText,
        overlapFlag: Int,
    ) {
        if (overlapFlag == 0) {
            //통과
            overlapBtn.text = "인증"
            overlapNtTv.text = "사용가능한 이메일입니다. 인증 과정을 완료해주세요."
            userInputEt.isEnabled = false
            overlapNtTv.setTextColor(overlapNtTv.context.getColor(R.color.green_blue))
        } else if (overlapFlag == 1) {
            overlapBtn.isEnabled = true
            overlapBtn.setTextColor(overlapBtn.context.getColor(R.color.green_blue))
            overlapBtn.text = "중복"
            overlapNtTv.text = "이미 사용중인 이메일입니다."
            overlapNtTv.setTextColor(overlapNtTv.context.getColor(R.color.red))
            userInputEt.isEnabled = true
        }
    }

    @JvmStatic
    @BindingAdapter("setNOverlapBtn", "setNicknameEt", "setNOverlapFlag")
    fun setEnableNOverlap(
        overlapNtTv: TextView,
        overlapBtn: Button,
        userInputEt: EditText,
        overlapFlag: Int,
    ) {
        if (overlapFlag == 0) {
            overlapNtTv.text = "사용가능한 닉네임입니다."
            overlapNtTv.setTextColor(overlapNtTv.context.getColor(R.color.green_blue))
            userInputEt.isEnabled = false
            overlapBtn.isEnabled = false
            overlapBtn.setTextColor(overlapBtn.context.getColor(R.color.brownish_grey))
        } else if (overlapFlag == 1) {
            overlapBtn.isEnabled = true
            overlapBtn.setTextColor(overlapBtn.context.getColor(R.color.green_blue))
            overlapNtTv.text = "이미 사용중인 닉네임입니다."
            overlapNtTv.setTextColor(overlapNtTv.context.getColor(R.color.red))
            userInputEt.isEnabled = true
        }

    }

    @JvmStatic
    @BindingAdapter("setOnTextView", "setOffTextView")
    fun setSwitchText(
        switch: SwitchCompat,
        onTextView: TextView,
        offTextView: TextView,
    ) {
        onTextView.gravity = Gravity.CENTER_VERTICAL
        offTextView.gravity = Gravity.CENTER_VERTICAL
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onTextView.setText(R.string.off)
                onTextView.visibility = View.VISIBLE
                offTextView.visibility = View.GONE
            } else {

                offTextView.setText(R.string.on)
                offTextView.visibility = View.VISIBLE
                onTextView.visibility = View.GONE
            }
        }
    }

    //phone Number Regex
    private fun matchPhoneNumberRegex(phoneNumber: String): Boolean {
        val regex = "^\\d{3}-\\d{4}-\\d{4}\$"
        return phoneNumber.matches(regex.toRegex())
    }

    //email Regex
    private fun matchEmailRegex(email: String): Boolean {
        val patterns = Patterns.EMAIL_ADDRESS
        return email.matches(patterns.toRegex())
    }

    //pwd Regex -> 영문자, 특수문자, 숫자를 포함해 8자리 이상 20자리 미만"
    private fun matchPwdRegex(pwd: String): Boolean {
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*#?&])[A-Za-z\\d\$@\$!%*#?&]{8,20}"
        return pwd.matches(regex.toRegex())
    }
}
