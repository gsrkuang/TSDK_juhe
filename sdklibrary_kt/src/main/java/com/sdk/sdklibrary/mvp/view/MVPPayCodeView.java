package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;

/**
 *@author colin
 * Date:2023-02-08
 * 获取支付前返回的验证code
 */

public interface MVPPayCodeView extends BaseView {
    void onPayCodeSuccess(String msg,String data,String payType) ;
    void onPayCodeFailed(String msg,String data) ;
    void onPayCodeCancel(String msg,String data) ;
}
