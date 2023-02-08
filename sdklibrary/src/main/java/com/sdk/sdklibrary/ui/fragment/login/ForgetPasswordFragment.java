package com.sdk.sdklibrary.ui.fragment.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseFragment;
import com.sdk.sdklibrary.mvp.Imp.ForgetPasswordPresenterImp;
import com.sdk.sdklibrary.mvp.model.MVPForgetPasswordBean;
import com.sdk.sdklibrary.mvp.presenter.ForgetPasswordPresenter;
import com.sdk.sdklibrary.mvp.view.MVPForgetPasswordView;
import com.sdk.sdklibrary.tools.LoggerUtils;

/**
 *@author colin
 * Date:2023-02-08
 * 通过手机号码+验证码修改密码
 */

public class ForgetPasswordFragment extends SdkBaseFragment implements MVPForgetPasswordView {

    private EditText phoneNumber,codeNumber,newPasswordNumber;
    private Button confirm,verificationCode;
    private ImageView goback;

    private ForgetPasswordPresenter forgetPasswordPresenterImp;

    private String mPhoneNumber,mCode,mNewPassword;
    protected boolean accountTag,codeTag,passwordTag;

    private ImageView clearAccount, clearCode,clearNewPassword;

    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;

    //new倒计时对象,总共的时间,每隔多少秒更新一次时间
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    private final String LOGIN_FORMERROR = "手机号码/验证码/新密码长度格式错误";
    private final String LENGTH_EMPTY = "请检查手机号/验证码/新密码输入";
    private final String PHONECHECK_ERROR = "该手机号码未被注册！";

    private String mFrom;

    public static ForgetPasswordFragment newInstance(String from){
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("forgetpassword",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.forgetpassword;
    }

    @Override
    public void initViews(View view) {
        phoneNumber = view.findViewById(R.id.forget_phonenumber);
        codeNumber = view.findViewById(R.id.forget_code);
        newPasswordNumber = view.findViewById(R.id.forget_newpassword);

        verificationCode = view.findViewById(R.id.verificationCode);
        confirm = view.findViewById(R.id.confirm_forgetpassword);
        goback = view.findViewById(R.id.goback);

        clearAccount = view.findViewById(R.id.iv_clear_account);
        clearCode = view.findViewById(R.id.iv_clear_code);
        clearNewPassword = view.findViewById(R.id.iv_clear_password);
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
                    accountTag = true;
                }else{
                    clearAccount.setVisibility(View.INVISIBLE);
                    accountTag = false;
                }

                checkConfirmEnable(accountTag,codeTag,passwordTag);
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
                    confirm.setEnabled(true);
                }else {
                    confirm.setEnabled(false);
                }

                if (str.length() > 0) {
                    clearCode.setVisibility(View.VISIBLE);
                    codeTag = true;
                } else {
                    clearCode.setVisibility(View.INVISIBLE);
                    codeTag = false;
                }

                checkConfirmEnable(accountTag,codeTag,passwordTag);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newPasswordNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0) {
                    clearNewPassword.setVisibility(View.VISIBLE);
                    passwordTag = true;
                } else {
                    clearNewPassword.setVisibility(View.INVISIBLE);
                    passwordTag = false;
                }

                checkConfirmEnable(accountTag,codeTag,passwordTag);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void checkConfirmEnable(boolean accountTag ,boolean codeTag , boolean passwordTag){
        if (accountTag && codeTag && passwordTag){
            confirm.setEnabled(true);
        }else {
            confirm.setEnabled(false);
        }
    }

    @Override
    public void initListener() {
        setOnClick(confirm);
        setOnClick(verificationCode);
        setOnClick(goback);
        setOnClick(clearAccount);
        setOnClick(clearCode);
        setOnClick(clearNewPassword);
        setEditTextListener();
    }

    @Override
    public void initData() {
        forgetPasswordPresenterImp = new ForgetPasswordPresenterImp();
        forgetPasswordPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.confirm_forgetpassword){
            confirmMethod();
        }else if (id == R.id.verificationCode){
            getCodeMethod();
        }else if (id == R.id.goback){
            goBackMainUI();
        }else if (id == R.id.iv_clear_account) {
            clearAccountText();
        } else if (id == R.id.iv_clear_code) {
            clearCodeText();
        }else if (id == R.id.iv_clear_password) {
            clearPasswordText();
        }
    }

    private void goBackMainUI() {
        getFragmentManager().popBackStack();
    }
    public void clearAccountText(){
        phoneNumber.setText("");
    }
    public void clearCodeText(){
        codeNumber.setText("");
    }
    public void clearPasswordText(){
        newPasswordNumber.setText("");
    }

    private void getCodeMethod(){
        mPhoneNumber = phoneNumber.getText().toString().trim();
        //先检查手机号码是否已经被绑定，再去发送验证码
        forgetPasswordPresenterImp.checkBindPhone(mPhoneNumber,getActivity());

    }

    private void confirmMethod(){

        mPhoneNumber = phoneNumber.getText().toString().trim();
        mCode = codeNumber.getText().toString().trim();
        mNewPassword = newPasswordNumber.getText().toString().trim();


        accountTag = (mPhoneNumber.length() > ACCOUNT_MIN_LENGTH) && (mPhoneNumber.length() < ACCOUNT_MAX_LENGTH);
        codeTag = (mCode.length() > PASSWORD_MIN_LENGTH) && (mCode.length() < PASSWORD_MAX_LENGTH);
        passwordTag = (mNewPassword.length() > PASSWORD_MIN_LENGTH) && (mNewPassword.length() < PASSWORD_MAX_LENGTH);

        if ((TextUtils.isEmpty(mPhoneNumber)) && (TextUtils.isEmpty(mCode))&& (TextUtils.isEmpty(mNewPassword))    ) {
            showToast(LENGTH_EMPTY);
            return;
        } else {
            if (accountTag && codeTag && passwordTag) {
                MVPForgetPasswordBean bean = new MVPForgetPasswordBean(mPhoneNumber, mCode,mNewPassword);
                forgetPasswordPresenterImp.confirmReset(bean, getActivity());
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    @Override
    public void resetSuccess(String msg, String data) {
        //重置密码成功
        goBackMainUI();
        Toast.makeText(getActivity(), "密码重置成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resetFailed(String msg, String data) {
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
    public void phoneExist(String msg, String data) {
        //手机号码未被绑定，发送验证码
        mPhoneNumber = phoneNumber.getText().toString().trim();
        accountTag = (mPhoneNumber.length() > ACCOUNT_MIN_LENGTH) && (mPhoneNumber.length() < ACCOUNT_MAX_LENGTH);
        if ((TextUtils.isEmpty(mPhoneNumber))) {
            showToast(LENGTH_EMPTY);
            return;
        }
        else {
            if (accountTag) {
                forgetPasswordPresenterImp.forgetPasswordGetCode(mPhoneNumber, getActivity());
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    @Override
    public void phoneNotExist(String msg, String data) {
        //手机号码已被绑定，提示不能发送验证码
        showToast(PHONECHECK_ERROR);
    }


    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        forgetPasswordPresenterImp.detachView();
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
