package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.repository.PloggingRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class CleanhouseViewModel : ViewModel(){

    private val ploggingRepository = PloggingRepositoryImpl()

    private var _liveFlag = MutableLiveData<Boolean>()
    val liveFlag : LiveData<Boolean>
        get() = _liveFlag

    init {
        _liveFlag.value = false
    }

    fun updateLiveFlag(){
        _liveFlag.value = !liveFlag.value!!
    }

    @SuppressLint("CheckResult")
    fun plogging(ploggingRequest: PloggingRequest, showToast: (msg:String) -> Unit) {
        ploggingRepository.plogging(ploggingRequest).subscribeBy(
            onSuccess = {
                if (it.success) {
                    showToast("성공했습니다.")
                } else {
                    //회원가입 실패 시 예외처리 필요
                    showToast("실패 : ${it.msg}")
                }
            },
            onError = {
                it.printStackTrace()
            }

        )
    }
}