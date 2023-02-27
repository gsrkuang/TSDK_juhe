package com.sdk.sdklibrary.mvp.presenter;

import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.model.AntiAddictionBean;
import com.sdk.sdklibrary.mvp.view.MVPAntiAddictionView;

public interface AntiAddictionPresenter extends BasePresenter<MVPAntiAddictionView> {

    void confirm(AntiAddictionBean bean, Context context) ;
}
