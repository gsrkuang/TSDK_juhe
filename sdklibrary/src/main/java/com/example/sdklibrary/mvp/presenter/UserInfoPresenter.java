package com.example.sdklibrary.mvp.presenter;

import android.content.Context;

import com.example.sdklibrary.base.BasePresenter;
import com.example.sdklibrary.mvp.view.MVPUserInfoView;

/**
 * Date:2023-02-01
 * Time:10:55
 * author:colin
 */
public interface UserInfoPresenter extends BasePresenter<MVPUserInfoView> {
    void getUserInfo(Context context) ;
}
