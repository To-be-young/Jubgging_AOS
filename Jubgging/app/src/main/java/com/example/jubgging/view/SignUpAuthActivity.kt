package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivitySignupAuthBinding
import com.example.jubgging.viewmodel.SignUpViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class SignUpAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupAuthBinding
    private val viewModel: SignUpViewModel by viewModels()
    var verificationId = ""

    //callbacks
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        }

        override fun onVerificationFailed(e: FirebaseException) {
        }

        override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
            this@SignUpAuthActivity.verificationId = verificationId
            viewModel.updateCodeSentFlag(true)
            showToast(0)
            viewModel.timerStart(::showToast)
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

        var userId = intent.getStringExtra("userId")
        var userPwd = intent.getStringExtra("userPwd")
        var phoneNumber: String = ""


        binding.signupPhoneNumberEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())


        binding.signupSecondFinBtn.setOnClickListener {
            val intent = Intent(this, SignUpUserInfoActivity::class.java)
            startActivity(intent)
        }


        //전화번호 인증 check
        // 전화번호 입력 -> 인증 버튼 활성화
        // regex O
        // 국제 전화번호 적용 O
        // 인증번호 발송버튼 누른 후 -> Timer 시작 timer 규격

        // 유효시간 내 입력 및 버튼 누른 후 인증 , 유효시간 지나면 안내 O
        // 인증 완료시 다음단계 버튼 활성화
        
        //발송 버튼
        binding.signupSendSmsBtn.setOnClickListener {
            //전화번호 formatting
            phoneNumber = setPhoneNumber(binding.signupPhoneNumberEt.text.toString())
            //문자 전송
            viewModel.sendSMSCode(phoneNumber, this, callbacks)
        }
        //인증확인 버튼
        binding.signupPnumAuthBtn.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(verificationId, binding.signupAuthCodeEt.text.toString())
            viewModel.verifySMSCode(credential, this, ::showToast)
        }


    }

    private fun showToast(tag: Int) {
        when (tag) {
            0 -> Toast.makeText(this, "인증코드가 발송되었습니다. 60초 내에 입력해주세요.", Toast.LENGTH_LONG).show()
            1 -> Toast.makeText(this, "인증에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(this, "인증번호가 틀렸습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(this, "동일한 기기에서 너무 많은 요청이 수신되었습니다. 나중에 다시 시도하세요.", Toast.LENGTH_SHORT).show()
            4 -> Toast.makeText(this, "입력시간이 초과되었습니다. 인증 요청을 재시도 해주세요.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setPhoneNumber(input: String): String {
        return "+82${input.substring(0, 3)}${input.substring(4, 8)}${input.substring(9, 13)}"
    }

}