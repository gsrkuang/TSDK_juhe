package com.sdk.sdklibrary.mvp.presenter;


import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPForgetPasswordBean;
import com.sdk.sdklibrary.mvp.view.MVPForgetPasswordView;

/**
 * Created by bolin
 * 忘记密码Presenter
 */

public interface ForgetPasswordPresenter extends BasePresenter<MVPForgetPasswordView> {
    void confirmReset(MVPForgetPasswordBean user, Context context) ;
    void forgetPasswordGetCode(String phoneNumber, Context context) ;
    void checkBindPhone(String phoneNumber, Context context) ;
}
