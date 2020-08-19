package com.jacknic.jetpack.paging.model

data class PageWrapper<Item : Any>(
    /**
     * curPage : 2
     * datas : []
     * offset : 20
     * over : false
     * pageCount : 403
     * size : 20
     * total : 8054
     */
    val curPage: Int = 0,
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
    val datas: List<Item> = emptyList()
)