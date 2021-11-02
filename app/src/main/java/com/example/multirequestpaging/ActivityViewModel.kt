package com.example.multirequestpaging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class ActivityViewModel(app: Application) : AndroidViewModel(app) {

    fun getMovie(): Flow<PagingData<Genre>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MultiRequestPagingSource(DataSource())
            }
        ).flow
    }
}