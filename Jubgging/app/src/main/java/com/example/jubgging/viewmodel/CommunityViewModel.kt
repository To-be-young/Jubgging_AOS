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
    val date: LiveData<String>
        get() = _date

    private var _sTime = MutableLiveData<String>()
    val sTime: LiveData<String>
        get() = _sTime

    private var _eTime = MutableLiveData<String>()
    val eTime: LiveData<String>
        get() = _eTime

    private var _postingSuccess = MutableLiveData<Boolean>()
    val postingSuccess: LiveData<Boolean>
        get() = _postingSuccess

    private var _communityTitle = MutableLiveData<String>()
    val communityTitle: LiveData<String>
        get() = _communityTitle

    private var _communityDesc = MutableLiveData<String>()
    val communityDesc: LiveData<String>
        get() = _communityDesc

    private var _communityNotice = MutableLiveData<String>()
    val communityNotice: LiveData<String>
        get() = _communityNotice

    private var _communityPlace = MutableLiveData<String>()
    val communityPlace: LiveData<String>
        get() = _communityPlace

    private var _communityCapacity = MutableLiveData<Int>()
    val communityCapacity: LiveData<Int>
        get() = _communityCapacity

    private var _communityParticipant = MutableLiveData<Int>()
    val communityParticipant: LiveData<Int>
        get() = _communityParticipant

    private var _communityEtc = MutableLiveData<String>()
    val communityEtc: LiveData<String>
        get() = _communityEtc

    init {
        _date.value = ""
        _sTime.value = ""
        _eTime.value = ""
        _postingSuccess.value = false
    }

    fun updateSTime(selectedSTime: String) {
        _sTime.value = selectedSTime
    }

    fun updateETime(selectedETime: String) {
        _eTime.value = selectedETime
    }

    fun updateDate(selectedDate: String) {
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
                _postingSuccess.value = it.success
                Log.d("TAG", "postingCommunity: ${it.success} ")
                Log.d("TAG", "postingCommunity: ${it.msg}")
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    @SuppressLint("CheckResult")
    fun getCommunityDetail(postId: Int) {
        communityRepository.getCommunityDetail(postId)
            .subscribeBy(
                onSuccess = {
                  if(it.success){
                      _communityTitle.value = it.data.title
                      Log.d("TAG", "getCommunityDetail: ${it.data.title}")
                      _communityDesc.value = it.data.content
                      _communityNotice.value = it.data.qualification
                      _communityCapacity.value = it.data.capacity
                      _communityParticipant.value = it.data.participant
                      _communityPlace.value = it.data.gatheringPlace
                      _communityEtc.value = it.data.etc




                  }else{
                      Log.d("TAG", "getCommunityDetail: ${it.msg}")
                  }
            },
                onError = {
                it.printStackTrace()
            })
    }


}