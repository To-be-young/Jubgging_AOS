package com.example.jubgging.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivityUserInfoBinding
import com.example.jubgging.viewmodel.UserInfoViewModel

class UserProfileActivity:AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private val viewModel: UserInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        binding.userInfoVm = viewModel
        binding.lifecycleOwner = this
        viewModel.getUserInfo()
    }

}