package com.sdk.sdklibrary.mvp.model

/**
 * Date:2023-01-18
 * Time:16:52
 * author:colin
 */
class MVPChangePasswordBean(
    var oldPassword: String,
    var newPassword: String,
    var newConfirmPassword: String
) {

    override fun toString(): String {
        return "MVPChangePasswordBean{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newConfirmPassword='" + newConfirmPassword + '\'' +
                '}'
    }
}