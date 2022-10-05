package com.tobeyoung.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.tobeyoung.jubgging.repository.UserRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class UserInfoViewModel :ViewModel() {
    private val userRepository = UserRepositoryImpl()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email
    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    init {
        _email.value = ""
        _nickname.value = ""
    }
    private fun updateUserInfo(email:String, nickname:String){
        _email.value = email
        _nickname.value = nickname
    }

    @SuppressLint("CheckResult")
    fun getUserInfo(){
        userRepository.getUserInfo().subscribeBy(
           onSuccess =  {
               updateUserInfo(it.data.userId,it.data.nickname)
               Log.d("TAG", "getUserInfo: ${it.data.userId}")
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

}