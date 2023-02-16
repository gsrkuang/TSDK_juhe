package com.sdk.sdklibrary.ui.fragment.login.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.call.GameSdk;
import com.sdk.sdklibrary.mvp.view.MVPAntiAddictionView;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 * Date:2023-02-10
 * Time:15:16
 * author:colin
 * 输入真实姓名 身份证提交实名认证
 *
 */
public class AntiAddictionDialog extends SdkBaseDialog implements MVPAntiAddictionView {

    private Button cancelTipsButton,confirmTipsButton;

    public AntiAddictionDialog(Activity act) {
        super(act);
    }

    @Override
    public int getLayoutId() {
        return R.layout.antiaddiction_dialog;
    }

    @Override
    public void initViews() {

        cancelTipsButton = $(R.id.cancelTipsButton);
        confirmTipsButton = $(R.id.confirmTipsButton);
    }

    @Override
    public void initListener() {
        setOnClick(cancelTipsButton);
        setOnClick(confirmTipsButton);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirmTipsButton){
            //实名认证结果
            dismiss();
            new AntiAddictionDialog(act).show();
        }else if (id == R.id.cancelTipsButton){
            dismiss();
            GameSdk.getInstance().sdkLogout(act);
        }

    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void success(String msg, String data) {

    }

    @Override
    public void fail(String msg, String data) {

    }
}
