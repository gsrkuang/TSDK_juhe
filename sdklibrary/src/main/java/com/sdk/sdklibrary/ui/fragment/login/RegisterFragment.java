package com.sdk.sdklibrary.ui.fragment.login;

import android.content.Intent;
import android.os.Bundle;
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
import com.sdk.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.sdk.sdklibrary.mvp.Imp.RegistPresenterImp;
import com.sdk.sdklibrary.mvp.model.MVPLoginBean;
import com.sdk.sdklibrary.mvp.model.MVPRegisterBean;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;
import com.sdk.sdklibrary.mvp.view.MVPLoginView;
import com.sdk.sdklibrary.mvp.view.MVPRegistView;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.ui.AgreementActivity;
import com.sdk.sdklibrary.ui.PrivacyActivity;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;

/**
 * Created by bolin
 * 通过用户自定义账号的注册界面
 */

public class RegisterFragment extends SdkBaseFragment implements MVPRegistView, MVPLoginView {

    private EditText username,passWord;
    private Button register;
    private ImageView goback;

    private TextView agreement, privacy;
    private RegistPresenterImp registPresenterImp;
    private LoginPresenterImp loginPresenterImp;

    private String mUserName,mPassWord;
    protected boolean accountTag,passwordTag;
    private CheckBox checkPrivacyBox;

    private ImageView clearPassword, clearAccount;

    protected boolean editUserNameTag, editPasswordTag;

    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;

    private final String LOGIN_FORMERROR = "帐号/密码长度格式错误";
    private final String LENGTH_EMPTY = "请检查帐号/密码输入";

    private final String AGREE_PRIVACY = "请先阅读并同意用户协议和隐私协议";
    private final String ACCOUNT_NOT_ALLNUMBER = "账号不允许纯数字";

    private String mFrom;

    public static RegisterFragment newInstance(String from){
        RegisterFragment fragment = new RegisterFragment();
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
        return R.layout.regist;
    }

    @Override
    public void initViews(View view) {
        username = view.findViewById(R.id.registusername);
        passWord = view.findViewById(R.id.registerpassword);
        register = view.findViewById(R.id.regist);
        goback = view.findViewById(R.id.goback);
        checkPrivacyBox = view.findViewById(R.id.check_privacy);

        clearAccount = view.findViewById(R.id.iv_clear_account);
        clearPassword = view.findViewById(R.id.iv_clear_password);


        agreement = view.findViewById(R.id.text_agreement);
        privacy = view.findViewById(R.id.text_privacy);
    }


    @Override
    public void initListener() {
        setOnClick(register);
        setOnClick(goback);

        setOnClick(clearAccount);
        setOnClick(clearPassword);

        setOnClick(agreement);
        setOnClick(privacy);
        setEditTextListener();
    }

    public void setEditTextListener() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();

                if (str.length() > 0) {
                    clearAccount.setVisibility(View.VISIBLE);
                    editUserNameTag = true;
                    if (editPasswordTag && editUserNameTag) {
                        register.setEnabled(true);
                    }
                } else {
                    clearAccount.setVisibility(View.INVISIBLE);
                    editUserNameTag = false;
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();

                if (str.length() > 0) {
                    clearPassword.setVisibility(View.VISIBLE);
                    editPasswordTag = true;
                    if (editPasswordTag && editUserNameTag) {
                        register.setEnabled(true);
                    }

                } else {
                    clearPassword.setVisibility(View.INVISIBLE);
                    editPasswordTag = false;
                    register.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @Override
    public void initData() {
        registPresenterImp = new RegistPresenterImp();
        registPresenterImp.attachView(this);

        loginPresenterImp = new LoginPresenterImp();
        loginPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.regist){
            registeMethod();
        }else if (id == R.id.goback){
            goBackMainUI();
        } else if (id == R.id.iv_clear_account) {
            clearAccountText();
        } else if (id == R.id.iv_clear_password) {
            clearPasswordText();
        }else if (id == R.id.text_agreement) {
            Intent intent = new Intent(getActivity(), AgreementActivity.class);
            startActivity(intent);
        } else if (id == R.id.text_privacy) {
            Intent intent = new Intent(getActivity(), PrivacyActivity.class);
            startActivity(intent);
        }else {

        }
    }

    private void goBackMainUI() {
        getFragmentManager().popBackStack();
    }

    public void clearAccountText(){
        username.setText("");
    }
    public void clearPasswordText(){
        passWord.setText("");
    }


    private void registeMethod(){

        if (!checkPrivacy()){
            return;
        }
        mUserName = username.getText().toString().trim();
        mPassWord = passWord.getText().toString().trim();

        accountTag = (mUserName.length() > ACCOUNT_MIN_LENGTH) && (mUserName.length() < ACCOUNT_MAX_LENGTH);
        passwordTag = (mPassWord.length() > PASSWORD_MIN_LENGTH) && (mPassWord.length() < PASSWORD_MAX_LENGTH);

        if (mUserName.matches("[0-9]+")) {
            //该字符串是纯数字
            showToast(ACCOUNT_NOT_ALLNUMBER);
            return;
        }

        if ((TextUtils.isEmpty(mUserName)) && (TextUtils.isEmpty(mPassWord))  ) {
            showToast(LENGTH_EMPTY);
            return;
        } else {
            if (accountTag && passwordTag) {
                MVPRegisterBean bean = new MVPRegisterBean(mUserName, mPassWord);
                registPresenterImp.regist(bean, getActivity());
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    @Override
    public void registSuccess(String msg, String data) {
//        SdkLoginDialogFragment.getInstance().dismiss();//注册成功销毁登陆窗
        //注册成功后调用登陆接口，自动登录
        loginMethod();
        LoggerUtils.i("注册成功");
    }

    @Override
    public void registFailed(String msg, String data) {
        LoggerUtils.i("注册失败");
    }

    private void loginMethod() {
        if (!checkPrivacy()){
            return;
        }

        mUserName = username.getText().toString().trim();
        mPassWord = passWord.getText().toString().trim();

        MVPLoginBean bean = new MVPLoginBean(mUserName, mPassWord);
        loginPresenterImp.login(bean, getActivity());

    }


    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registPresenterImp.detachView();
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
        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS, user);
        LoggerUtils.i("登录成功");
        SdkLoginDialogFragment.getInstance().dismiss();//登陆成功销毁登陆窗
    }

    @Override
    public void onekeyloginSuccess(String msg, SDKUserResult user) {

    }

    @Override
    public void loginFailed(String msg, String data) {
        Delegate.loginlistener.callback(SDKStatusCode.FAILURE, "login failure");
        LoggerUtils.i("登录失败");
    }
}
