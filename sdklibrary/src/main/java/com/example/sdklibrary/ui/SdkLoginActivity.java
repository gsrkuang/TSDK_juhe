package com.example.sdklibrary.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sdklibrary.R;
import com.example.sdklibrary.aboutfacebook.FacebookSDK;
import com.example.sdklibrary.aboutgoogle.GoogleSDK;
import com.example.sdklibrary.abouttaptap.TapTapSDK;
import com.example.sdklibrary.base.SdkBaseActivity;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.example.sdklibrary.mvp.model.MVPLoginBean;
import com.example.sdklibrary.mvp.view.MVPLoginView;
import com.example.sdklibrary.tools.LoggerUtils;

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

    private String appkey;

    //从服务器获取idtoken google 、facebook的初始化参数写在了androidmanifest的meta标签中
    private String googleRequestIdToken ="431344480217-h57ic0pucch447opi29tkidp4bug09b9.apps.googleusercontent.com";
    //从服务器获取ClientID taptap
    private String taptapClientID = "b8jxty2rhkryzv6xl6";


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

        appkey = getIntent().getStringExtra("appkey");
        //得到appkey后，从服务器返回得到各种配置好的idtoken，然后再进行google taptap facebook的初始化
        //此处写网络请求

        //初始化Google登陆
        GoogleSDK.getInstance().signInClient(this, googleRequestIdToken);
        TapTapSDK.getInstance().init(SdkLoginActivity.this, taptapClientID);
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
            GoogleSDK.getInstance().LoginClick(this,googleRequestIdToken);
        } else if (id == R.id.loginButtonFacebook) {
            FacebookSDK.getInstance().LoginClick(this);
        } else if (id == R.id.loginButtonTaptap) {
            TapTapSDK.getInstance().LoginClick(this);
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

        Delegate.listener.callback(SDKStatusCode.SUCCESS, "login success");
        LoggerUtils.i("登录成功");

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
