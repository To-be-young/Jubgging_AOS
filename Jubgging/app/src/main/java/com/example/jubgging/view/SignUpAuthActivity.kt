package com.example.jubgging.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivitySignupAuthBinding
import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.viewmodel.SignUpViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class SignUpAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupAuthBinding
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var dialog:Dialog
    var verificationId = ""


    //callbacks
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        }

        override fun onVerificationFailed(e: FirebaseException) {
        }

        //SMS 인증 코드 발송 시
        override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
            this@SignUpAuthActivity.verificationId = verificationId
            viewModel.updateCodeSentFlag(true)
            showSendSMSCodeToast()
            setEnableSendCodeBtn(true)
            // 유효시간 내 입력 및 버튼 누른 후 인증 , 유효시간 지나면 안내 O
            viewModel.phoneCodeTimerStart(::setPhoneCodeNotice)

            binding.signupPnumAuthBtn.isEnabled = true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_auth)
        binding.lifecycleOwner = this
        binding.signUpVm = viewModel

        //flag 초기화
        viewModel.updateCodeSentFlag(false)

        val userId = intent.getStringExtra("userId")
        val userPwd = intent.getStringExtra("userPwd")
        var phoneNumber: String = ""


        binding.signupPhoneNumberEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_finish_signup)
            window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }


        binding.signupFinBtn.setOnClickListener {
            viewModel.signUp(signUpRequest = SignUpRequest(userId!!,
                userPwd!!,
                binding.signupUserNicknameEt.text.toString(),
                binding.signupPhoneNumberEt.text.toString()), ::showDialog)
        }


        //전화번호 인증 check
        // 전화번호 입력 -> 인증 버튼 활성화 O
        // regex O
        // 국제 전화번호 적용 O
        // 인증번호 발송버튼 누른 후 -> Timer 시작 O, timer 규격 수정 필요 O

        // 인증 완료시 다음단계 버튼 활성화 O

        binding.signupOverlapChkBtn.setOnClickListener {
            viewModel.checkNickNameOverlap(binding.signupUserNicknameEt.text.toString(),
                ::setNicknameNotice)
        }

        //발송 버튼
        binding.signupSendSmsBtn.setOnClickListener {
            //전화번호 formatting
            phoneNumber = setPhoneNumber(binding.signupPhoneNumberEt.text.toString())
            //문자 전송
            viewModel.sendSMSCode(phoneNumber, this, callbacks)
        }
        //인증확인 버튼
        binding.signupPnumAuthBtn.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(verificationId,
                binding.signupAuthCodeEt.text.toString())
            viewModel.verifySMSCode(credential, this, ::setPhoneCodeNotice)
        }


    }


    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    private fun setNicknameNotice(message: String, flag: Boolean) {
        binding.signupUserNicknameNtTv.text = message
        if (flag) {
            binding.signupUserNicknameNtTv.setTextColor(this.getColor(R.color.green_blue))
            binding.signupUserNicknameEt.isEnabled = false
            binding.signupOverlapChkBtn.isEnabled = false
            binding.signupOverlapChkBtn.setTextColor(this.getColor(R.color.brownish_grey))

        } else {
            binding.signupUserNicknameNtTv.setTextColor(this.getColor(R.color.red))
        }
    }

    private fun setEnableSendCodeBtn(flag: Boolean) {
        if (flag) {
            binding.signupSendSmsBtn.isEnabled = false
            binding.signupSendSmsBtn.setTextColor(this.getColor(R.color.brownish_grey))
            binding.signupPhoneNumberEt.isEnabled = false
        }
    }

    private fun setPhoneCodeNotice(message: String, flag: Boolean) {
        binding.signupPnumAuthCodeNtTv.text = message
        //인증 성공시 true
        //실패 시 false
        if (flag) {
            binding.signupPnumAuthCodeNtTv.setTextColor(this.getColor(R.color.green_blue))
            binding.signupPnumAuthBtn.isEnabled = false
            binding.signupPnumAuthBtn.setTextColor(this.getColor(R.color.brownish_grey))
        } else {
            binding.signupPnumAuthCodeNtTv.setTextColor(this.getColor(R.color.red))
            binding.signupPhoneNumberEt.isEnabled = true
            binding.signupSendSmsBtn.isEnabled = true
            binding.signupSendSmsBtn.setTextColor(this.getColor(R.color.green_blue))
        }
    }

    private fun showSendSMSCodeToast() {
        Toast.makeText(this, "인증코드가 발송되었습니다. 60초 내에 입력해주세요.", Toast.LENGTH_LONG).show()
    }


    private fun setPhoneNumber(input: String): String {
        return "+82${input.substring(0, 3)}${input.substring(4, 8)}${input.substring(9, 13)}"
    }
    private fun showDialog(){
        val nicknameTv = dialog.findViewById<TextView>(R.id.ds_nickname_tv)
        nicknameTv.text = binding.signupUserNicknameEt.text.toString()
        dialog.show()
        dialog.findViewById<Button>(R.id.ds_move_login_btn).setOnClickListener {
            moveToLogin()
        }
    }
}