package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;
import com.example.sdklibrary.mvp.model.MVPUserInfoResultBean;

/**
 * Created by bolin
 * 登录
 */

public interface MVPUserInfoView extends BaseView {
    void getUserInfo_Success(String msg, MVPUserInfoResultBean user) ;
    void getUserInfo_Failed(String msg,String data) ;
}
