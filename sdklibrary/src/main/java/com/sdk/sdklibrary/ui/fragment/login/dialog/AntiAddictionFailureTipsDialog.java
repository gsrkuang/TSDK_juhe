package com.sdk.sdklibrary.ui.fragment.login.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseDialog;
import com.sdk.sdklibrary.call.GameSdk;

/**
 * Date:2023-02-10
 * Time:15:30
 * author:colin
 *
 * 账号不在允许时间内无法进入游戏
 *
 * 您的账号已被纳入防沉迷系统，根据《关于防止未成年人沉迷网络游戏的通知》，
 * 您可在周五、周六、周日和法定节假日每日20时至21时登陆游戏，
 * 其他时间将无法为未成年人用户提供游戏服务。
 */
public class AntiAddictionFailureTipsDialog extends SdkBaseDialog {

    private Button confirmTipsButton;
    private Activity act;
    public AntiAddictionFailureTipsDialog(Activity act) {
        super(act);
        this.act = act;
    }

    @Override
    public int getLayoutId() {
        return R.layout.antiaddiction_failuretips_dialog;
    }

    @Override
    public void initViews() {
        confirmTipsButton = $(R.id.confirmTipsButton);
    }

    @Override
    public void initListener() {

        setOnClick(confirmTipsButton);
    }

    @Override
    public void initData() {
        setCancelable(false);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirmTipsButton){
            //退出dialog，关闭
            dismiss();
            GameSdk.getInstance().sdkLogout(act);
        }
    }


}
