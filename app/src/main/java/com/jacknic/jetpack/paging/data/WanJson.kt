package com.jacknic.jetpack.paging.data

/**
 * 玩 Android 响应数据结构
 */
class WanJson<T> {
    var errorCode = 0
    var errorMsg = ""
    var data: T? = null
}