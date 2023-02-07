package com.sdk.sdklibrary.base;

import android.app.Activity;

import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.call.GameSdkLogic;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.sdk.sdklibrary.mvp.Imp.RegistPresenterImp;
import com.sdk.sdklibrary.mvp.model.MVPLoginBean;
import com.sdk.sdklibrary.mvp.model.MVPRegisterBean;
import com.sdk.sdklibrary.mvp.view.MVPLoginView;
import com.sdk.sdklibrary.mvp.view.MVPRegistView;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 * Date:2022-11-11
 * Time:16:43
 * author:colin
 * <p>
 * 第三方SDK的基类
 */
public class SdkBaseThreeSDK implements MVPLoginView, MVPRegistView {
    private LoginPresenterImp loginPresenterImp;
    private RegistPresenterImp registPresenterImp;
    private String accountId;
    private Activity act;

    //当第三方登陆成功后，会自动登陆,登陆失败后会自动注册渠道的账号系统
    public void qudaoLogin(String accountId, Activity activity) {
        this.accountId = accountId;
        this.act = activity;
        loginPresenterImp = new LoginPresenterImp();
        loginPresenterImp.attachView(this);

        String pass = accountId;
        MVPLoginBean bean = new MVPLoginBean(accountId, pass);
        loginPresenterImp.login(bean, activity);
    }

    public void qudaoRegist(String accountId, Activity activity) {
        registPresenterImp = new RegistPresenterImp();
        registPresenterImp.attachView(this);

        String mPassWord = accountId;
        String mSecPassWord = accountId;

        MVPRegisterBean bean = new MVPRegisterBean(accountId, mPassWord, mSecPassWord);
        registPresenterImp.regist(bean, activity);

    }

    @Override
    public void showAppInfo(String msg, String data) {

    }

    @Override
    public void loginSuccess(String msg, String data) {
        GameSdkLogic.getInstance().sdkFloatViewShow();
        SdkLoginDialogFragment.getInstance().dismiss();
        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS, accountId);
    }

    @Override
    public void loginFailed(String msg, String data) {
        if (null != act && null != accountId) {
            qudaoRegist(accountId, act);
        } else {
            Delegate.loginlistener.callback(SDKStatusCode.FAILURE, msg);
        }
    }

    @Override
    public void registSuccess(String msg, String data) {
        GameSdkLogic.getInstance().sdkFloatViewShow();
        SdkLoginDialogFragment.getInstance().dismiss();
        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS, accountId);
    }

    @Override
    public void registFailed(String msg, String data) {
        Delegate.loginlistener.callback(SDKStatusCode.FAILURE, msg);
    }


}
