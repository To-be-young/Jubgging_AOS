package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivitySignupAccountBinding
import com.example.jubgging.network.c.C
import com.example.jubgging.viewmodel.SignUpViewModel
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpAccountActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySignupAccountBinding
    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_account)
        binding.lifecycleOwner = this
        binding.signUpVm = viewModel

        viewModel.updateEmailAuthFlag(false)

        var userId: String = ""
        var userPwd: String = ""


        //이메일 인증 및 중복 구현
        //1. 이메일 중복
        //1-1. null check O
        //1-2. regex check O
        //1-3. duplicate check
        //1-4-pass. btn text가 인증으로 change
        //2. 이메일 인증
        //2-1.email send & Toast message
        //2-2. response 확인 및 통과 여부에 따라 1로 다시 돌아가기













        binding.signupEmailAuthBtn.setOnClickListener {
            //이메일 인증 함수
            viewModel.updateEmailAuthFlag(true)
        }


        // 이메일 인증 완료 시 && 비밀번호와 비밀번호 확인이 같을 때 버튼 활성화
        binding.signupFirstFinBtn.setOnClickListener {
            userId = binding.signupUserIdEt.text.toString()
            userPwd = binding.signupUserPwdEt.text.toString()

            val intent = Intent(this, SignUpAuthActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("userPwd", userPwd)
            startActivity(intent)

        }
    }
}