package com.sdk.sdklibrary.config

/**
 * @author colin
 * Date:2023-02-08
 * 全局状态码:
 */
object SDKStatusCode {
    const val SUCCESS = 0
    const val FAILURE = 1
    const val OTHER = 2
    const val CANCEL = 3
    const val PHONE_REGISTER_SUCCESS = 500
    const val PHONE_REGISTER_FAILURE = 501
    const val PHONE_REGISTER_OTHER = 503
    const val PHONE_REGISTER_CANCEL = 502
    const val PAY_SUCCESS = 200
    const val PAY_FAILURE = 201
    const val PAY_CANCEL = 202
    const val LOGOUT_SUCCESS = 801
    const val LOGOUT_FAILURE = 802
    const val LOGOUT_CANCEL = 803
    const val LOGOUT_OTHER = 884
}