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
import androidx.lifecycle.Observer
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
    private lateinit var dialog: Dialog
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
            // 유효시간 내 입력 및 버튼 누른 후 인증 , 유효시간 지나면 안내 O
            viewModel.phoneCodeTimerStart()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_auth)
        binding.lifecycleOwner = this
        binding.signUpVm = viewModel

        viewModel.codeSentFlag.observe(this, Observer {
            if (it == true) {
                showToast("입력하신 전화번호로 인증번호가 발송되었습니다.")
            }
        })

        val userId = intent.getStringExtra("userId")
        val userPwd = intent.getStringExtra("userPwd")
        var phoneNumber: String = ""


        binding.signupPnumEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())

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
                binding.signupNicknameEt.text.toString(),
                binding.signupPnumEt.text.toString()), ::showDialog)
        }

        binding.signupOverlapChkBtn.setOnClickListener {
            viewModel.checkNickNameOverlap(binding.signupNicknameEt.text.toString())
        }

        //발송 버튼
        binding.signupPnumCodeSendBtn.setOnClickListener {
            //전화번호 formatting
            phoneNumber = setPhoneNumber(binding.signupPnumEt.text.toString())
            //문자 전송
            viewModel.sendSMSCode(phoneNumber, this, callbacks)
            //버튼 막기
            it.isEnabled = false
            binding.signupPnumCodeSendBtn.setTextColor(this.getColor(R.color.brownish_grey))

        }
        //인증확인 버튼
        binding.signupPnumAuthBtn.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(verificationId,
                binding.signupPnumAuthEt.text.toString())
            viewModel.verifySMSCode(credential, this)
        }


    }


    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }


    private fun setPhoneNumber(input: String): String {
        return "+82${input.substring(0, 3)}${input.substring(4, 8)}${input.substring(9, 13)}"
    }

    private fun showDialog() {
        val nicknameTv = dialog.findViewById<TextView>(R.id.ds_nickname_tv)
        nicknameTv.text = binding.signupNicknameEt.text.toString()
        dialog.show()
        dialog.findViewById<Button>(R.id.ds_move_login_btn).setOnClickListener {
            moveToLogin()
            dialog.dismiss()
        }
    }
}