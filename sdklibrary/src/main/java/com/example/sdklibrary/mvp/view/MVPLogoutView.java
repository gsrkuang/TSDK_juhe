package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;
import com.example.sdklibrary.mvp.model.MVPUserInfoResultBean;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;

/**
 * Created by bolin
 * 登出
 */

public interface MVPLogoutView extends BaseView {
    void logoutSuccess(String msg, SDKUserResult user) ;
    void logoutFailed(String msg,String data) ;
}
