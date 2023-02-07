package com.sdk.sdklibrary.base;

import android.app.Application;

import com.sdk.sdklibrary.aboutfacebook.FacebookSDK;
import com.sdk.sdklibrary.tools.SPDataUtils;

/**
 * Created by tzw on 2018/6/4.
 * 自定义Application
 */

public class GameSdkApplication extends Application {

    //doing you want do

    private static GameSdkApplication homeApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        homeApplication = this;
        SPDataUtils.init(getApplicationContext());
        FacebookSDK.getInstance().sdkInitialize(this);
//        InitStripe();

    }

    public static GameSdkApplication getInstance() {
        return homeApplication;
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
