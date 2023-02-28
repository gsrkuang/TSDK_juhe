package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;
import com.sdk.sdklibrary.mvp.model.MVPLoginResultBean;

/**
 *@author colin
 * Date:2023-02-08
 * 登录
 */

public interface MVPLoginView extends BaseView {
    void loginSuccess(String msg, MVPLoginResultBean bean) ;
    void onekeyloginSuccess(String msg, MVPLoginResultBean bean) ;
    void loginFailed(String msg,String data) ;
}
