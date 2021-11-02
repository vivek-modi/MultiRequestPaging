package com.example.multirequestpaging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException

class MultiRequestPagingSource(private val dataSource: DataSource) : PagingSource<String, Genre>() {

    override fun getRefreshKey(state: PagingState<String, Genre>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Genre> {
        val key = params.key ?: ""

        return try {
            val data = when (params) {
                is LoadParams.Refresh -> {
                    dataSource.fetchInitialMovie()
                }
                is LoadParams.Append -> {
                    dataSource.fetchMovieBefore(key)
                }
                is LoadParams.Prepend -> null
            }

            LoadResult.Page(
                data = data.result,
                prevKey = null,
                nextKey = data?.nextKey,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}