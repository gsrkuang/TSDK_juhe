package com.sdk.sdklibrary.ui.fragment.usercenter.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.call.GameSdk;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.Imp.LogoutPresenterImp;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;
import com.sdk.sdklibrary.mvp.presenter.LogoutPresenter;
import com.sdk.sdklibrary.mvp.view.MVPLogoutView;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.SPDataUtils;
import com.sdk.sdklibrary.ui.dialogfragment.SdkUserCenterDialogFragment;
import com.sdk.sdklibrary.ui.view.FloatIconView;


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
        GameSdk.getInstance().sdkFloatViewHide();
        GameSdk.getInstance().sdkLogin(act);
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
