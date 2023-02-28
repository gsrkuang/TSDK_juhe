package com.sdk.sdklibrary.mvp.presenter;

import android.app.Activity;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.view.MVPSdkInitView;

/**
 * Date:2023-02-01
 * Time:10:55
 * author:colin
 */
public interface SdkInitPresenter extends BasePresenter<MVPSdkInitView> {
    void init(String appId, Activity act, InitListener initListener) ;

    public interface InitListener {
        void success(String msg);
        void fail(String msg);
    }
}
