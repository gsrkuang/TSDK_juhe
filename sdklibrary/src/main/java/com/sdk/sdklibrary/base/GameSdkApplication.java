package com.sdk.sdklibrary.base;

import android.app.Application;

import com.sdk.sdklibrary.tools.SPDataUtils;

/**
 *@author colin
 * Date:2023-02-08
 * 自定义Application
 */

public class GameSdkApplication extends Application {

    //doing you want do
    private static GameSdkApplication homeApplication;
    private String appkey = "";
    private String ticket = "";
    @Override
    public void onCreate() {
        super.onCreate();
        homeApplication = this;
        SPDataUtils.init(getApplicationContext());
//        InitStripe();

    }

    public static GameSdkApplication getInstance() {
        return homeApplication;
    }


    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    /**
     * 初始化Stripe
     */
    public void InitStripe() {
//        PaymentConfiguration.init(
//                getApplicationContext(),
//                "pk_test_TYooMQauvdEDq54NiTphI7jx"
//        );
    }
}
