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

        binding.signupUserAuthChkCl.visibility = View.GONE
        binding.signupEmailAuthBtn.setOnClickListener {
            if (viewModel.emailOverlapFlag.value == 0) {
                viewModel.sendEmailCode(EmailRequest(binding.signupUserIdEt.text.toString()),
                    ::showToast, ::setTimeoutEnable,::setReSentEtEnable).apply {
                    binding.signupUserIdEt.isEnabled = false
                    binding.signupUserAuthChkCl.visibility = View.VISIBLE
                }
            } else {
                viewModel.checkEmailOverlap(binding.signupUserIdEt.text.toString(),
                    ::setEmailOverlapNotice)
            }
        }

        binding.signupUserAuthCodeBtn.setOnClickListener {
            viewModel.verifyEmailCode(EmailCodeAuthRequest(binding.signupUserIdEt.text.toString(),
                binding.signupUserAuthChkEt.text.toString()),
                ::setVerifyNotice,
                ::setEmailAuthUIEnable)
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
            binding.signupEmailAuthBtn.text = "인증"
            binding.signupUseridNtTv.text = "사용가능한 이메일입니다. 인증 과정을 완료해주세요."
            binding.signupUseridNtTv.setTextColor(this.getColor(R.color.green_blue))

        } else {
            binding.signupUseridNtTv.text = "이미 사용중인 이메일입니다."
            binding.signupUseridNtTv.setTextColor(this.getColor(R.color.red))
        }
    }

    private fun setReSentEtEnable(){
        binding.signupUserAuthChkEt.isEnabled = true
    }
    //메일 보내면 sendBtn enable -> false
//Timeout 되면 sendBtn 다시 true && email Et는 enable false,
    private fun setTimeoutEnable(msg: String, flag: Boolean) {
        binding.signupUserAuthCodeNtTv.text = msg
        binding.signupUserIdEt.isEnabled = false
        binding.signupEmailAuthBtn.isEnabled = true
        binding.signupUserAuthChkEt.isEnabled = true
        binding.signupEmailAuthBtn.setTextColor(this.getColor(R.color.green_blue))
        binding.signupUserAuthCodeNtTv.setTextColor(this.getColor(R.color.red))
        binding.signupUserAuthChkEt.isEnabled = false
    }

    private fun setVerifyNotice(msg: String, flag: Boolean) {
        binding.signupUserAuthCodeNtTv.text = msg
        if (flag) {
            //성공 시
            binding.signupUserAuthCodeNtTv.setTextColor(this.getColor(R.color.green_blue))
        } else {
            //실패 시
            binding.signupUserAuthCodeNtTv.setTextColor(this.getColor(R.color.red))
        }
    }

    // 이메일 인증 성공 실패 관련 UI set
    private fun setEmailAuthUIEnable(flag: Boolean) {
        if (flag) {
            // 이메일 인증 실패시
            binding.signupUserAuthCodeBtn.isEnabled = true
            binding.signupUserAuthCodeNtTv.setTextColor(this.getColor(R.color.green_blue))

            binding.signupEmailAuthBtn.isEnabled = true
            binding.signupEmailAuthBtn.setTextColor(this.getColor(R.color.green_blue))

            binding.signupUserAuthChkEt.isEnabled = true
        } else {
            //이메일 인증 성공시
            binding.signupUserAuthCodeBtn.isEnabled = false
            binding.signupUserAuthCodeBtn.setTextColor(this.getColor(R.color.brownish_grey))

            binding.signupEmailAuthBtn.isEnabled = false
            binding.signupEmailAuthBtn.setTextColor(this.getColor(R.color.brownish_grey))

            binding.signupUserAuthChkEt.isEnabled = false
        }

    }
}