package com.example.sdklibrary.mvp.view;

import com.example.sdklibrary.base.BaseView;

/**
 * Created by bolin
 * 获取支付前返回的验证code
 */

public interface MVPPayCodeView extends BaseView {
    void onPayCodeSuccess(String msg,String data) ;
    void onPayCodeFailed(String msg,String data) ;
    void onPayCodeCancel(String msg,String data) ;
}
