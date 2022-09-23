package com.example.sdklibrary.aboutgoogle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.tools.LoggerUtils;
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
public class GoogleSDK {

    public static final int SIGN_LOGIN = 901;
    private GoogleSignInClient mGoogleSignInClient;

    private GoogleSDK(){
    }

    public static GoogleSDK instance ;

    public static GoogleSDK getInstance(){
        if (instance == null){
            instance = new GoogleSDK();
        }
        return instance;
    }

    public void signInClient(Activity activity,String requestIdToken){
        if (mGoogleSignInClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions
                    .DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(requestIdToken)
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        }

    }

    public Intent getGoogleIntent(Activity activity,String requestIdToken) {
        Intent signInInten;
        if (mGoogleSignInClient == null) {
            signInClient(activity,requestIdToken);
        }
        signInInten = mGoogleSignInClient.getSignInIntent();
        return signInInten;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data,Activity activity){
        switch (requestCode) {
            case SIGN_LOGIN:
                Log.e("+++googleLogin","setActivityResultGoogle");
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                if (task == null) {
                    Log.e("+++googleLogin","task：null");
                }
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.e("+++googleLogin","Id:" + account.getId() + "/n|Email:" + account.getEmail() + "/n|IdToken:" + account.getIdToken());
                    GameLoginSuccess(activity,account.getId());
                } catch (ApiException e) {
                    e.printStackTrace();
                    GameLoginFail(activity,e.getMessage());
                    Log.e("+++googleLogin","ApiException:" + e.getMessage());

                }
                break;
        }

    }
    //游戏google登陆成功
    public void GameLoginSuccess(Activity activity,String accountId){
        Delegate.listener.callback( SDKStatusCode.SUCCESS,accountId);
        LoggerUtils.i("+++谷歌登录成功");
        activity.finish();
    }
    //游戏google登陆失败
    public void GameLoginFail(Activity activity,String msg){
        Delegate.listener.callback( SDKStatusCode.FAILURE,msg);
        LoggerUtils.i("+++谷歌登录失败"+msg);
    }
}
