package com.example.jubgging.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.jubgging.R
import com.example.jubgging.databinding.ActivitySignupAuthBinding
import com.example.jubgging.viewmodel.SignUpViewModel

class SignUpAuthActivity:AppCompatActivity() {
    private lateinit var binding: ActivitySignupAuthBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_auth)
        binding.lifecycleOwner =this
        binding.signUpVm = viewModel

        binding.signupSecondFinBtn.setOnClickListener {
            val intent = Intent(this,SignUpUserInfoActivity::class.java)
            startActivity(intent)
        }

    }
}