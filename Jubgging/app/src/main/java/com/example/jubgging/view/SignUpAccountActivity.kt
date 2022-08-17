package com.example.jubgging.view

import android.content.Intent
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

        var userId:String = ""
        var userPwd:String = ""

        binding.signupFirstFinBtn.setOnClickListener {
            userId = binding.signupUserIdEt.text.toString()
            userPwd = binding.signupUserPwdEt.text.toString()

            val intent = Intent(this,SignUpAuthActivity::class.java)
            startActivity(intent)

        }




    }

}