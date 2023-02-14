package com.sdk.sdklibrary.ui.fragment.login.dialog;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.tools.SPDataUtils;

/**
 * Date:2023-01-30
 * Time:15:44
 * author:colin
 * 一键登录dialog，提示用户保存一键登录后的密码
 */
public class OneKeyLoginTipsDialog extends SdkBaseDialog {


    private TextView account,password;

    private TextView confirm;

    private Activity act ;
    public OneKeyLoginTipsDialog(Activity act) {
        super(act);
        this.act = act;
    }


    @Override
    public int getLayoutId() {
        return R.layout.tips_onekeylogin;
    }

    @Override
    public void initViews() {
        account = $(R.id.text_account);
        password = $(R.id.text_password);
        confirm = $(R.id.confirm);
    }

    @Override
    public void initListener() {
        setOnClick(confirm);
    }


    @Override
    public void initData() {
        String onekeyAccount = SPDataUtils.getInstance().getOneKeyAccount();
        String onekeyPassword = SPDataUtils.getInstance().getOneKeyPassword();
        account.setText(onekeyAccount);
        password.setText(onekeyPassword);
        setCancelable(false);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirm){
            dismiss();
        }else if (id == R.id.goback){

        }
    }

}

