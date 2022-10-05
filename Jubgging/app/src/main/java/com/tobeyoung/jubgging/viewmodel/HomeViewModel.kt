package com.tobeyoung.jubgging.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tobeyoung.jubgging.repository.UserRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class HomeViewModel : ViewModel() {
    private val userRepository = UserRepositoryImpl()

    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    init {
        _nickname.value = ""
    }

    fun updateUserNickname(inputNickname: String) {
        if (inputNickname.isNotEmpty()) {
            _nickname.value = inputNickname
        }
    }


    @SuppressLint("CheckResult")
    fun getUserNickname() {
        userRepository.getUserNicknameEmail().subscribeBy(
            onSuccess = {
                if (it.isSuccess()) {
                    updateUserNickname(it.data.nickname)
                }
            },
            onError = {}
        )
    }

}
