package com.tobeyoung.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tobeyoung.jubgging.model.CommunityGroup
import com.tobeyoung.jubgging.network.ApiClient
import com.tobeyoung.jubgging.network.data.request.PostCommunityRequest
import com.tobeyoung.jubgging.paging.PagingRepository
import com.tobeyoung.jubgging.repository.CommunityRepositoryImpl
import com.tobeyoung.jubgging.repository.UserRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy
import java.text.SimpleDateFormat
import java.util.*

class CommunityViewModel() : ViewModel() {
    private val communityRepository = CommunityRepositoryImpl()
    private var currentResultLiveData: LiveData<PagingData<CommunityGroup>>? = null

    private val userRepository = UserRepositoryImpl()

    private var _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    private var _sTime = MutableLiveData<String>()
    val sTime: LiveData<String>
        get() = _sTime

    private var _eTime = MutableLiveData<String>()
    val eTime: LiveData<String>
        get() = _eTime


    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    private var _postId = MutableLiveData<Int>()
    val postId:LiveData<Int>
        get() = _postId

    private var _communityDate = MutableLiveData<String>()
    val communityDate: LiveData<String>
        get() = _communityDate

    private var _communitySTime = MutableLiveData<String>()
    val communitySTime: LiveData<String>
        get() = _communitySTime

    private var _communityETime = MutableLiveData<String>()
    val communityETime: LiveData<String>
        get() = _communityETime


    private var _communityNickname = MutableLiveData<String>()
    val communityNickname: LiveData<String>
        get() = _communityNickname

    private var _communityTitle = MutableLiveData<String>()
    val communityTitle: LiveData<String>
        get() = _communityTitle

    private var _communityDesc = MutableLiveData<String>()
    val communityDesc: LiveData<String>
        get() = _communityDesc

    private var _communityNotice0 = MutableLiveData<String>()
    val communityNotice0: LiveData<String>
        get() = _communityNotice0

    private var _communityNotice1 = MutableLiveData<String>()
    val communityNotice1: LiveData<String>
        get() = _communityNotice1

    private var _communityNotice2 = MutableLiveData<String>()
    val communityNotice2: LiveData<String>
        get() = _communityNotice2

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

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email



    init {
        _date.value = ""
        _sTime.value = ""
        _eTime.value = ""
        _isSuccess.value = false
        _eTime.value = ""
        _nickname.value = ""
        _email.value = ""
        _postId.value = 0
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
    fun updatePostId(inputPostId:Int){
        _postId.value = inputPostId
    }

    fun updateDetailData(
        title: String,
        desc: String,
        notice0: String,
        notice1: String,
        notice2: String,
        place: String,
        capacity: Int,
        participant: Int,
        etc: String,
        date: String,
        sTime: String,
        eTime: String,
        nickname: String,
    ) {
        _communityNickname.value = nickname
        _communityTitle.value = title
        _communityDesc.value = desc
        _communityNotice0.value = notice0
        _communityNotice1.value = notice1
        _communityNotice2.value = notice2
        _communityPlace.value = place
        _communityCapacity.value = capacity
        _communityParticipant.value = participant
        _communityDate.value = date
        _communitySTime.value = sTime
        _communityETime.value = eTime
        _communityEtc.value = etc

    }

    fun updateEmailNickname(email: String, nickname: String) {
        _email.value = email
        _nickname.value = nickname
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
                _isSuccess.value = it.success
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
                    if (it.success) {
                        _communityNickname.value = it.data.nickname!!
                        _communityTitle.value = it.data.title!!
                        _communityDesc.value = it.data.content!!
                        _communityNotice0.value = it.data.qualification!![0]
                        _communityNotice1.value = it.data.qualification!![1]
                        _communityNotice2.value = it.data.qualification!![2]
                        _communityCapacity.value = it.data.capacity!!
                        _communityParticipant.value = it.data.participant!!
                        _communityPlace.value = it.data.gatheringPlace!!
                        _communityEtc.value = it.data.etc!!
                        _communityDate.value = formattingDate(it.data.gatheringTime!!)
                        _communitySTime.value = formattingTime(it.data.gatheringTime)
                        _communityETime.value = formattingTime(it.data.endingTime!!)


                    } else {
                        Log.d("TAG", "getCommunityDetail: ${it.msg}")
                    }
                },
                onError = {
                    it.printStackTrace()
                })
    }

    @SuppressLint("CheckResult")
    fun getUserNicknameEmail() {
        userRepository.getUserNicknameEmail().subscribeBy(
            onSuccess = {
                if (it.success) {
                    updateEmailNickname(it.data.userId, it.data.nickname)
                } else {
                    Log.d("TAG", "getUserNicknameEmail: failed")
                }
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    @SuppressLint("CheckResult")
    fun joinCommunity(postId: Int) {
        communityRepository.joinCommunity(postId).subscribeBy(
            onSuccess = {
                _isSuccess.value = it.success
            },
            onError = {
                it.printStackTrace()
            }
        )
    }


    private fun formattingDate(inputDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = simpleDateFormat.parse(inputDate) as Date
        return simpleDateFormat.format(date)
    }

    private fun formattingTime(inputTime: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val date = simpleDateFormat.parse(inputTime) as Date
        return SimpleDateFormat("HH:mm", Locale.KOREA).format(date)
    }

}