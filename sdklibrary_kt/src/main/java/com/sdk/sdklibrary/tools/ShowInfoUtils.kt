package com.sdk.sdklibrary.tools

import com.sdk.sdklibrary.base.BaseView
import com.sdk.sdklibrary.config.HttpUrlConstants

/**
 * Date:2023-01-12
 * Time:10:39
 * author:colin
 *
 * 用于给Activity后者Fragment展示Toast的信息
 * 网络请求返回code参数后，展示的Toast信息
 */
class ShowInfoUtils {
    companion object{
        //根据DataCode打印日志
        @JvmStatic
        fun LogDataCode(view: BaseView, dataCode: Int) {
            when (dataCode) {
                HttpUrlConstants.BZ_INVALID_PARAM -> view.showAppInfo("", "无效参数")
                HttpUrlConstants.BZ_INVALID_APP_ID -> view.showAppInfo("", "无效应用ID")
                HttpUrlConstants.BZ_INVALID_ACCOUNT -> view.showAppInfo("", "账号不合法")
                HttpUrlConstants.BZ_INVALID_TOKEN -> view.showAppInfo("", "Token已失效")
                HttpUrlConstants.BZ_ERROR -> view.showAppInfo("", "未知错误")
                HttpUrlConstants.BZ_ERROR_ACCOUNT_PASSWORD -> view.showAppInfo("", "账号或密码错误")
                HttpUrlConstants.BZ_ERROR_SIGN -> view.showAppInfo("", "签名错误")
                HttpUrlConstants.BZ_ERROR_CODE -> view.showAppInfo("", "验证码错误")
                HttpUrlConstants.BZ_FAILURE -> view.showAppInfo("", "账号已存在")
                HttpUrlConstants.BZ_CODE -> view.showAppInfo("", "获取验证码过于频繁，1分钟后再试")
                HttpUrlConstants.BZ_ERROR_ACCOUNT -> view.showAppInfo("", "账号错误或已锁定")
                HttpUrlConstants.BZ_ERROR_CHANGE_PASSWORD -> view.showAppInfo("", "原密码错误")
                HttpUrlConstants.BZ_LIMITED_APP -> view.showAppInfo("", "应用限制")
                HttpUrlConstants.BZ_LIMITED_APP_PAY -> view.showAppInfo("", "应用支付限制")
                HttpUrlConstants.BZ_LIMITED_APP_REG -> view.showAppInfo("", "应用注册限制")
                HttpUrlConstants.BZ_EXPIRED_APP -> view.showAppInfo("", "应用过期")
                HttpUrlConstants.BZ_LIMITED_UNDERAGE_LOGIN -> view.showAppInfo("", "未成年限制登录")
                HttpUrlConstants.BZ_LIMITED_SINGLE_PAY -> view.showAppInfo("", "单次充值额度限制")
                HttpUrlConstants.BZ_LIMITED_DAILY_ACCUMULATE_PAY -> view.showAppInfo("", "日累计充值额度限制")
                HttpUrlConstants.BZ_LIMITED_WEEKLY_ACCUMULATE_PAY -> view.showAppInfo("", "周累计充值额度限制")
                HttpUrlConstants.BZ_LIMITED_MONTHLY_ACCUMULATE_PAY -> view.showAppInfo("", "月累计充值额度限制")
                else -> {}
            }
        }
    }

}