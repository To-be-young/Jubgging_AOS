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
import com.example.jubgging.network.PloggingReceive
import com.example.jubgging.network.data.request.PloggingRequest
import com.example.jubgging.network.data.response.BaseResponse
import com.example.jubgging.network.data.response.PathwayResponse
import com.example.jubgging.network.data.response.PloggingResponse
import com.example.jubgging.paging.PagingRepository
import com.example.jubgging.repository.PloggingRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class CleanhouseViewModel : ViewModel(){

    private val ploggingRepository = PloggingRepositoryImpl()

    private var PloggingData : LiveData<PagingData<HistoryGroup>>? = null

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

    fun getList(): LiveData<PagingData<HistoryGroup>> {
        val newResultLiveData: LiveData<PagingData<HistoryGroup>> =
            PagingRepository(ApiClient.api).getCommunities().cachedIn(viewModelScope)
        Log.d("imsiiii", "${newResultLiveData}")
        PloggingData = newResultLiveData
        return newResultLiveData
    }



}