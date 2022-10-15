package com.tobeyoung.jubgging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tobeyoung.jubgging.model.CommunityGroup
import com.tobeyoung.jubgging.network.ApiInterface
import com.tobeyoung.jubgging.network.data.response.CommunityJoinResponse

class MyCommunityJoinedPagingSource (private val apiInterface: ApiInterface) :
    PagingSource<Int,  CommunityJoinResponse<CommunityGroup>>() {
    override fun getRefreshKey(state: PagingState<Int, CommunityJoinResponse<CommunityGroup>>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,  CommunityJoinResponse<CommunityGroup>> {
        return try {
            val pageIdx = params.key ?: 0
            val response = apiInterface.getMyJoinCommunityList(page = pageIdx)
            val joinResponse = response.body()?.data?.content
//            val communities = response.body()?.data?.content
            LoadResult.Page(
                data = joinResponse!!,
                prevKey = if (pageIdx == 0) null else pageIdx - 1,
                nextKey = pageIdx + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}