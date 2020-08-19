package com.jacknic.jetpack.paging.data

import android.util.Log
import androidx.paging.PagingSource
import com.jacknic.jetpack.paging.api.WanApi
import com.jacknic.jetpack.paging.model.Article
import java.io.IOException

/**
 * 数据源
 *
 * @author Jacknic
 */
class WanPagingSource(private val wanApi: WanApi) : PagingSource<Int, Article>() {

    private val startIndex = 0
    private val tag = javaClass.simpleName

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageNo = params.key ?: startIndex
        return try {
            val response = wanApi.listArticle(pageNo, null)
            val wrapper = response.body()?.data
            if (wrapper != null) {
                Log.d(tag, "加载数据：第${pageNo}页 - ${wrapper.size}")
                LoadResult.Page(
                    data = wrapper.datas,
                    prevKey = if (pageNo > startIndex) pageNo - 1 else null,
                    nextKey = if (wrapper.over) null else pageNo + 1
                    // 显示数据列表大小
                    // , itemsBefore = wrapper.offset
                    // , itemsAfter = wrapper.total - wrapper.offset
                )
            } else {
                LoadResult.Error(IOException("加载数据出错"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

//    override val jumpingSupported = true
}