package com.sdk.sdklibrary.mvp.presenter;

import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPLoginBean;
import com.sdk.sdklibrary.mvp.view.MVPLoginView;

/**
 * Created by tzw on 2018/6/5.
 * 登录Presenter
 */

public interface LoginPresenter extends BasePresenter<MVPLoginView> {
    void login(MVPLoginBean user , Context context) ;
}
