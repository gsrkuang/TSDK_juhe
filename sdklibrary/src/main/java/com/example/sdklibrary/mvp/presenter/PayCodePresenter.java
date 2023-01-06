package com.example.sdklibrary.mvp.presenter;

import android.content.Context;

import com.example.sdklibrary.base.BasePresenter;
import com.example.sdklibrary.mvp.model.MVPPayCodeBean;
import com.example.sdklibrary.mvp.view.MVPPayCodeView;

/**
 * Created by bolin
 * 登录Presenter
 */

public interface PayCodePresenter extends BasePresenter<MVPPayCodeView> {
    void pay(MVPPayCodeBean payBean , Context context) ;
    void alipay(MVPPayCodeBean payBean , Context context) ;

}
