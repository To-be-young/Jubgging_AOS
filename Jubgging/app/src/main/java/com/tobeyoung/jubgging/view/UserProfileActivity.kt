package com.tobeyoung.jubgging.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tobeyoung.jubgging.R
import com.tobeyoung.jubgging.databinding.ActivityUserInfoBinding
import com.tobeyoung.jubgging.viewmodel.UserInfoViewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private val viewModel: UserInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        binding.userInfoVm = viewModel
        binding.lifecycleOwner = this
        viewModel.getUserInfo()

        binding.userInfoNicknameBtn.setOnClickListener {
            showToast("아직 준비 중인 기능입니다. 조금만 기다려주세요!")
        }
        binding.userInfoProfileUpdateBtn.setOnClickListener {
            showToast("아직 준비 중인 기능입니다. 조금만 기다려주세요!")
        }
        binding.userInfoPwdBtn.setOnClickListener {
            showToast("아직 준비 중인 기능입니다. 조금만 기다려주세요!")
        }
    }
    private fun showToast(msg:String) {
      Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}