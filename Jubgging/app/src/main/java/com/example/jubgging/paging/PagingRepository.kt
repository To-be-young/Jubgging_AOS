package com.example.jubgging.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.model.HistoryGroup
import com.example.jubgging.network.ApiInterface

class PagingRepository(private val apiInterface: ApiInterface){

    fun getCommunities():LiveData<PagingData<CommunityGroup>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {CommunityPagingSource(apiInterface)}
        ).liveData
    }
    fun getPloggingHistories():LiveData<PagingData<HistoryGroup>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {PloggingPagingSource(apiInterface)}
        ).liveData
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}