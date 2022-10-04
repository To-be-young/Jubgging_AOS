package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
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

class CleanhouseViewModel : ViewModel() {
    private val ploggingRepository = PloggingRepositoryImpl()
    private var PloggingData: LiveData<PagingData<HistoryGroup>>? = null

    private var _isSwitchOn = MutableLiveData<Boolean>()
    val isSwitchOn: LiveData<Boolean>
        get() = _isSwitchOn

    private var _distance = MutableLiveData<Double>()
    val distance: LiveData<Double>
        get() = _distance

    private var _cleanHouseMarkerObserve = MutableLiveData<Pair<Boolean, Double>>()
    val cleanHouseMarkerObserve: LiveData<Pair<Boolean, Double>>
        get() = _cleanHouseMarkerObserve

    init {
        _isSwitchOn.value = false
        _distance.value = 0.0
        _cleanHouseMarkerObserve.value = Pair(false, 1.0)
    }

    fun updateDistance(inputDistance: Double) {
        _distance.value = inputDistance
    }

    fun updateIsSwitchOn() {
        _isSwitchOn.value = !isSwitchOn.value!!
    }

    fun updateCleanHouseMarkerObserve(first_value: Boolean, second_value: Double) {
        _cleanHouseMarkerObserve.value = Pair(first_value, second_value)
    }

    @SuppressLint("CheckResult")
    fun sendPloggingResult(ploggingRequest: PloggingRequest, showToast: (msg: String) -> Unit) {
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
            PagingRepository(ApiClient.api).getPloggingHistories().cachedIn(viewModelScope)
        PloggingData = newResultLiveData
        return newResultLiveData
    }
}