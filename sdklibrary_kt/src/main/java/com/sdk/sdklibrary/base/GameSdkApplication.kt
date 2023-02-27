package com.sdk.sdklibrary.base

import android.app.Application
import com.sdk.sdklibrary.tools.SPDataUtils

/**
Data:2023/2/21
Time:19:20
author:colin handsome
 * 自定义Application
 */
open class GameSdkApplication : Application() {
    private var appkey = ""
    private var ticket = ""
    companion object {
        //doing you want do
        @JvmField
        var instance: GameSdkApplication? = null

        @JvmStatic
        fun getInstance():GameSdkApplication{
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SPDataUtils.init(applicationContext)
        //        InitStripe();
    }




    fun getTicket(): String {
        return ticket
    }

    fun setTicket(ticket: String) {
        this.ticket = ticket
    }

    fun getAppkey(): String {
        return appkey
    }

    fun setAppkey(appkey: String) {
        this.appkey = appkey
    }

    /**
     * 初始化Stripe
     */
    fun InitStripe() {
//        PaymentConfiguration.init(
//                getApplicationContext(),
//                "pk_test_TYooMQauvdEDq54NiTphI7jx"
//        );
    }


}