package com.sdk.sdklibrary.aboutgoogle;

import android.app.Activity;
import android.content.Intent;

import com.sdk.sdklibrary.base.SdkBaseThreeSDK;
import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 * Date:2022-09-14
 * Time:15:14
 * author:colin
 */
public class GoogleSDK extends SdkBaseThreeSDK {

    public static final int SIGN_LOGIN = 901;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginPresenterImp loginPresenterImp;

    private GoogleSDK() {

    }

    public static GoogleSDK instance;

    public static GoogleSDK getInstance() {
        if (instance == null) {
            instance = new GoogleSDK();
        }
        return instance;
    }

    public void LoginClick(Activity activity, String googleRequestIdToken) {
        activity.startActivityForResult(GoogleSDK.getInstance().getGoogleIntent(activity, googleRequestIdToken), GoogleSDK.SIGN_LOGIN);
    }

    public void Logout() {
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut();
        }
    }

    public void signInClient(Activity activity, String requestIdToken) {
        if (mGoogleSignInClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions
                    .DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(requestIdToken)
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        }

    }

    public Intent getGoogleIntent(Activity activity, String requestIdToken) {
        Intent signInInten;
        if (mGoogleSignInClient == null) {
            signInClient(activity, requestIdToken);
        }
        signInInten = mGoogleSignInClient.getSignInIntent();
        return signInInten;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity) {
        switch (requestCode) {
            case SIGN_LOGIN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                if (task == null) {
                    LoggerUtils.i(LogTAG.googleLogin, "task：null");
                }
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    LoggerUtils.i(LogTAG.googleLogin, "Id:" + account.getId() + "/n|Email:" + account.getEmail() + "/n|IdToken:" + account.getIdToken());
                    GameLoginSuccess(activity, account.getId());
                } catch (ApiException e) {
                    e.printStackTrace();
                    if ("12501: ".equals(e.getMessage())) {
                        GameLoginCancel(activity, "登陆取消");
                    } else {
                        GameLoginFail(activity, e.getMessage());
                    }
                    LoggerUtils.e(LogTAG.googleLogin, "ApiException:" + e.getMessage());

                }
                break;
        }

    }

    //游戏google登陆成功
    public void GameLoginSuccess(Activity activity, String accountId) {
        //需要自动登录/自动注册 用户 --目前第三方登陆的账号和密码相同
        qudaoLogin("google_" + accountId, activity);
        LoggerUtils.i("+++谷歌登录成功");
    }

    //游戏google登陆失败
    public void GameLoginFail(Activity activity, String msg) {

        Delegate.loginlistener.callback(SDKStatusCode.FAILURE, msg);
        LoggerUtils.i("+++谷歌登录失败" + msg);
    }

    //游戏google登陆取消
    public void GameLoginCancel(Activity activity, String msg) {

        Delegate.loginlistener.callback(SDKStatusCode.CANCEL, msg);
        LoggerUtils.i("+++谷歌登录取消" + msg);

    }

}
