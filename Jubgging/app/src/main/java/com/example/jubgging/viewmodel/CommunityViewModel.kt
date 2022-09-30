package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.ApiInterface
import com.example.jubgging.network.data.request.PostCommunityRequest
import com.example.jubgging.paging.PagingRepository
import com.example.jubgging.repository.CommunityRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class CommunityViewModel() : ViewModel() {
    private val communityRepository = CommunityRepositoryImpl()
    private var currentResultLiveData: LiveData<PagingData<CommunityGroup>>? = null

    private var _date = MutableLiveData<String>()
    val date:LiveData<String>
    get() = _date

    private var _sTime = MutableLiveData<String>()
    val sTime:LiveData<String>
        get() = _sTime

    private var _eTime = MutableLiveData<String>()
    val eTime:LiveData<String>
        get() = _eTime


    init {
        _date.value = ""
        _sTime.value = ""
        _eTime.value = ""
    }
    fun updateSTime(selectedSTime:String){
        _sTime.value = selectedSTime
    }
    fun updateETime(selectedETime:String){
        _eTime.value = selectedETime
    }

    fun updateDate(selectedDate:String){
        _date.value = selectedDate
        Log.d("TAG", "updateDate: ${_date.value} ")
    }

    fun getList(): LiveData<PagingData<CommunityGroup>> {
        val newResultLiveData: LiveData<PagingData<CommunityGroup>> =
            PagingRepository(ApiClient.api).getCommunities().cachedIn(viewModelScope)
        currentResultLiveData = newResultLiveData
        return newResultLiveData
    }

    @SuppressLint("CheckResult")
    fun postingCommunity(postCommunityRequest: PostCommunityRequest) {
        communityRepository.postCommunity(postCommunityRequest).subscribeBy(
            onSuccess = {
                //Toast

                //moveToList

            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}