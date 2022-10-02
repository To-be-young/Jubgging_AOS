package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PathwayResponse
import com.example.jubgging.repository.PathwayRepositoryImpl
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
                    Log.d("pathway_sucess", "${it.data[0].latitude}")
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