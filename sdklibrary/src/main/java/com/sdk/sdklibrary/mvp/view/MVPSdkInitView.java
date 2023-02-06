package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;

/**
 * Created by bolin
 * 登出
 */

public interface MVPSdkInitView extends BaseView {
    void initSuccess(String msg, String data) ;
    void initFailed(String msg,String data) ;
}
