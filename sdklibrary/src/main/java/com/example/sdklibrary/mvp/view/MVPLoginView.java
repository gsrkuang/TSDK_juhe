package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;

/**
 * Created by bolin
 * 登录
 */

public interface MVPLoginView extends BaseView {
    void loginSuccess(String msg, SDKUserResult user) ;
    void onekeyloginSuccess(String msg, SDKUserResult user) ;
    void loginFailed(String msg,String data) ;
}
