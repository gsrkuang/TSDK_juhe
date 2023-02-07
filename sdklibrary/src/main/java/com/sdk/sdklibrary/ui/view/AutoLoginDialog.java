package com.sdk.sdklibrary.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.call.GameSdkLogic;
import com.sdk.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.sdk.sdklibrary.mvp.model.MVPLoginBean;
import com.sdk.sdklibrary.mvp.presenter.LoginPresenter;
import com.sdk.sdklibrary.tools.SPDataUtils;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 * Date:2023-02-06
 * Time:17:57
 * author:colin
 */
public class AutoLoginDialog extends SdkBaseDialog {

    private TextView time, account, switchAccount; //倒计时时间
    private CountDownTimer countDownTimer;
    private LoginPresenter loginPresenterImp;
    private Activity act;

    public AutoLoginDialog(Activity act) {
        super(act);
        act = this.act;
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
        loginPresenterImp = new LoginPresenterImp();
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

                close();
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
    }


}
