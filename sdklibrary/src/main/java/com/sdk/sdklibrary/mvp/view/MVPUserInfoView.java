package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;
import com.sdk.sdklibrary.mvp.model.MVPUserInfoResultBean;

/**
 *@author colin
 * Date:2023-02-08
 * 登录
 */

public interface MVPUserInfoView extends BaseView {
    void getUserInfo_Success(String msg, MVPUserInfoResultBean user) ;
    void getUserInfo_Failed(String msg,String data) ;
}
