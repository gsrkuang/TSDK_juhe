package com.sdk.sdklibrary.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.sdk.sdklibrary.base.GameSdkApplication;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *@author colin
 * Date:2023-02-08
 * Cookie拦截器 关于拦截器的说明：
 *  https://www.jianshu.com/p/bd1be47a16c1
 */

public class AddCookiesInterceptor implements Interceptor {

    private static final String COOKIE_PREF = "cookies_prefs";
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        //获取cookie
        String cookie = getCookie(request.url().toString(), request.url().host());
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
            LoggerUtils.i(LogTAG.cookie, "interceptor addHeader Cookie: "+cookie);
        }
        return chain.proceed(builder.build());
    }

    private String getCookie(String url, String domain) {

        SharedPreferences sp = GameSdkApplication.getInstance().getSharedPreferences(COOKIE_PREF,
                Context.MODE_PRIVATE);

        String cookie = sp.getString(domain, "");
        Log.i(LogTAG.cookie, "interceptor getCookie: "+cookie);

//      存储cookie
        SPDataUtils.getInstance().saveString(HttpUrlConstants.COOKIE_DATA,cookie);

        if (!TextUtils.isEmpty(domain) && sp.contains(domain) && !
                TextUtils.isEmpty(sp.getString(domain, ""))) {
            return sp.getString(domain, "");
        }
        return null;
    }
}
