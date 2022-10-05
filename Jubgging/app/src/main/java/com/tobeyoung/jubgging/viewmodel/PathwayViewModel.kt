package com.tobeyoung.jubgging.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tobeyoung.jubgging.network.data.response.BaseResponse
import com.tobeyoung.jubgging.network.data.response.PathwayResponse
import com.tobeyoung.jubgging.repository.PathwayRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class PathwayViewModel : ViewModel(){
    private val pathwayRepository = PathwayRepositoryImpl()

    private var _PathwayData : MutableLiveData<BaseResponse<List<PathwayResponse>>> = MutableLiveData<BaseResponse<List<PathwayResponse>>>()
    var PathwayData : MutableLiveData<BaseResponse<List<PathwayResponse>>>
        get() = _PathwayData
    init {
        PathwayData = MutableLiveData<BaseResponse<List<PathwayResponse>>>()
    }

    @SuppressLint("CheckResult")
    fun pathway(recordId : Int, showToast: (msg:String) -> Unit) {
        pathwayRepository.pathway(recordId).subscribeBy(
            onSuccess = {
                if (it.success) {
                    PathwayData.value = it
                    showToast("성공했습니다.")
                }else {
                    showToast("실패 : ${it}")
                }
            },
            onError = {
                it.printStackTrace()
            }

        )
    }
}