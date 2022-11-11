package com.example.sdklibrary.aboutfacebook;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.example.sdklibrary.aboutgoogle.GoogleSDK;
import com.example.sdklibrary.base.SdkBaseThreeSDK;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Date:2022-09-16
 * Time:19:31
 * author:colin
 */
public class FacebookSDK extends SdkBaseThreeSDK{

    private FacebookSDK(){

    }

    public static FacebookSDK instance ;

    public static FacebookSDK getInstance(){
        if (instance == null){
            instance = new FacebookSDK();
        }
        return instance;
    }

    // Initialize Facebook Login button
    private CallbackManager mFacebookCallbackManager = CallbackManager.Factory.create();

    /**
     * 初始化Facebook
     */
    public void sdkInitialize(Application context) {
        FacebookSdk.sdkInitialize(context);
        AppEventsLogger.activateApp(context);
    }

    public void LoginClick(Activity activity){
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile"));
        //Login Callback registration
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(getApplicationContext(), "in LoginResult on success", Toast.LENGTH_LONG).show();
                LoggerUtils.i(LogTAG.facebookLogin,"in LoginResult on success");

                //facebook授权成功
                if (loginResult != null) {
                    AccessToken accessToken = loginResult.getAccessToken();
                    if (accessToken != null) {
                        String token = accessToken.getToken();
                        String fb_uid = accessToken.getUserId();
                        GameLoginSuccess(activity,fb_uid);
                    }
                }
            }

            @Override
            public void onCancel() {
                Log.d("FacebookLogin", "in FaceBookLoginResult on cancel");
                LoggerUtils.i(LogTAG.facebookLogin,"in FaceBookLoginResult on cancel");

                GameLoginCancel(activity,"");
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), "in FaceBookLoginResult on error"+exception, Toast.LENGTH_LONG).show();

                LoggerUtils.i(LogTAG.facebookLogin,"in FaceBookLoginResult on error" +exception);
                GameLoginFail(activity,exception.toString());
            }
        });
    }





    public void Logout(){
        LoginManager.getInstance().logOut();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity){
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //游戏Facebook登陆成功
    public void GameLoginSuccess(Activity activity,String accountId){
        LoggerUtils.i("+++Facebook登录成功");
        qudaoLogin("facebook_"+accountId,activity);
    }
    //游戏Facebook登陆失败
    public void GameLoginFail(Activity activity,String msg){
        Delegate.loginlistener.callback( SDKStatusCode.FAILURE,msg);
        LoggerUtils.i("+++Facebook登录失败"+msg);
    }
    //游戏Facebook登陆取消
    public void GameLoginCancel(Activity activity,String msg){
        Delegate.loginlistener.callback( SDKStatusCode.CANCEL,msg);
        LoggerUtils.i("+++Facebook登录取消"+msg);
    }
}
