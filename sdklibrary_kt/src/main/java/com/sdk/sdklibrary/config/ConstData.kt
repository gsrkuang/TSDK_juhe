package com.sdk.sdklibrary.config


/**
 * @author colin
 * Date:2023-02-08
 * 常量文件
 */
object ConstData {
    const val LOGIN_SUCCESS = "登录成功"
    const val LOGIN_CANCEL = "登录取消"
    const val LOGIN_FAILURE = "登录失败"
    const val LOGOUT_SUCCESS = "登出成功"
    const val LOGOUT_CANCEL = "登出取消"
    const val LOGOUT_FAILURE = "登出失败"
    const val INIT_SUCCESS = "初始化成功"
    const val INIT_FAILURE = "初始化失败"
    const val REGIST_SUCCESS = "注册成功"
    const val REGIST_FAILURE = "注册失败"
    const val PHONECODE_SUCCESS = "获取验证码成功"
    const val PHONECODE_FAILURE = "获取验证码失败"
    const val PAY_SUCCESS = "支付成功"
    const val PAY_FAILURE = "支付失败"
    const val PAY_CANCEL = "支付取消"
    const val RESET_SUCCESS = "密码重置成功"
    const val RESET_FAILURE = "密码重置失败"
    const val RESET_CANCEL = "密码重置取消"
    const val PASS_CHANGE_SUCCESS = "修改密码成功"
    const val PASS_CHANGE_FAILURE = "修改密码失败"
    const val PAYCODE_SUCCESS = "获取PayCode成功"
    const val PAYCODE_FAILURE = "获取PayCode失败"
    const val PAYCODE_CANCEL = "获取PayCode取消"
    const val PHONE_EXIST = "手机号码存在"
    const val PHONE_NOTEXIST = "手机号码不存在"
    const val ONEKEY_ACCOUNT_DIGITS = 10 //一键注册的随机账号位数 ,其中两位小写字母再前，后面补全数字
    const val ONEKEY_PASSWORD_DIGITS = 10 //一键注册的随机账号位数
    const val BIND_SUCCESS = "手机绑定成功"
    const val BIND_FAILURE = "手机绑定失败"
    const val BINDID_SUCCESS = "身份证绑定成功"
    const val BINDID_FAILURE = "身份证绑定失败"
    const val USERINFO_SUCCESS = "获取用户数据成功"
    const val USERINFO_FAILURE = "获取用户数据失败"
    const val USERINFO_TOKEN_FAILURE = "获取用户数据Token失效"
}
