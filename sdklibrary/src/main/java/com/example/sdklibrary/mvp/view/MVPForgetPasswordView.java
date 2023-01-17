package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;

/**
 * Created by bolin
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
