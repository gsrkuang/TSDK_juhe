package com.example.sdklibrary.ui.fragment.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseFragment;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.example.sdklibrary.mvp.model.MVPLoginBean;
import com.example.sdklibrary.mvp.view.MVPLoginView;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.example.sdklibrary.ui.fragment.SettingFragment;

/**
 * Date:2022-11-07
 * Time:17:53
 * author:colin
 */
public class LoginFragment extends SdkBaseFragment implements MVPLoginView {

    private RegisterFragment registerFragment;
    private PhoneRegisterFragment phoneRegisterFragment;

    private EditText username, passWord;
    private TextView login, speedRegister, forgetpassword;
    private LoginPresenterImp loginPresenterImp;
    private String mUserName, mPassWord;
    private ImageView phoneRegister;
    private CheckBox checkPrivacyBox;
    private ImageView clearPassword, clearAccount;

    protected boolean accountTag, passwordTag;

    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;
    private final String LOGIN_FORMERROR = "帐号/密码长度格式错误";
    private final String LENGTH_EMPTY = "请检查帐号/密码输入";
    private final String AGREE_PRIVACY = "请先阅读并同意用户协议和隐私协议";

    private String appkey;


    private Activity act;

    private String mFrom;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    public static LoginFragment newInstance(String from) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("login", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void initViews(View view) {
        username = view.findViewById(R.id.loginusername);
        passWord = view.findViewById(R.id.loginpassword);
        login = view.findViewById(R.id.mvplogin);
        speedRegister = view.findViewById(R.id.mvpregister);
        forgetpassword = view.findViewById(R.id.mvpforgetpassword);
        phoneRegister = view.findViewById(R.id.mvpPhoneRegister);
        checkPrivacyBox = view.findViewById(R.id.check_privacy);

        clearAccount = view.findViewById(R.id.iv_clear_account);
        clearPassword = view.findViewById(R.id.iv_clear_password);


    }


    @Override
    public void initListener() {
        setOnClick(login);
        setOnClick(speedRegister);
        setOnClick(forgetpassword);
        setOnClick(phoneRegister);
        setOnClick(checkPrivacyBox);
        setOnClick(clearAccount);
        setOnClick(clearPassword);
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
                } else {
                    clearAccount.setVisibility(View.INVISIBLE);
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
                } else {
                    clearPassword.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }



    @Override
    public void initData() {

        registerFragment = RegisterFragment.newInstance("setting");
        phoneRegisterFragment = PhoneRegisterFragment.newInstance("loginFragment");

        loginPresenterImp = new LoginPresenterImp();
        loginPresenterImp.attachView(this);

//        appkey = act.getIntent().getStringExtra("appkey");

        //得到appkey后，从服务器返回得到各种配置好的idtoken，然后再进行google taptap facebook的初始化
        //此处写网络请求


        act = getActivity();


        username.setText(SPDataUtils.getInstance().getUserAccount());
        passWord.setText(SPDataUtils.getInstance().getUserPassword());

    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.mvplogin) {
            loginMethod();
        } else if (id == R.id.mvpregister) {
//            regist();
            getFragmentManager().beginTransaction().replace(R.id.login_container, registerFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.mvpforgetpassword) {

        } else if (id == R.id.mvpPhoneRegister) {
            getFragmentManager().beginTransaction().replace(R.id.login_container, phoneRegisterFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.iv_clear_account) {
            clearAccountText();
        } else if (id == R.id.iv_clear_password) {
            clearPasswordText();
        } else {

        }
    }

    public void clearAccountText(){
        username.setText("");
    }
    public void clearPasswordText(){
        passWord.setText("");
    }

    private void loginMethod() {
        if (!checkPrivacy()) {
            return;
        }

        mUserName = username.getText().toString().trim();
        mPassWord = passWord.getText().toString().trim();

        accountTag = (mUserName.length() > ACCOUNT_MIN_LENGTH) && (mUserName.length() < ACCOUNT_MAX_LENGTH);
        passwordTag = (mPassWord.length() > PASSWORD_MIN_LENGTH) && (mPassWord.length() < PASSWORD_MAX_LENGTH);

        if ((TextUtils.isEmpty(mUserName)) && (TextUtils.isEmpty(mPassWord))) {
            showToast(LENGTH_EMPTY);
            return;
        } else {
            if (accountTag && passwordTag) {
                MVPLoginBean bean = new MVPLoginBean(mUserName, mPassWord);
                loginPresenterImp.login(bean, act);
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void loginSuccess(String msg, String data) {
        GameSdkLogic.getInstance().sdkFloatViewShow();
        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS, "login success");
        LoggerUtils.i("登录成功");
        SdkLoginDialogFragment.getInstance().dismiss();//登陆成功销毁登陆窗
    }

    @Override
    public void loginFailed(String msg, String data) {
        Delegate.loginlistener.callback(SDKStatusCode.FAILURE, "login failure");
        LoggerUtils.i("登录失败");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenterImp.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    //判断是否同意隐私
    public boolean checkPrivacy() {

        if (!checkPrivacyBox.isChecked()) {
            showToast(AGREE_PRIVACY);
            return false;
        } else {
            return true;
        }
    }


}
