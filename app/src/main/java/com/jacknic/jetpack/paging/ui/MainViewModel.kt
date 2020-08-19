package com.jacknic.jetpack.paging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jacknic.jetpack.paging.data.WanRepository
import com.jacknic.jetpack.paging.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * 主页面 VM
 */
class MainViewModel : ViewModel() {
    private val repository = WanRepository()
    private var pageFlow: Flow<PagingData<Article>>? = null

    @ExperimentalPagingApi
    fun articlePageFlow(): Flow<PagingData<Article>> {
        if (pageFlow != null) {
            return pageFlow as Flow<PagingData<Article>>
        }
        return repository.getArticleList().cachedIn(viewModelScope).also { pageFlow = it }
    }

}