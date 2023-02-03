package com.example.sdklibrary.tools;

import com.example.sdklibrary.base.BaseView;
import com.example.sdklibrary.config.HttpUrlConstants;

/**
 * Date:2023-01-12
 * Time:10:39
 * author:colin
 *
 * 用于给Activity后者Fragment展示Toast的信息
 * 网络请求返回code参数后，展示的Toast信息
 */
public class ShowInfoUtils {

    //根据DataCode打印日志
    public static void LogDataCode(BaseView view, int dataCode) {

        switch (dataCode) {
            case HttpUrlConstants.BZ_INVALID_PARAM:
                view.showAppInfo("", "无效参数");
                break;
            case HttpUrlConstants.BZ_INVALID_APP_ID:
                view.showAppInfo("", "无效应用ID");
                break;
            case HttpUrlConstants.BZ_INVALID_ACCOUNT:
                view.showAppInfo("", "账号不合法");
                break;
            case HttpUrlConstants.BZ_INVALID_TOKEN:
                view.showAppInfo("", "Token已失效");
                break;
            case HttpUrlConstants.BZ_ERROR:
                view.showAppInfo("", "未知错误");
                break;
            case HttpUrlConstants.BZ_ERROR_ACCOUNT_PASSWORD:
                view.showAppInfo("", "账号或密码错误");
                break;
            case HttpUrlConstants.BZ_ERROR_SIGN:
                view.showAppInfo("", "签名错误");
                break;
            case HttpUrlConstants.BZ_ERROR_CODE:
                view.showAppInfo("", "验证码错误");
                break;
            case HttpUrlConstants.BZ_FAILURE:
                view.showAppInfo("", "账号已存在");
                break;
            case HttpUrlConstants.BZ_CODE:
                view.showAppInfo("", "获取验证码过于频繁，1分钟后再试");
                break;

            case HttpUrlConstants.BZ_ERROR_ACCOUNT:
                view.showAppInfo("", "账号错误或已锁定");
                break;
            case HttpUrlConstants.BZ_ERROR_CHANGE_PASSWORD:
                view.showAppInfo("", "原密码错误");
                break;

            case HttpUrlConstants.BZ_LIMITED_APP:
                view.showAppInfo("", "应用限制");
                break;

            case HttpUrlConstants.BZ_LIMITED_APP_PAY:
                view.showAppInfo("", "应用支付限制");
                break;

            case HttpUrlConstants.BZ_LIMITED_APP_REG:
                view.showAppInfo("", "应用注册限制");
                break;

            case HttpUrlConstants.BZ_EXPIRED_APP:
                view.showAppInfo("", "应用过期");
                break;

            default:
                break;
        }
    }



}
