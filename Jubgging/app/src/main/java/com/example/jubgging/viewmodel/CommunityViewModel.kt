package com.example.jubgging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.ApiClient
import com.example.jubgging.network.ApiInterface
import com.example.jubgging.paging.PagingRepository

class CommunityViewModel() : ViewModel() {

    private var currentResultLiveData: LiveData<PagingData<CommunityGroup>>? = null

    fun getList(): LiveData<PagingData<CommunityGroup>> {
        val newResultLiveData: LiveData<PagingData<CommunityGroup>> =
            PagingRepository(ApiClient.api).getCommunities().cachedIn(viewModelScope)
        currentResultLiveData = newResultLiveData
        return newResultLiveData
    }
}