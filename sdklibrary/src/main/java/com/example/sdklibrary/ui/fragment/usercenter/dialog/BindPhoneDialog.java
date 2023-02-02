package com.example.sdklibrary.ui.fragment.usercenter.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseDialog;
import com.example.sdklibrary.mvp.Imp.BindPhonePresenterImp;
import com.example.sdklibrary.mvp.Imp.ForgetPasswordPresenterImp;
import com.example.sdklibrary.mvp.model.MVPPhoneBindBean;
import com.example.sdklibrary.mvp.presenter.BindPhonePresenter;
import com.example.sdklibrary.mvp.view.MVPBindPhoneView;
import com.example.sdklibrary.tools.LoggerUtils;

/**
 * Date:2023-01-31
 * Time:16:35
 * author:colin
 */
public class BindPhoneDialog extends SdkBaseDialog implements MVPBindPhoneView {
    private EditText phoneNumber,codeNumber;
    private Button confirmBind,verificationCode;
    private ImageView goback;

    private ImageView clearAccount, clearCode;

    private String mPhoneNumber,mCode;
    protected boolean accountTag,codeTag;

    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;

    private BindPhonePresenter bindPhonePresenterImp;

    //new倒计时对象,总共的时间,每隔多少秒更新一次时间
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    private final String LOGIN_FORMERROR = "手机号码/验证码长度格式错误";
    private final String LENGTH_EMPTY = "请检查手机号/验证码输入";
    private final String BINDCHECK_ERROR = "该手机号码已被绑定！";
    private Activity act ;

    public BindPhoneDialog(Activity act) {
        super(act);
        this.act = act;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bindphone;
    }

    @Override
    public void initViews() {
        phoneNumber = $(R.id.bind_phonenumber);
        codeNumber = $(R.id.bind_code);
        clearAccount = $(R.id.iv_clear_account);
        clearCode = $(R.id.iv_clear_code);
        verificationCode = $(R.id.verificationCode);
        confirmBind = $(R.id.confirm_bind);
        goback = $(R.id.goback);
    }

    @Override
    public void initListener() {
        setOnClick(confirmBind);
        setOnClick(verificationCode);
        setOnClick(goback);
        setOnClick(clearAccount);
        setOnClick(clearCode);
        setEditTextListener();
    }

    public void setEditTextListener() {
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();

                if (str.length() == 11){
                    verificationCode.setEnabled(true);
                }else {
                    verificationCode.setEnabled(false);
                }

                if (str.length() > 0) {
                    clearAccount.setVisibility(View.VISIBLE);
                }else{
                    clearAccount.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        codeNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() == 6){
                    confirmBind.setEnabled(true);
                }else {
                    confirmBind.setEnabled(false);
                }

                if (str.length() > 0) {
                    clearCode.setVisibility(View.VISIBLE);
                } else {
                    clearCode.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void initData() {
        bindPhonePresenterImp = new BindPhonePresenterImp();
        bindPhonePresenterImp.attachView(this);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirm_bind){
            bindMethod();
        }else if (id == R.id.verificationCode){
            getCodeMethod();
        }else if (id == R.id.goback){
            dismiss();
        }else if (id == R.id.iv_clear_account) {
            clearAccountText();
        } else if (id == R.id.iv_clear_code) {
            clearCodeText();
        }
    }

    public void clearAccountText(){
        phoneNumber.setText("");
    }
    public void clearCodeText(){
        codeNumber.setText("");
    }

    private void bindMethod(){

        mPhoneNumber = phoneNumber.getText().toString().trim();
        mCode = codeNumber.getText().toString().trim();

        accountTag = (mPhoneNumber.length() > ACCOUNT_MIN_LENGTH) && (mPhoneNumber.length() < ACCOUNT_MAX_LENGTH);
        codeTag = (mCode.length() > PASSWORD_MIN_LENGTH) && (mCode.length() < PASSWORD_MAX_LENGTH);

        if ((TextUtils.isEmpty(mPhoneNumber)) && (TextUtils.isEmpty(mCode))    ) {
            showToast(LENGTH_EMPTY);
            return;
        } else {
            if (accountTag && codeTag ) {
                MVPPhoneBindBean bean = new MVPPhoneBindBean(mPhoneNumber, mCode);
                bindPhonePresenterImp.confirm(bean, act);
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    private void getCodeMethod(){

        mPhoneNumber = phoneNumber.getText().toString().trim();
        //先检查手机号码是否已经被绑定，再去发送验证码
        bindPhonePresenterImp.checkBindPhone(mPhoneNumber,act);


    }


    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void bind_success(String msg, String data) {
        //手机绑定成功
        dismiss();

        Toast.makeText(act, "手机绑定成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bind_fail(String msg, String data) {
        LoggerUtils.i("重置密码失败");
    }

    @Override
    public void verificationCodeFailed(String msg, String data) {
        LoggerUtils.i("获取验证码失败");

    }

    @Override
    public void verificationCodeSuccess(String msg, String data) {
        LoggerUtils.i("获取验证码成功");
        //开始倒计时60s
        myCountDownTimer.start();
    }

    @Override
    public void phoneCanBind(String msg, String data) {
        //手机号码未被绑定，发送验证码
        mPhoneNumber = phoneNumber.getText().toString().trim();
        accountTag = (mPhoneNumber.length() > ACCOUNT_MIN_LENGTH) && (mPhoneNumber.length() < ACCOUNT_MAX_LENGTH);
        if ((TextUtils.isEmpty(mPhoneNumber))) {
            showToast(LENGTH_EMPTY);
            return;
        }
        else {
            if (accountTag) {
                bindPhonePresenterImp.bindGetCode(mPhoneNumber, act);
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    @Override
    public void phoneCanNotBind(String msg, String data) {
        //手机号码已被绑定，提示不能发送验证码
        showToast(BINDCHECK_ERROR);
    }

    //倒计时函数
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            verificationCode.setEnabled(false);
            verificationCode.setText(l/1000+"秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            verificationCode.setText("重新获取");
            //设置可点击
            verificationCode.setEnabled(true);
        }
    }
}
