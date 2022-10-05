package com.tobeyoung.jubgging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tobeyoung.jubgging.model.HistoryGroup
import com.tobeyoung.jubgging.network.ApiInterface

class PloggingPagingSource(private val apiInterface: ApiInterface) :
    PagingSource<Int, HistoryGroup>() {
    override fun getRefreshKey(state: PagingState<Int, HistoryGroup>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryGroup> {
        return try {
            val pageIdx = params.key ?: 0
            val response = apiInterface.getPloggingList(page = pageIdx)
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