package com.sdk.sdklibrary.mvp.presenter;

import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.MVPPhoneBindBean;
import com.sdk.sdklibrary.mvp.view.MVPBindPhoneView;

/**
 * Date:2023-01-31
 * Time:17:04
 * author:colin
 */
public interface BindPhonePresenter extends BasePresenter<MVPBindPhoneView> {
    void confirm(MVPPhoneBindBean bean, Context context) ;
    void bindGetCode(String phoneNumber, Context context) ;
    void checkBindPhone(String phoneNumber, Context context) ;
}


