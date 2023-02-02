package com.example.sdklibrary.ui.fragment.usercenter.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseDialog;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.BindPhonePresenterImp;
import com.example.sdklibrary.mvp.Imp.LogoutPresenterImp;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;
import com.example.sdklibrary.mvp.presenter.LogoutPresenter;
import com.example.sdklibrary.mvp.view.MVPLogoutView;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.example.sdklibrary.ui.dialogfragment.SdkUserCenterDialogFragment;


/**
 * Date:2022-04-06
 * Time:14:49
 * author:colin
 * <p>
 * 登出dialog
 */
public class LogoutDialog extends SdkBaseDialog implements MVPLogoutView {

    private Button cancel;//取消按钮
    private Button confirm;//登陆按钮

    private OnLogoutOnClickListener logoutOnClickListener;
    private OnCancelOnClickListener cancelOnClickListener;

    private Activity act;

    private LogoutPresenter logoutPresenterImp;
    public LogoutDialog(Activity act) {
        super(act);
        this.act = act;
    }


    public void setLogoutOnClickListener(OnLogoutOnClickListener logoutOnClickListener) {
        this.logoutOnClickListener = logoutOnClickListener;
    }

    public void setCancelOnClickListener(OnCancelOnClickListener cancelOnClickListener) {
        this.cancelOnClickListener = cancelOnClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.logout_dialog;
    }

    @Override
    public void initViews() {
        confirm = $(R.id.confirmTipsButton);
        cancel = $(R.id.cancelTipsButton);
    }

    @Override
    public void initListener() {
        setOnClick(confirm);
        setOnClick(cancel);
    }

    @Override
    public void initData() {
        logoutPresenterImp = new LogoutPresenterImp();
        logoutPresenterImp.attachView(this);
    }


    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirmTipsButton) {
            if (logoutOnClickListener != null) {
                logoutOnClickListener.onLogoutClick();
            }
            logoutPresenterImp.logout(act);
        } else if (id == R.id.cancelTipsButton) {

            dismiss();
            if (cancelOnClickListener != null) {
                cancelOnClickListener.onCancelClick();
            }
        }
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void logoutSuccess(String msg, SDKUserResult user) {
        SPDataUtils.getInstance().clearLogin();
        dismiss();
        SdkUserCenterDialogFragment.getInstance().dismiss();
        GameSdkLogic.getInstance().sdkFloatViewHide();
        GameSdkLogic.getInstance().sdkLogin(act);
        Delegate.loginlistener.callback(SDKStatusCode.LOGOUT_SUCCESS, new SDKUserResult());
    }

    @Override
    public void logoutFailed(String msg, String data) {
        LoggerUtils.i("登出失败");
    }

    public interface OnLogoutOnClickListener {
        void onLogoutClick();
    }

    public interface OnCancelOnClickListener {
        void onCancelClick();
    }

}
