package com.sdk.sdklibrary.mvp.presenter;


import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPPhoneRegisterBean;
import com.sdk.sdklibrary.mvp.view.MVPPhoneRegistView;

/**
 * Created by bolin
 * 注册Presenter
 */

public interface PhoneRegistPresenter extends BasePresenter<MVPPhoneRegistView> {
    void phoneLogin(MVPPhoneRegisterBean user, Context context) ;
    void phoneLoginGetCode(String phoneNumber, Context context) ;
}
