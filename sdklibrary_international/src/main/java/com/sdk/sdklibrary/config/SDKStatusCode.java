package com.sdk.sdklibrary.config;

/**
 * Created by tzw on 2018/6/5.
 * 全局状态码:
 */

public final class SDKStatusCode {
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    public static final int OTHER = 2;
    public static final int CANCEL = 3;

    public static final int PAY_SUCCESS = 200;
    public static final int PAY_FAILURE = 404;
    public static final int PAY_OTHER = 500;

    public static final int LOGOUT_SUCCESS = 801;
    public static final int LOGOUT_FAILURE = 802;
    public static final int LOGOUT_CANCEL = 803;
    public static final int LOGOUT_OTHER = 884;
}
