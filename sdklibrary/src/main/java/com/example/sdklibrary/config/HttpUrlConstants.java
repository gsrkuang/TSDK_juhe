package com.example.sdklibrary.config;

/**
 * Created by cbl
 * URL_状态限定
 */

public final class HttpUrlConstants {

//    public static final String SDK_BASE_URL = "http://192.168.0.105:8983";
    public static final String SDK_BASE_URL = "https://xiyecode.free.svipss.top";

    public static final String COOKIE_DATA = "cookieData";

    //  请求成功，响应成功(!--接口成功--!)
    public static final String NET_OK = "netOK";
    //cookieData

    //  请求成功，响应失败(!--接口失败--!)
    public static final String NET_ON_FAILURE = "netFaiure";

    //------------!!!------------

    //  没有网络链接(!--客户端没有联网--!)
    public static final String NET_NO_LINKING = "请检查网络链接";

    //  后台服务器错误(!--服务器宕机--!)
    public static final String SERVER_ERROR = "啊哦~服务器去月球了";

    //10000错误系列
    public static final int BZ_INVALID_PARAM = 10000;//无效参数
    public static final int BZ_INVALID_APP_ID = 10001;//无效应用ID
    public static final int BZ_INVALID_ACCOUNT = 10102;//账号不合法
    public static final int BZ_INVALID_TOKEN = 10103;//Token已失效
    //12000错误系列
    public static final int BZ_ERROR = 12000; //未知错误
    public static final int BZ_ERROR_ACCOUNT_PASSWORD = 12001;//账号或密码错误

    public static final int BZ_ERROR_ACCOUNT = 12002;//账号错误或已锁定
    public static final int BZ_ERROR_CHANGE_PASSWORD = 12003;//修改密码错误
    public static final int BZ_ERROR_SIGN = 12100;//签名错误
    public static final int BZ_ERROR_CODE = 12200;//验证码错误

    public static final int BZ_CODE = 14200;//Frequently operation

    public static final int BZ_UNBIND = 15001;//Unbound phone: 手机号码未被绑定
//    public static final int BZ_UNBIND = 15100;//Unbound phone: 手机号码未被绑定

    //业务成功、失败
    public static final int BZ_SUCCESS = 20000; //成功
    public static final int BZ_FAILURE = 20001; //注册失败


    //登录url  POST
    //username，password(不用)
//    public static String getLoginUrl() {
//        return "https://www.wanandroid.com/user/login";
//    }

    //注册url  POST(不用)
    //username,password,repassword
//    public static String getRegisterUrl() {
//        return "https://www.wanandroid.com/user/register";
//    }

    //支付url  POST(不用)
    //username,password,repassword

    //用户协议Url
    public static String getAgreementUrl(){return SDK_BASE_URL+"/html/protocol/111111/user.html";};
    public static String getPrivacytUrl(){return SDK_BASE_URL+"/html/protocol/111111/privacy.html";};

    public static String getPayUrl() {
        return "https://xiyecode.free.svipss.top/api/paypal/pay";
    }

    //tsdk登录url  POST
    //account,password
    public static String getLoginUrl() {
        return SDK_BASE_URL+"/api/v2/account/login/account";
    }

    //tsdk一键登录url  POST
    //account,password
    public static String getOneKeyLoginUrl() {
        return SDK_BASE_URL+"/api/v2/account/login/rapid";
    }

    //tsdk ①验证码登录/忘记密码-获取验证码  POST
    //account,password
    public static String getPhoneCodeUrl() {
        return SDK_BASE_URL+"/api/v2/account/code/send";
    }

    //tsdk ②验证码登录/忘记密码url  POST
    //account,password
    public static String getPhoneCodeLoginUrl() { return SDK_BASE_URL+"/api/v2/account/login/phone"; }


    //tsdk注册url  POST
    //account,password
    public static String getRegisterUrl() { return SDK_BASE_URL+"/api/v2/account/register/account"; }

    //tsdk忘记密码url  POST
    //oldpassword,newpassword,newconfirmpassword
    public static String getChangePasswordUrl() { return SDK_BASE_URL+"/api/v2/account/password/update"; }

    //tsdk忘记密码url  POST
    //account,password
    public static String getFrogetUrl() { return SDK_BASE_URL+"/api/v2/account/password/reset"; }

    //tsdk检查手机号是否被绑定url  POST
    //phone
    public static String getBindPhoneCheckUrl() { return SDK_BASE_URL+"/api/v2/account/bind/phone/check"; }

    //tsdk 绑定手机号url  POST
    //phone 验证码
    public static String getBindPhoneUrl() { return SDK_BASE_URL+"/api/v2/account/bind/phone"; }



    //tsdk登出url  POST
    //account,password
    public static String getLogoutUrl() {
        return SDK_BASE_URL+"/api/v2/account/logout";
    }

    //tsdk获取用户信息url  POST
    //请求头appId,ticket
    public static String getUserInfoUrl() { return SDK_BASE_URL+"/api/v2/account/info"; }
    //tsdk支付url alipay  POST
    public static String getPayUrl_alipay() { return SDK_BASE_URL+"/api/v1/pay/payment/alipay"; }
    public static String getPayUrl_wxpay() { return SDK_BASE_URL+"/api/v1/pay/payment/wx/wxh5"; }

    //微信支付后查询接口
    public static String getPayUrl_wxpay_query_url() { return SDK_BASE_URL+"/api/v1/pay/query/wx/"; }
    //微信H5支付后的返回页redirect_url
    public static String getPayUrl_wxpay_redirect_url() { return SDK_BASE_URL+"/html/wxH5OrderQuery.html"; }


}
