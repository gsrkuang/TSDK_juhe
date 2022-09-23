package com.example.sdklibrary.base;

import android.app.Application;

import com.example.sdklibrary.aboutfacebook.FacebookSDK;
import com.example.sdklibrary.tools.SPDataUtils;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

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
