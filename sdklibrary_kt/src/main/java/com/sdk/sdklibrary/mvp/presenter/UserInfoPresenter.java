package com.sdk.sdklibrary.mvp.presenter;

import android.content.Context;

import com.sdk.sdklibrary.base.BasePresenter;
import com.sdk.sdklibrary.mvp.view.MVPUserInfoView;

/**
 * Date:2023-02-01
 * Time:10:55
 * author:colin
 */
public interface UserInfoPresenter extends BasePresenter<MVPUserInfoView> {
    void getUserInfo(Context context) ;
}
