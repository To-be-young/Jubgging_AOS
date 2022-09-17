package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivitySignupAccountBinding
import com.example.jubgging.network.data.request.EmailCodeAuthRequest
import com.example.jubgging.network.data.request.EmailRequest
import com.example.jubgging.viewmodel.SignUpViewModel

class SignUpAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupAccountBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_account)
        binding.lifecycleOwner = this
        binding.signUpVm = viewModel

        var userEmail: String = ""
        var userPwd: String = ""

        viewModel.codeSentFlag.observe(this, Observer {
            if(it == true){
                showToast("입력하신 이메일로 인증번호가 발송되었습니다.")
            }
        })

        binding.signupEmailCheckBtn.setOnClickListener {
            if (viewModel.overlapFlag.value == 0) {
                //email overlap 검사 통과 후 인증 과정
                viewModel.sendEmailCode(EmailRequest(binding.signupEmailEt.text.toString()))
            } else {
                //email overlap 검사 통과 X
                viewModel.checkEmailOverlap(binding.signupEmailEt.text.toString())
            }
        }

        binding.signupEmailAuthBtn.setOnClickListener {
            if(binding.signupEmailAuthEt.text.isNotEmpty()){
                viewModel.verifyEmailCode(EmailCodeAuthRequest(binding.signupEmailEt.text.toString(), binding.signupEmailAuthEt.text.toString()))
            }
        }

        // 이메일 인증 완료 시 && 비밀번호와 비밀번호 확인이 같을 때 버튼 활성화
        binding.signupAccountFinBtn.setOnClickListener {
            userEmail = binding.signupEmailEt.text.toString()
            userPwd = binding.signupPwdEt.text.toString()

            val intent = Intent(this, SignUpAuthActivity::class.java)
            intent.putExtra("userId", userEmail)
            intent.putExtra("userPwd", userPwd)
            startActivity(intent)

        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}