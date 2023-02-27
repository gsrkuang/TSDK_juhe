package com.sdk.sdklibrary.mvp.presenter;


import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPRegisterBean;
import com.sdk.sdklibrary.mvp.view.MVPRegistView;

/**
 *@author colin
 * Date:2023-02-08
 * 注册Presenter
 */

public interface RegistPresenter extends BasePresenter<MVPRegistView> {
    void regist(MVPRegisterBean user, Context context) ;
}
