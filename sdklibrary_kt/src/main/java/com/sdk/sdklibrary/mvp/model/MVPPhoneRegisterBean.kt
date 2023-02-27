package com.sdk.sdklibrary.mvp.model

/**
 * @author colin
 * Date:2023-02-08
 * 注册
 */
class MVPPhoneRegisterBean(var phoneNumber: String, var code: String) {

    override fun toString(): String {
        return "MVPPhoneRegisterBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                '}'
    }
}