package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jubgging.model.HistoryGroup
import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.paging.PagingRepository
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

    private var PloggingData : LiveData<PagingData<HistoryGroup>>? = null

    @SuppressLint("CheckResult")
    fun sendPloggingResult(ploggingRequest: PloggingRequest, showToast: (msg:String) -> Unit) {
        ploggingRepository.sendPloggingResult(ploggingRequest).subscribeBy(
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

    fun getList(): LiveData<PagingData<HistoryGroup>> {
        val newResultLiveData: LiveData<PagingData<HistoryGroup>> =
            PagingRepository(ApiClient.api).getCommunities().cachedIn(viewModelScope)
        PloggingData = newResultLiveData
        return newResultLiveData
    }



}