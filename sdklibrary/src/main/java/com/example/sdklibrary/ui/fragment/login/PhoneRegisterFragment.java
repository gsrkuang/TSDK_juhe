package com.example.sdklibrary.ui.fragment.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseFragment;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.PhoneRegistPresenterImp;
import com.example.sdklibrary.mvp.model.MVPPhoneRegisterBean;
import com.example.sdklibrary.mvp.presenter.PhoneRegistPresenter;
import com.example.sdklibrary.mvp.view.MVPPhoneRegistView;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 * Created by bolin
 * 通过手机号码+验证码注册界面
 */

public class PhoneRegisterFragment extends SdkBaseFragment implements MVPPhoneRegistView {

    private EditText phoneNumber,codeNumber;
    private Button register,verificationCode;
    private ImageView goback;

    private PhoneRegistPresenter phoneRegistPresenterImp;

    private String mPhoneNumber,mCode;
    protected boolean accountTag,codeTag;

    private CheckBox checkPrivacyBox;

    private ImageView clearAccount, clearCode;

    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;

    private final String LOGIN_FORMERROR = "手机号码/验证码长度格式错误";
    private final String LENGTH_EMPTY = "请检查手机号/验证码输入";
    private final String CONTENT_ERROR = "两次密码输入不一致";
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
        } else if ( ! mCode.equals(mCode) ){
            showToast(CONTENT_ERROR);
            return;
        }
        else {
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
    public void loginSuccess(String msg, String data) {
        SdkLoginDialogFragment.getInstance().dismiss();//注册成功销毁登陆窗
        LoggerUtils.i("手机验证码登陆成功");
        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS,"phoneCode regist success");
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

}
