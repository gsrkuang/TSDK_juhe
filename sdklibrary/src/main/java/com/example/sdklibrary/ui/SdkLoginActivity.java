package com.example.sdklibrary.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sdklibrary.R;
import com.example.sdklibrary.aboutfacebook.FacebookSDK;
import com.example.sdklibrary.aboutgoogle.GoogleSDK;
import com.example.sdklibrary.abouttaptap.TapTapSDK;
import com.example.sdklibrary.base.SdkBaseActivity;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.example.sdklibrary.mvp.model.MVPLoginBean;
import com.example.sdklibrary.mvp.view.MVPLoginView;
import com.example.sdklibrary.tools.LoggerUtils;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.taptap.sdk.TapLoginHelper;

/**
 * Created by tzw on 2018/6/4.
 * 登录
 */

public class SdkLoginActivity extends SdkBaseActivity implements MVPLoginView {

    private EditText username, passWord;
    private TextView login, speedRegister, forgetpassword;
    private LoginPresenterImp loginPresenterImp;
    private String mUserName, mPassWord;
    protected boolean accountTag, passwordTag;
    private ImageView loginGoogle, loginFacebook,loginTaptap;

    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;
    private final String LOGIN_FORMERROR = "帐号/密码长度格式错误";
    private final String LENGTH_EMPTY = "请检查帐号/密码输入";

    private String googleRequestIdToken;

    @Override
    public int getLayoutId() {

    /*    //因为横竖屏的UI需要适配,大家可以先写两套UI 通过适配文件进行配置，写法如下：
        if (ConfigInfo.allowPORTRAIT){
            return "竖屏布局";
        }else {
            return "横屏布局";
        }
        */
        return R.layout.login;
    }

    @Override
    public void initViews() {
        username = $(R.id.loginusername);
        passWord = $(R.id.loginpassword);
        login = $(R.id.mvplogin);
        speedRegister = $(R.id.mvpregister);
        forgetpassword = $(R.id.mvpforgetpassword);
        loginGoogle = $(R.id.loginButtonGoogle);
        loginFacebook = $(R.id.loginButtonFacebook);
        loginTaptap = $(R.id.loginButtonTaptap);
    }

    @Override
    public void initListener() {
        setOnClick(login);
        setOnClick(speedRegister);
        setOnClick(forgetpassword);
        setOnClick(loginGoogle);
        setOnClick(loginFacebook);
        setOnClick(loginTaptap);
    }

    @Override
    public void initData() {
        loginPresenterImp = new LoginPresenterImp();
        loginPresenterImp.attachView(this);

        googleRequestIdToken = getIntent().getStringExtra("googleRequestIdToken");
        //初始化Google登陆
        GoogleSDK.getInstance().signInClient(this, googleRequestIdToken);
        TapTapSDK.getInstance().init(SdkLoginActivity.this, "b8jxty2rhkryzv6xl6");
        //注册登录监听
        TapTapSDK.getInstance().registerLoginCallback(this);

    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.mvplogin) {
            loginMethod();
        } else if (id == R.id.mvpregister) {
            regist();
        } else if (id == R.id.mvpforgetpassword) {

        } else if (id == R.id.loginButtonGoogle) {
            startActivityForResult(GoogleSDK.getInstance().getGoogleIntent(this, googleRequestIdToken), GoogleSDK.SIGN_LOGIN);
        } else if (id == R.id.loginButtonFacebook) {
            FacebookSDK.getInstance().logInWithReadPermissions(this);
        } else if (id == R.id.loginButtonTaptap) {
            // 登录
            TapLoginHelper.startTapLogin(SdkLoginActivity.this, TapLoginHelper.SCOPE_PUBLIC_PROFILE);
        } else {

        }
    }

    private void regist() {
        startActivity(new Intent(this, SdkRegistActivity.class));
        finish();
    }

    private void loginMethod() {

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
                loginPresenterImp.login(bean, SdkLoginActivity.this);
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
        //这里可以添加游戏悬浮窗
        Delegate.listener.callback(SDKStatusCode.SUCCESS, "login success");
        LoggerUtils.i("登录成功");
        finish();
    }

    @Override
    public void loginFailed(String msg, String data) {
        Delegate.listener.callback(SDKStatusCode.FAILURE, "login failure");
        LoggerUtils.i("登录失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenterImp.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleSDK.getInstance().onActivityResult(requestCode, resultCode, data, this);
        FacebookSDK.getInstance().onActivityResult(requestCode, resultCode, data, this);

    }


}
