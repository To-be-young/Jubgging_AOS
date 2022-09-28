package com.example.jubgging.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jubgging.repository.CommunityRepositoryImpl
import io.reactivex.rxkotlin.subscribeBy

class CommunityViewModel : ViewModel() {
    private val communityRepository = CommunityRepositoryImpl()

    @SuppressLint("CheckResult")
    fun getCommunityGroupList() {
        communityRepository.getCommunityGroupList(0).subscribeBy(
            onSuccess = {
                Log.d("TAG", "getCommunityGroupList: ${it.data.totalElements}")
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}