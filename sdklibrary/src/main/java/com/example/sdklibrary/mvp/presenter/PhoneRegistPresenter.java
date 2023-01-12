package com.example.sdklibrary.mvp.presenter;


import android.content.Context;

import com.example.sdklibrary.base.BasePresenter;
import com.example.sdklibrary.mvp.model.MVPPhoneRegisterBean;
import com.example.sdklibrary.mvp.view.MVPPhoneRegistView;

/**
 * Created by bolin
 * 注册Presenter
 */

public interface PhoneRegistPresenter extends BasePresenter<MVPPhoneRegistView> {
    void phoneLogin(MVPPhoneRegisterBean user, Context context) ;
    void phoneLoginGetCode(String phoneNumber, Context context) ;
}
