package com.sdk.sdklibrary.mvp.model.user

/**
 * Date:2023-01-15
 * Time:18:52
 * author:colin
 *
 * 暴露给SDK的用户接入的信息返回接口
 */
class SDKUserResult {
    var username: String? = null
    var token: String? = null
    var uid: String? = null

    override fun toString(): String {
        return "SDKUserResult{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", uid='" + uid + '\'' +
                '}'
    }
}