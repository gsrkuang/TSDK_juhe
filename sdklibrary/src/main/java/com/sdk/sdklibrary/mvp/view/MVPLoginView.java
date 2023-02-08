package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;

/**
 *@author colin
 * Date:2023-02-08
 * 登录
 */

public interface MVPLoginView extends BaseView {
    void loginSuccess(String msg, SDKUserResult user) ;
    void onekeyloginSuccess(String msg, SDKUserResult user) ;
    void loginFailed(String msg,String data) ;
}
