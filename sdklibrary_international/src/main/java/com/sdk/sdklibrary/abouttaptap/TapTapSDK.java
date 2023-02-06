package com.sdk.sdklibrary.abouttaptap;

import android.app.Activity;
import android.content.Context;

import com.sdk.sdklibrary.base.SdkBaseThreeSDK;
import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.AccountGlobalError;
import com.taptap.sdk.Profile;
import com.taptap.sdk.TapLoginHelper;

/**
 * Date:2022-09-16
 * Time:14:30
 * author:colin
 */
public class TapTapSDK extends SdkBaseThreeSDK {
    private boolean initTag = false;
    public static TapTapSDK tapTapSDK ;
    public TapTapSDK(){

    }
    public static TapTapSDK getInstance(){
        if (tapTapSDK == null){
            tapTapSDK = new TapTapSDK();
        }
        return tapTapSDK;
    }

    public void LoginClick(Activity activity){
        // 登录
        TapLoginHelper.startTapLogin(activity, TapLoginHelper.SCOPE_PUBLIC_PROFILE);
    }
    public void Logout(){
        TapLoginHelper.logout();
    }

    public void init(Context context, String clientID){
        // 适用于中国大陆
        TapLoginHelper.init(context, clientID);
        initTag = true;
    }
    public void registerLoginCallback(Activity activity){
        // 实例化监听
        TapLoginHelper.TapLoginResultCallback loginCallback = new TapLoginHelper.TapLoginResultCallback() {
            @Override
            public void onLoginSuccess(AccessToken token) {
                LoggerUtils.i(LogTAG.taptapLogin,"TapTap authorization succeed");
                // 开发者调用 TapLoginHelper.getCurrentProfile() 可以获得当前用户的一些基本信息，例如名称、头像。
                Profile profile = TapLoginHelper.getCurrentProfile();
                GameLoginSuccess(activity,profile.getUnionid());
            }

            @Override
            public void onLoginCancel() {
                LoggerUtils.i(LogTAG.taptapLogin,"TapTap authorization cancelled");
                GameLoginCancel(activity,"TapTap authorization cancelled");
            }

            @Override
            public void onLoginError(AccountGlobalError globalError) {
                LoggerUtils.i(LogTAG.taptapLogin,"TapTap authorization failed. cause: " + globalError.getMessage());
                GameLoginFail(activity,globalError.getMessage());
            }
        };
        // 注册监听
        TapLoginHelper.registerLoginCallback(loginCallback);
    }

    //游戏Taptap登陆成功
    public void GameLoginSuccess(Activity activity, String accountId){
        qudaoLogin("taptap_"+accountId,activity);
        LoggerUtils.i("+++Taptap登录成功");
    }
    //游戏Taptap登陆失败
    public void GameLoginFail(Activity activity,String msg){
        Delegate.loginlistener.callback( SDKStatusCode.FAILURE,msg);
        LoggerUtils.i("+++Taptap登录失败"+msg);
    }
    //游戏Taptap登陆取消
    public void GameLoginCancel(Activity activity,String msg){
        Delegate.loginlistener.callback( SDKStatusCode.CANCEL,msg);
        LoggerUtils.i("+++Taptap登录取消"+msg);
    }
}
