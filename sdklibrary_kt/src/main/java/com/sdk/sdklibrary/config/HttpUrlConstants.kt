package com.sdk.sdklibrary.config

/**
 * @author colin
 * Date:2023-02-08
 * URL_状态限定
 */
object HttpUrlConstants {
    //    public static final String SDK_BASE_URL = "http://192.168.0.105:8983";
    const val SDK_BASE_URL = "https://xiyecode.free.svipss.top/"
    const val COOKIE_DATA = "cookieData"

    //  请求成功，响应成功(!--接口成功--!)
    const val NET_OK = "netOK"

    //cookieData
    //  请求成功，响应失败(!--接口失败--!)
    const val NET_ON_FAILURE = "netFaiure"

    //------------!!!------------
    //  没有网络链接(!--客户端没有联网--!)
    const val NET_NO_LINKING = "请检查网络链接"

    //  后台服务器错误(!--服务器宕机--!)
    const val SERVER_ERROR = "啊哦~服务器去月球了"

    //10000错误系列
    const val BZ_INVALID_PARAM = 10000 //无效参数
    const val BZ_INVALID_APP_ID = 10001 //无效应用ID
    const val BZ_INVALID_ACCOUNT = 10102 //账号不合法
    const val BZ_INVALID_TOKEN = 10103 //Token已失效

    //12000错误系列
    const val BZ_ERROR = 12000 //未知错误
    const val BZ_ERROR_ACCOUNT_PASSWORD = 12001 //账号或密码错误
    const val BZ_ERROR_ACCOUNT = 12002 //账号错误或已锁定
    const val BZ_ERROR_CHANGE_PASSWORD = 12003 //修改密码错误
    const val BZ_ERROR_SIGN = 12100 //签名错误
    const val BZ_ERROR_CODE = 12200 //验证码错误
    const val BZ_LIMITED_APP = 14001 // "limit app", "应用限制"
    const val BZ_LIMITED_APP_REG = 14002 // "limit app register", "应用注册限制"
    const val BZ_LIMITED_APP_PAY = 14003 // "limit app payment", "应用支付限制"
    const val BZ_EXPIRED_APP = 16001 // "expired app", "应用过期"
    const val BZ_CODE = 14200 //Frequently operation
    const val BZ_UNBIND = 15001 //Unbound phone: 手机号码未被绑定

    //    public static final int BZ_UNBIND = 15100;//Unbound phone: 手机号码未被绑定
    const val BZ_LIMITED_UNDERAGE_LOGIN = 14300 // "limit login of underage", "未成年限制登录"),
    const val BZ_LIMITED_SINGLE_PAY = 14301 // "limit single payment", "单次充值额度限制"),
    const val BZ_LIMITED_DAILY_ACCUMULATE_PAY =
        14302 // "limit daily accumulated payment", "日累计充值额度限制"),
    const val BZ_LIMITED_WEEKLY_ACCUMULATE_PAY =
        14303 // "limit weekly accumulated payment", "周累计充值额度限制"),
    const val BZ_LIMITED_MONTHLY_ACCUMULATE_PAY =
        14304 // "limit monthly accumulated payment", "月累计充值额度限制"),

    //业务成功、失败
    const val BZ_SUCCESS = 20000 //成功
    const val BZ_FAILURE = 20001 //注册失败

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
    const val getAgreementUrl = SDK_BASE_URL + "html/protocol/111111/user.html"
    const val getPrivacytUrl = SDK_BASE_URL + "html/protocol/111111/privacy.html"
    const val getPayUrl = "https://xiyecode.free.svipss.top/api/paypal/pay"
    //tsdk 初始化url  POST
    const val getInitUrl = SDK_BASE_URL + "api/v1/app/init"
    //tsdk登录url  POST
    //account,password
    const val getLoginUrl = SDK_BASE_URL + "api/v2/account/login/account"
    //tsdk一键登录url  POST
    //account,password
    const val getOneKeyLoginUrl = SDK_BASE_URL + "api/v2/account/login/rapid"
    //tsdk ①验证码登录/忘记密码-获取验证码  POST
    //account,password
    const val getPhoneCodeUrl = SDK_BASE_URL + "api/v2/account/code/send"
    //tsdk ②验证码登录/忘记密码url  POST
    //account,password
    const val getPhoneCodeLoginUrl = SDK_BASE_URL + "api/v2/account/login/phone"
    //tsdk注册url  POST
    //account,password
    const val getRegisterUrl = SDK_BASE_URL + "api/v2/account/register/account"
    //tsdk修改密码url  POST
    //oldpassword,newpassword,newconfirmpassword
    const val getChangePasswordUrl = SDK_BASE_URL + "api/v2/account/password/update"
    //tsdk忘记密码url  POST
    //account,password
    const val getFrogetUrl = SDK_BASE_URL + "api/v2/account/password/reset"


    //tsdk检查手机号是否被绑定url  POST
    //phone
    const val getBindPhoneCheckUrl = SDK_BASE_URL + "api/v2/account/bind/phone/check"


    //tsdk 绑定手机号url  POST
    //phone 验证码
    const val getBindPhoneUrl = SDK_BASE_URL + "api/v2/account/bind/phone"


    //tsdk 绑定身份证url  POST
    //name
    //idnumber
    const val getAntiAddictionView = SDK_BASE_URL + "api/v2/account/bind/phone"


    //tsdk登出url  POST
    //account,password
    const val getLogoutUrl = SDK_BASE_URL + "api/v2/account/logout"


    //tsdk获取用户信息url  POST
    //请求头appId,ticket
    const val getUserInfoUrl = SDK_BASE_URL + "api/v2/account/info"


    //tsdk支付url alipay  POST
    const val getPayUrl_alipay = SDK_BASE_URL + "api/v1/pay/payment/alipay"


    const val getPayUrl_wxpay = SDK_BASE_URL + "api/v1/pay/payment/wx/wxh5"


    //微信支付后查询接口
    const val getPayUrl_wxpay_query_url = SDK_BASE_URL + "api/v1/pay/query/wx/"


    //微信H5支付后的返回页redirect_url
    const val getPayUrl_wxpay_redirect_url = SDK_BASE_URL + "html/wxH5OrderQuery.html"

}