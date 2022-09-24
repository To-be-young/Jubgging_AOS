package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PloggingResponse
import com.example.jubgging.repository.PloggingRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class CleanhouseViewModel : ViewModel(){

    private val ploggingRepository = PloggingRepositoryImpl()

    private var _liveFlag = MutableLiveData<Boolean>()
    val liveFlag : LiveData<Boolean>
        get() = _liveFlag

    private var _PloggingData : MutableLiveData<BaseResponse<List<PloggingResponse>>> = MutableLiveData<BaseResponse<List<PloggingResponse>>>()
    var PloggingData :  MutableLiveData<BaseResponse<List<PloggingResponse>>>
        get() = _PloggingData
    init {
        _liveFlag.value = false
        PloggingData = MutableLiveData<BaseResponse<List<PloggingResponse>>>()
    }

    fun updateLiveFlag(){
        _liveFlag.value = !liveFlag.value!!
    }

    @SuppressLint("CheckResult")
    fun plogging_req(ploggingRequest: PloggingRequest, showToast: (msg:String) -> Unit) {
        ploggingRepository.plogging_req(ploggingRequest).subscribeBy(
            onSuccess = {
                if (it.success) {
                    showToast("성공했습니다.")
                } else {
                    showToast("실패 : ${it.msg}")
                }
            },
            onError = {
                it.printStackTrace()
            }

        )
    }

    @SuppressLint("CheckResult")
    fun plogging_res(useremail : String, showToast: (msg:String) -> Unit) {
        ploggingRepository.plogging_res(useremail).subscribeBy(
            onSuccess = {
                if (it.success) {
                    showToast("성공했습니다.")
                    Log.d("plogging_res", "${it.data.get(0)}")
                    PloggingData.value = it
                    Log.d("PloggingData", "${PloggingData.value!!.data}")

                } else {
                    showToast("실패 : ${it}")
                }
            },
            onError = {
                it.printStackTrace()
            }

        )
    }
}