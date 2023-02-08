package com.sdk.sdklibrary.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseFragment;
import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.call.GameSdk;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.Imp.PhoneRegistPresenterImp;
import com.sdk.sdklibrary.mvp.model.MVPPhoneRegisterBean;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;
import com.sdk.sdklibrary.mvp.presenter.PhoneRegistPresenter;
import com.sdk.sdklibrary.mvp.view.MVPPhoneRegistView;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.ui.AgreementActivity;
import com.sdk.sdklibrary.ui.PrivacyActivity;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 *@author colin
 * Date:2023-02-08
 * 通过手机号码+验证码注册界面
 */

public class PhoneRegisterFragment extends SdkBaseFragment implements MVPPhoneRegistView {

    private EditText phoneNumber,codeNumber;
    private Button register,verificationCode;
    private ImageView goback;

    private TextView agreement, privacy;
    private PhoneRegistPresenter phoneRegistPresenterImp;

    private String mPhoneNumber,mCode;
    protected boolean accountTag,codeTag;

    private CheckBox checkPrivacyBox;

    private ImageView clearAccount, clearCode;

    //new倒计时对象,总共的时间,每隔多少秒更新一次时间
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);


    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;

    private final String LOGIN_FORMERROR = "手机号码/验证码长度格式错误";
    private final String LENGTH_EMPTY = "请检查手机号/验证码输入";
    private final String AGREE_PRIVACY = "请先阅读并同意用户协议和隐私协议";

    private String mFrom;

    public static PhoneRegisterFragment newInstance(String from){
        PhoneRegisterFragment fragment = new PhoneRegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("register",from);
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
        return R.layout.phone_regist;
    }

    @Override
    public void initViews(View view) {
        phoneNumber = view.findViewById(R.id.regist_phonenumber);
        codeNumber = view.findViewById(R.id.register_code);

        verificationCode = view.findViewById(R.id.verificationCode);
        register = view.findViewById(R.id.regist_phonecode);
        goback = view.findViewById(R.id.goback);

        checkPrivacyBox = view.findViewById(R.id.check_privacy);

        clearAccount = view.findViewById(R.id.iv_clear_account);
        clearCode = view.findViewById(R.id.iv_clear_code);

        agreement = view.findViewById(R.id.text_agreement);
        privacy = view.findViewById(R.id.text_privacy);
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
                    register.setEnabled(true);
                }else {
                    register.setEnabled(false);
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
    public void initListener() {
        setOnClick(register);
        setOnClick(verificationCode);
        setOnClick(goback);
        setOnClick(clearAccount);
        setOnClick(clearCode);

        setOnClick(agreement);
        setOnClick(privacy);
        setEditTextListener();
    }

    @Override
    public void initData() {
        phoneRegistPresenterImp = new PhoneRegistPresenterImp();
        phoneRegistPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.regist_phonecode){
            loginMethod();
        }else if (id == R.id.verificationCode){
            getCodeMethod();
        }else if (id == R.id.goback){
            goBackMainUI();
        }else if (id == R.id.iv_clear_account) {
            clearAccountText();
        } else if (id == R.id.iv_clear_code) {
            clearCodeText();
        }else if (id == R.id.text_agreement) {
            Intent intent = new Intent(getActivity(), AgreementActivity.class);
            startActivity(intent);
        } else if (id == R.id.text_privacy) {
            Intent intent = new Intent(getActivity(), PrivacyActivity.class);
            startActivity(intent);
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

    private void getCodeMethod(){
        if (!checkPrivacy()){
            return;
        }
        mPhoneNumber = phoneNumber.getText().toString().trim();
        accountTag = (mPhoneNumber.length() > ACCOUNT_MIN_LENGTH) && (mPhoneNumber.length() < ACCOUNT_MAX_LENGTH);
        if ((TextUtils.isEmpty(mPhoneNumber))) {
            showToast(LENGTH_EMPTY);
            return;
        }
        else {
            if (accountTag) {
                phoneRegistPresenterImp.phoneLoginGetCode(mPhoneNumber, getActivity());
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    private void loginMethod(){
        if (!checkPrivacy()){
            return;
        }
        mPhoneNumber = phoneNumber.getText().toString().trim();
        mCode = codeNumber.getText().toString().trim();

        accountTag = (mPhoneNumber.length() > ACCOUNT_MIN_LENGTH) && (mPhoneNumber.length() < ACCOUNT_MAX_LENGTH);
        codeTag = (mCode.length() > PASSWORD_MIN_LENGTH) && (mCode.length() < PASSWORD_MAX_LENGTH);

        if ((TextUtils.isEmpty(mPhoneNumber)) && (TextUtils.isEmpty(mCode))    ) {
            showToast(LENGTH_EMPTY);
            return;
        } else {
            if (accountTag && codeTag ) {

                MVPPhoneRegisterBean bean = new MVPPhoneRegisterBean(mPhoneNumber, mCode);
                phoneRegistPresenterImp.phoneLogin(bean, getActivity());
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    //判断是否同意隐私
    public boolean checkPrivacy(){
        if (!checkPrivacyBox.isChecked()){
            showToast(AGREE_PRIVACY);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void loginSuccess(String msg, SDKUserResult user) {
        GameSdk.getInstance().sdkFloatViewShow();
        SdkLoginDialogFragment.getInstance().dismiss();//注册成功销毁登陆窗
        LoggerUtils.i("手机验证码登陆成功");
        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS,user);
    }

    @Override
    public void loginFailed(String msg, String data) {
        LoggerUtils.i("手机验证码登陆失败");
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
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        phoneRegistPresenterImp.detachView();
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
