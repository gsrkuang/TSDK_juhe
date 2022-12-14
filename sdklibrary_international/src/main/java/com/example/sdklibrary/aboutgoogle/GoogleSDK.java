package com.example.sdklibrary.aboutgoogle;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.sdklibrary.base.SdkBaseThreeSDK;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.LoginPresenterImp;
import com.example.sdklibrary.mvp.model.MVPLoginBean;
import com.example.sdklibrary.mvp.view.MVPLoginView;
import com.example.sdklibrary.mvp.view.MVPRegistView;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.facebook.login.LoginManager;
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
                    LoggerUtils.i(LogTAG.googleLogin, "task???null");
                }
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    LoggerUtils.i(LogTAG.googleLogin, "Id:" + account.getId() + "/n|Email:" + account.getEmail() + "/n|IdToken:" + account.getIdToken());
                    GameLoginSuccess(activity, account.getId());
                } catch (ApiException e) {
                    e.printStackTrace();
                    if ("12501: ".equals(e.getMessage())) {
                        GameLoginCancel(activity, "????????????");
                    } else {
                        GameLoginFail(activity, e.getMessage());
                    }
                    LoggerUtils.e(LogTAG.googleLogin, "ApiException:" + e.getMessage());

                }
                break;
        }

    }

    //??????google????????????
    public void GameLoginSuccess(Activity activity, String accountId) {
        //??????????????????/???????????? ?????? --?????????????????????????????????????????????
        qudaoLogin("google_" + accountId, activity);
        LoggerUtils.i("+++??????????????????");
    }

    //??????google????????????
    public void GameLoginFail(Activity activity, String msg) {

        Delegate.loginlistener.callback(SDKStatusCode.FAILURE, msg);
        LoggerUtils.i("+++??????????????????" + msg);
    }

    //??????google????????????
    public void GameLoginCancel(Activity activity, String msg) {

        Delegate.loginlistener.callback(SDKStatusCode.CANCEL, msg);
        LoggerUtils.i("+++??????????????????" + msg);

    }

}
