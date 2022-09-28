package com.example.jubgging.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jubgging.model.CommunityGroup
import com.example.jubgging.network.ApiInterface

class CommunityPagingSource(private val apiInterface: ApiInterface) :
    PagingSource<Int, CommunityGroup>() {
    override fun getRefreshKey(state: PagingState<Int, CommunityGroup>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityGroup> {
        return try {
            val pageIdx = params.key ?: 0
            val response = apiInterface.getCommunityList(page = pageIdx)
            val communities = response.body()?.data?.content
            LoadResult.Page(
                data = communities!!,
                prevKey = if (pageIdx == 0) null else pageIdx - 1,
                nextKey = pageIdx + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}