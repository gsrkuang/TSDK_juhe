package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;

/**
 *@author colin
 * Date:2023-02-08
 * 登出
 */

public interface MVPLogoutView extends BaseView {
    void logoutSuccess(String msg, SDKUserResult user) ;
    void logoutFailed(String msg,String data) ;
}
