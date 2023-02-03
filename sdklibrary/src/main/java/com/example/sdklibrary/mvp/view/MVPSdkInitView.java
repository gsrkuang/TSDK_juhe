package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;

/**
 * Created by bolin
 * 登出
 */

public interface MVPSdkInitView extends BaseView {
    void initSuccess(String msg, String data) ;
    void initFailed(String msg,String data) ;
}
