package com.sdk.sdklibrary.ui.fragment.login.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.call.GameSdk;

/**
 * Date:2023-02-10
 * Time:15:27
 * author:colin
 *
 * 这是提示Dialog
 * 如果用户未进行实名认证，能够进行退出和跳转到实名认证
 *
 * 根据《国家新闻出版署关于防止未成年人沉迷网络游戏的通知》和《国家新闻出版署关千进一步严格管理切实防止未成年人沉迷网络游戏的通知》
 * ，用户需要使用有效的证件进行实名认证才能体验游戏内容。
 */
public class AntiAddictionTipsDialog extends SdkBaseDialog {

    private Button cancelTipsButton,confirmTipsButton;
    private Activity act;
    public AntiAddictionTipsDialog(Activity act) {
        super(act);

        this.act = act;
    }

    @Override
    public int getLayoutId() {
        return R.layout.antiaddiction_tips_dialog;
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
            //弹出实名认证dialog
            dismiss();
            new AntiAddictionDialog(act).show();
        }else if (id == R.id.cancelTipsButton){
            //调用登出接口
            dismiss();
            GameSdk.getInstance().sdkLogout(act);
        }
    }
}
