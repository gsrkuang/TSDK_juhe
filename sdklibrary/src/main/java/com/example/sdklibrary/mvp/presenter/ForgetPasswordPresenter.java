package com.example.sdklibrary.mvp.presenter;


import android.content.Context;

import com.example.sdklibrary.base.BasePresenter;
import com.example.sdklibrary.mvp.model.MVPForgetPasswordBean;
import com.example.sdklibrary.mvp.view.MVPForgetPasswordView;

/**
 * Created by bolin
 * 忘记密码Presenter
 */

public interface ForgetPasswordPresenter extends BasePresenter<MVPForgetPasswordView> {
    void confirmReset(MVPForgetPasswordBean user, Context context) ;
    void forgetPasswordGetCode(String phoneNumber, Context context) ;

    void checkBindPhone(String phoneNumber, Context context) ;
}
