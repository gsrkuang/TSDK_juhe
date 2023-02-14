package com.sdk.sdklibrary.ui.fragment.login.dialog;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.call.GameSdk;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.sdk.sdklibrary.mvp.model.MVPLoginBean;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;
import com.sdk.sdklibrary.mvp.presenter.LoginPresenter;
import com.sdk.sdklibrary.mvp.view.MVPLoginView;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.SPDataUtils;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 * Date:2023-02-06
 * Time:17:57
 * author:colin
 */
public class AutoLoginDialog extends SdkBaseDialog implements MVPLoginView {

    private TextView time, account, switchAccount; //倒计时时间
    private CountDownTimer countDownTimer;
    private LoginPresenter loginPresenterImp;
    private Activity act;

    public AutoLoginDialog(Activity act) {
        super(act);
        this.act = act;
    }

    @Override
    public int getLayoutId() {
        return R.layout.autologin_dialog;
    }

    @Override
    public void initViews() {
        time = $(R.id.time);
        account = $(R.id.account);
        switchAccount = $(R.id.switchAccount);

    }

    @Override
    public void initListener() {
        setOnClick(switchAccount);
    }

    @Override
    public void initData() {
        setCancelable(false);
        loginPresenterImp = new LoginPresenterImp();
        loginPresenterImp.attachView(this);

        account.setText("账号：" + SPDataUtils.getInstance().getUserAccount());

        final int timeNum = 4;//倒计时4秒
        //倒计时3秒
        countDownTimer = new CountDownTimer(timeNum * 1000, 1000) {

            @Override
            public void onTick(long l) {
                time.setText(l / 1000 + "");
            }

            @Override
            public void onFinish() {

                //登录
                MVPLoginBean bean = new MVPLoginBean(SPDataUtils.getInstance().getUserAccount(), SPDataUtils.getInstance().getUserPassword());
                loginPresenterImp.login(bean, act);

            }
        }.start();

    }

    @Override
    public void processClick(View v) {

        if (v.getId() == R.id.switchAccount) {
            countDownTimer.cancel();

            close();
            //打开登录窗口
            SdkLoginDialogFragment dialog = SdkLoginDialogFragment.getInstance();
            dialog.show(act.getFragmentManager(), "SdkLoginDialogFragment");
            dialog.setCancelable(false);
        }
    }

    public void close() {

        dismiss();
        loginPresenterImp.detachView();
    }


    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void loginSuccess(String msg, SDKUserResult user) {

//        GameSdk.getInstance().sdkInitFloatView(act);
        GameSdk.getInstance().sdkFloatViewShow();
//        new FloatIconView(act).show();

        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS, user);
        LoggerUtils.i("登录成功");
        LoginSuccessToastView.showToast(act, user.getUsername());

        close();
    }

    @Override
    public void onekeyloginSuccess(String msg, SDKUserResult user) {

    }

    @Override
    public void loginFailed(String msg, String data) {

    }
}
