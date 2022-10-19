package com.tobeyoung.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityLoginBinding
import com.tobeyoung.jubgging.network.data.request.LoginRequest
import com.tobeyoung.jubgging.viewmodel.SignUpViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.signUpVm = viewModel



        binding.loginFinBtn.setOnClickListener {
            if (binding.loginUserEmailEt.text.toString()
                    .isNotEmpty() && binding.loginUserPwdEt.text.toString().isNotEmpty()
            ) {
                viewModel.login(LoginRequest(binding.loginUserEmailEt.text.toString().trim(),
                    binding.loginUserPwdEt.text.toString().trim()), ::moveToMain, ::showToast)

            } else {
                showToast(2)
            }

        }


        binding.loginSignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }

    private fun showToast(tag: Int) {
        when (tag) {
            0 -> Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(this, "계정이 존재하지 않거나 이메일 또는 비밀번호가 정확하지 않습니다.", Toast.LENGTH_SHORT)
                .show()
            2 -> Toast.makeText(this, "아이디와 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        finish()
        startActivity(intent)
    }
}