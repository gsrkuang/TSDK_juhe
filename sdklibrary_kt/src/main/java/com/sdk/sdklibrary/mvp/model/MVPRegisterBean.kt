package com.sdk.sdklibrary.mvp.model

/**
 * @author colin
 * Date:2023-02-08
 * 注册
 */
class MVPRegisterBean(var userName: String, var passWord: String) {

    override fun toString(): String {
        return "MVPRegisterBean{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}'
    }
}