package com.sdk.sdklibrary.ui.fragment.login.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.call.GameSdk;
import com.sdk.sdklibrary.mvp.view.MVPAntiAddictionView;
import com.sdk.sdklibrary.tools.IDCardUtil;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 * Date:2023-02-10
 * Time:15:16
 * author:colin
 * 输入真实姓名 身份证提交实名认证
 */
public class AntiAddictionDialog extends SdkBaseDialog implements MVPAntiAddictionView {

    private Button cancelTipsButton, confirmTipsButton;

    private final String LENGTH_FORMERROR = "身份证长度格式错误";
    private final String LENGTH_EMPTY = "请检查姓名/身份证号为空";
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
        setCancelable(false);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirmTipsButton) {
            //检查
            //实名认证结果
//            dismiss();
            boolean result = false;
            if (result){
                dismiss();
                //提示完成实名认证，已满18
            }else {
                //未成年提示

                dismiss();
                //弹出未成年提示dialog
                new AntiAddictionFailureTipsDialog(act).show();
            }


        } else if (id == R.id.cancelTipsButton) {
            dismiss();
            GameSdk.getInstance().sdkLogout(act);
        }

    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }


    @Override
    public void bindId_success(String msg, String data,String idnumber) {
        //绑定完成
//        TODO:根据绑定身份证号是否成年做不同操作，如果是非成年人则禁止登录，如果是成年人则进入游戏
        if (!IDCardUtil.isAdult(idnumber)){
            //未成年人
            dismiss();
            //弹出未成年提示dialog
            new AntiAddictionFailureTipsDialog(act);
        }else {
            dismiss();
        }

    }

    @Override
    public void bindId_fail(String msg, String data) {
        //绑定失败

    }
}
