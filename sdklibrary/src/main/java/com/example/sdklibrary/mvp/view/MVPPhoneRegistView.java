package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;

/**
 * Created by bolin
 * 注册
 */

public interface MVPPhoneRegistView extends BaseView {
    void loginSuccess(String msg, SDKUserResult user) ;
    void loginFailed(String msg, String data) ;

    void verificationCodeFailed(String msg, String data) ;
    void verificationCodeSuccess(String msg, String data) ;
}
