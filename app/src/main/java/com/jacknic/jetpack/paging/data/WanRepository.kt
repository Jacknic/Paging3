package com.jacknic.jetpack.paging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jacknic.jetpack.paging.api.WanApi
import com.jacknic.jetpack.paging.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WanRepository {

    private val wanApi = Retrofit.Builder().baseUrl(WanApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(WanApi::class.java)

    @ExperimentalPagingApi
    fun getArticleList(): Flow<PagingData<Article>> {
        return Pager(
            initialKey = 1,
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { WanPagingSource(wanApi) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
