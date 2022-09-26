package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.jubgging.network.data.request.LoginRequest
import com.example.jubgging.repository.SignUpRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy


class SignUpViewModel : ViewModel() {
    private val signUpRepository = SignUpRepositoryImpl()

    companion object {
        var token: String = ""
    }

    //login Api
    @SuppressLint("CheckResult")
    fun login(loginRequest: LoginRequest, moveToMain: () -> Unit, showToast: (tag: Int) -> Unit) {
        signUpRepository.login(loginRequest).subscribeBy(onSuccess = {
            if (it.code == 0) {
                updateToken(it.data.accessToken)

                moveToMain().apply {
                    showToast(0)
                }

            } else {
                showToast(1)
            }
        }, onError = {
            it.printStackTrace()
        })
    }

    fun updateToken(inputToken: String) {
        token = inputToken
    }
}