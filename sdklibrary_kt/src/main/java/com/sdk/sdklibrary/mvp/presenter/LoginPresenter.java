package com.sdk.sdklibrary.mvp.presenter;

import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPLoginBean;
import com.sdk.sdklibrary.mvp.view.MVPLoginView;

/**
 *@author colin
 * Date:2023-02-08
 * 登录Presenter
 */

public interface LoginPresenter extends BasePresenter<MVPLoginView> {
    void login(MVPLoginBean user , Context context) ;
    void onekey(Context context) ;
}
