package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;

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
