package com.sdk.sdklibrary.mvp.presenter;

import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPPayCodeBean;
import com.sdk.sdklibrary.mvp.view.MVPPayCodeView;

/**
 *@author colin
 * Date:2023-02-08
 * 登录Presenter
 */

public interface PayCodePresenter extends BasePresenter<MVPPayCodeView> {
    void pay(MVPPayCodeBean payBean , Context context) ;
    void alipay(MVPPayCodeBean payBean , Context context) ;
    void wxpay(MVPPayCodeBean payBean , Context context) ;

}
