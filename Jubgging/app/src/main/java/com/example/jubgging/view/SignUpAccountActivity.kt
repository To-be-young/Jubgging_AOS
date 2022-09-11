package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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

        viewModel.updateEmailAuthFlag(false)

        var userId: String = ""
        var userPwd: String = ""

        binding.signupUserAuthCl.visibility = View.GONE
        binding.signupEmailCheckBtn.setOnClickListener {
            if (viewModel.emailOverlapFlag.value == 0) {
                viewModel.sendEmailCode(EmailRequest(binding.signupUserIdEt.text.toString()),
                    ::showToast).apply {
                    binding.signupUserIdEt.isEnabled = false
                    binding.signupUserAuthCl.visibility = View.VISIBLE
                }
            } else {
                viewModel.checkEmailOverlap(binding.signupUserIdEt.text.toString(),
                    ::setEmailOverlapNotice)
            }
        }

        binding.signupUserIdCodeAuthBtn.setOnClickListener {
            viewModel.verifyEmailCode(EmailCodeAuthRequest(binding.signupUserIdEt.text.toString(),
                binding.signupUserAuthEt.text.toString()), ::setVerifyNotice, ::setUIEnable)
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

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun setEmailOverlapNotice(flag: Int) {
        if (flag == 0) {
            binding.signupEmailCheckBtn.text = "인증"
            binding.signupUserIdNoticeTv.text = "사용가능한 이메일입니다. 인증 과정을 완료해주세요."
        } else {
            binding.signupUserIdNoticeTv.text = "이미 사용중인 이메일입니다."
        }

    }

    private fun setVerifyNotice(msg: String,flag: Boolean) {
        binding.signupUserIdAuthNoticeTv.text = msg
        if(flag){
            binding.signupUserIdAuthNoticeTv.setTextColor(this.getColor(R.color.green_blue))
        }else{
            binding.signupUserIdAuthNoticeTv.setTextColor(this.getColor(R.color.brownish_grey))
        }
    }

    private fun setUIEnable(flag: Boolean) {
        if(flag){
            binding.signupUserIdCodeAuthBtn.isEnabled = true
            binding.signupUserIdCodeAuthBtn.setTextColor(this.getColor(R.color.green_blue))

            binding.signupEmailCheckBtn.isEnabled = true
            binding.signupEmailCheckBtn.setTextColor(this.getColor(R.color.green_blue))

            binding.signupUserAuthEt.isEnabled = true
        }else{

            binding.signupUserIdCodeAuthBtn.isEnabled = false
            binding.signupUserIdCodeAuthBtn.setTextColor(this.getColor(R.color.brownish_grey))

            binding.signupEmailCheckBtn.isEnabled = false
            binding.signupEmailCheckBtn.setTextColor(this.getColor(R.color.brownish_grey))

            binding.signupUserAuthEt.isEnabled = false
        }

    }
}