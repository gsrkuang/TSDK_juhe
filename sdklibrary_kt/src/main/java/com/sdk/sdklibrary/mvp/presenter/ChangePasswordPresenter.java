package com.sdk.sdklibrary.mvp.presenter;

import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPChangePasswordBean;
import com.sdk.sdklibrary.mvp.view.MVPChangePasswordView;

/**
 * Date:2023-01-18
 * Time:16:53
 * author:colin
 */
public interface ChangePasswordPresenter extends BasePresenter<MVPChangePasswordView> {
    void change(MVPChangePasswordBean bean, Context context) ;
}
