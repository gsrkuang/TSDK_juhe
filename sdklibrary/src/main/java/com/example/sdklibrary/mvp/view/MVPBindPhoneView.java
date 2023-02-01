package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;

/**
 * Date:2023-01-31
 * Time:17:00
 * author:colin
 */
public interface MVPBindPhoneView extends BaseView {
    void bind_success(String msg, String data);
    void bind_fail(String msg, String data);

    void verificationCodeFailed(String msg, String data) ;
    void verificationCodeSuccess(String msg, String data) ;


    void phoneCanBind(String msg, String data) ;
    void phoneCanNotBind(String msg, String data) ;

}

