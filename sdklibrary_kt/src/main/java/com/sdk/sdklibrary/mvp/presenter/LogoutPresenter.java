package com.sdk.sdklibrary.mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.view.MVPLogoutView;

/**
 * Date:2023-02-01
 * Time:10:55
 * author:colin
 */
public interface LogoutPresenter extends BasePresenter<MVPLogoutView> {
    void logout(Context context) ;
    void sdkLogout(Activity act) ;
}
