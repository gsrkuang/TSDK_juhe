package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;

/**
 * Created by tzw on 2018/6/5.
 * 注册
 */

public interface MVPRegistView extends BaseView {
    void registSuccess(String msg, String data) ;
    void registFailed(String msg, String data) ;
}
