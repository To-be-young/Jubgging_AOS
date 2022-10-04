package com.example.jubgging.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.example.jubgging.databinding.ActivitySignupBinding
import com.example.jubgging.network.data.request.EmailCodeAuthRequest
import com.example.jubgging.network.data.request.EmailRequest
import com.example.jubgging.network.data.request.SignUpRequest
import com.example.jubgging.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding.lifecycleOwner = this
        binding.signUpVm = viewModel

        viewModel.codeSentFlag.observe(this, Observer {
            if(it == true){
                showToast("입력하신 이메일로 인증번호가 발송되었습니다.")
            }
        })


        binding.signupEmailCheckBtn.setOnClickListener {
            if (viewModel.eOverlapFlag.value == 0) {
                //email overlap 검사 통과 후 인증 과정
                viewModel.sendEmailCode(EmailRequest(binding.signupEmailEt.text.toString().trim()))
            } else {
                //email overlap 검사 통과 X
                viewModel.checkEmailOverlap(binding.signupEmailEt.text.toString().trim())
            }
        }

        binding.signupEmailAuthBtn.setOnClickListener {
            if(binding.signupEmailAuthEt.text.isNotEmpty()){
                viewModel.verifyEmailCode(EmailCodeAuthRequest(binding.signupEmailEt.text.toString().trim(), binding.signupEmailAuthEt.text.toString().trim()))
            }
        }

        binding.signupOverlapChkBtn.setOnClickListener {
            viewModel.checkNickNameOverlap(binding.signupNicknameEt.text.toString().trim())
        }

        // 이메일 인증 완료 시 && 비밀번호와 비밀번호 확인이 같을 때 버튼 활성화
        binding.signupAccountFinBtn.setOnClickListener {
            viewModel.signUp(signUpRequest = SignUpRequest(binding.signupEmailEt.text.toString(),binding.signupPwdEt.text.toString(),binding.signupNicknameEt.text.toString()),::showDialog)
        }


        dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_finish_signup)
            window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
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