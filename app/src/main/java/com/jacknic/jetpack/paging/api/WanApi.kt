package com.jacknic.jetpack.paging.api

import com.jacknic.jetpack.paging.data.WanJson
import com.jacknic.jetpack.paging.model.Article
import com.jacknic.jetpack.paging.model.PageWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 玩Android 开放API
 *
 * https://wanandroid.com/blog/show/2
 */
interface WanApi {

    /**
     * 获取文章列表
     *
     * [pageNo] 页码
     * [cid] 分类ID
     */
    @GET("/article/list/{pageNo}/json")
    suspend fun listArticle(
        @Path("pageNo") pageNo: Int,
        @Query("cid") cid: Int?
    ): Response<WanJson<PageWrapper<Article>>>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}