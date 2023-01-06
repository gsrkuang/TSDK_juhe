package com.example.sdklibrary.mvp.presenter;


import android.content.Context;

import com.example.sdklibrary.base.BasePresenter;
import com.example.sdklibrary.mvp.model.MVPRegisterBean;
import com.example.sdklibrary.mvp.view.MVPRegistView;

/**
 * Created by bolin
 * 注册Presenter
 */

public interface RegistPresenter extends BasePresenter<MVPRegistView> {
    void regist(MVPRegisterBean user, Context context) ;
}
