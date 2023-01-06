package com.example.sdklibrary.mvp.presenter;

import android.content.Context;

import com.example.sdklibrary.base.BasePresenter;
import com.example.sdklibrary.mvp.model.MVPLoginBean;
import com.example.sdklibrary.mvp.view.MVPLoginView;

/**
 * Created by bolin
 * 登录Presenter
 */

public interface LoginPresenter extends BasePresenter<MVPLoginView> {
    void login(MVPLoginBean user , Context context) ;
}
