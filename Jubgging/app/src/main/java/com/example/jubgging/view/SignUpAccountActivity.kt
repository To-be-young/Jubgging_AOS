package com.example.jubgging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivitySignupAccountBinding
import com.example.jubgging.viewmodel.SignUpViewModel

class SignUpAccountActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySignupAccountBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_account)
        binding.lifecycleOwner =this
        binding.signUpVm = viewModel
    }
}