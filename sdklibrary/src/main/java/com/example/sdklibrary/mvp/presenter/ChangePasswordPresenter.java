package com.example.sdklibrary.mvp.presenter;

import android.content.Context;

import com.example.sdklibrary.base.BasePresenter;
import com.example.sdklibrary.mvp.model.MVPChangePasswordBean;
import com.example.sdklibrary.mvp.view.MVPChangePasswordView;
import com.example.sdklibrary.mvp.view.MVPForgetPasswordView;

/**
 * Date:2023-01-18
 * Time:16:53
 * author:colin
 */
public interface ChangePasswordPresenter extends BasePresenter<MVPChangePasswordView> {
    void change(MVPChangePasswordBean bean, Context context) ;
}
