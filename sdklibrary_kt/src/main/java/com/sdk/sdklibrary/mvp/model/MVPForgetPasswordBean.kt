package com.sdk.sdklibrary.mvp.model

/**
 * @author colin
 * Date:2023-02-08
 * 注册
 */
class MVPForgetPasswordBean(var phoneNumber: String, var code: String, var newPassword: String) {

    override fun toString(): String {
        return "MVPForgetPasswordBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}'
    }
}