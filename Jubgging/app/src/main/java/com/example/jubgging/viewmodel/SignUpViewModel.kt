package com.example.jubgging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jubgging.repository.SignUpRepositoryImpl

class SignUpViewModel : ViewModel() {
    private val signUpRepository = SignUpRepositoryImpl()

}