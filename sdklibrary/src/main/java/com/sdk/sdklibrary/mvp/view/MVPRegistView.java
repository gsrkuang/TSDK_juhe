package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;

/**
 *@author colin
 * Date:2023-02-08
 * 注册
 */

public interface MVPRegistView extends BaseView {
    void registSuccess(String msg, String data) ;
    void registFailed(String msg, String data) ;
}
