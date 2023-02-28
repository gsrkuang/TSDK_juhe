package com.sdk.sdklibrary.mvp.model

/**
 * Date:2023-01-05
 * Time:15:24
 * author:colin
 */
class ApiResponse<T> {
     var ts = 0
     var code = 0
     var msg: String? = null
     var data: T? = null
}