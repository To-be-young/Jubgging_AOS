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

    private var _totalTime = MutableLiveData<Int>()
    val totalTime: LiveData<Int>
        get() = _totalTime

    private var _totalDistance = MutableLiveData<Double>()
    val totalDistance: LiveData<Double>
        get() = _totalDistance

    init {
        _nickname.value = ""
        _totalTime.value = 0
        _totalDistance.value = 0.0
    }

    fun updateUserNickname(inputNickname: String) {
        if (inputNickname.isNotEmpty()) {
            _nickname.value = inputNickname
        }
    }

    fun updatePloggingTotalData(time: Int, distance: Double) {
        _totalTime.value = time
        _totalDistance.value = distance
    }


    @SuppressLint("CheckResult")
    fun getUserNickname() {
        userRepository.getUserNicknameEmail().subscribeBy(
            onSuccess = {
                if (it.isSuccess()) {
                    updateUserNickname(it.data.nickname)
                }
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    @SuppressLint("CheckResult")
    fun getPloggingTotalData() {
        userRepository.getPloggingTotalData().subscribeBy(
            onSuccess = {
                if (it.isSuccess()) {
                    updatePloggingTotalData(it.data.activityTime, it.data.distance)
                }
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}
