package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;

/**
 *@author colin
 * Date:2023-02-08
 * 忘记密码
 */

/**
 * Date:2023-01-16
 * Time:11:45
 * author:colin
 */

public interface MVPForgetPasswordView extends BaseView {
    void resetSuccess(String msg, String data) ;
    void resetFailed(String msg, String data) ;

    void verificationCodeFailed(String msg, String data) ;
    void verificationCodeSuccess(String msg, String data) ;


    void phoneExist(String msg, String data) ;
    void phoneNotExist(String msg, String data) ;
}
