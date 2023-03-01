package com.sdk.sdklibrary.ui.fragment.login.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText realName, idNumber;

    private final String LENGTH_FORMERROR = "身份证长度格式错误";
    private final String VALID_ERROR = "身份证长度或格式错误";
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
        realName = $(R.id.realName);
        idNumber = $(R.id.idnumber);
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
            dismiss();//TODO

            //检查姓名和身份证号码，错误则提示
            if (!checkNameAndIdNumber()){
                return;
            }

            //实名认证结果
//            dismiss();
            boolean result = true;
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


    /**
     * 检查用户输入的姓名和身份证号是否正确
     */
    public boolean checkNameAndIdNumber(){
        String number = idNumber.getText().toString().trim();
        String name = realName.getText().toString().trim();
        if ("".equals(name) || "".equals(number)){
            showToast(LENGTH_EMPTY);
            return false;
        }

        //检查
        boolean checkResult = IDCardUtil.isValid(idNumber.getText().toString().trim());
        if (!checkResult){
            showToast(VALID_ERROR);
            return false;
        }
        return true;

    }
}
