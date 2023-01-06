package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;

/**
 * Created by bolin
 * 注册
 */

public interface MVPPhoneRegistView extends BaseView {
    void registSuccess(String msg, String data) ;
    void registFailed(String msg, String data) ;

    void verificationCodeFailed(String msg, String data) ;
    void verificationCodeSuccess(String msg, String data) ;
}
