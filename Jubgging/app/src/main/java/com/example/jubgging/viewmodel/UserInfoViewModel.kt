package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jubgging.repository.SignUpRepositoryImpl
import com.example.jubgging.repository.UserRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class UserInfoViewModel :ViewModel() {
    private val userRepository = UserRepositoryImpl()

    @SuppressLint("CheckResult")
    fun getUserInfo(){
        userRepository.getUserInfo().subscribeBy(
           onSuccess =  {
               Log.d("TAG", "getUserInfo: ${it.data.userId}")
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

}