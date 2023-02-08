package com.sdk.sdklibrary.call;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.sdk.sdklibrary.base.GameSdkApplication;
import com.sdk.sdklibrary.callback.SdkCallbackListener;
import com.sdk.sdklibrary.config.ConfigInfo;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.Imp.LogoutPresenterImp;
import com.sdk.sdklibrary.mvp.Imp.SdkInitPresenterImp;
import com.sdk.sdklibrary.mvp.model.MVPPayCodeBean;
import com.sdk.sdklibrary.mvp.model.MVPPlayerBean;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;
import com.sdk.sdklibrary.mvp.presenter.LogoutPresenter;
import com.sdk.sdklibrary.mvp.presenter.SdkInitPresenter;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.SPDataUtils;
import com.sdk.sdklibrary.ui.SdkPayActivity;
import com.sdk.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.sdk.sdklibrary.ui.dialogfragment.SdkUserCenterDialogFragment;
import com.sdk.sdklibrary.ui.view.AutoLoginDialog;
import com.sdk.sdklibrary.ui.view.FloatIconView;

/**
 *@author colin
 * Date:2023-02-08
 * 供接入使用SDK开发人员调用的核心类
 */

public class GameSdk {
    private boolean checkInit;

    private FloatIconView floatIconView;

    private final String INIT_ERROR = "初始化失败，请先成功初始化";

    private LogoutPresenter logoutPresenterImp = new LogoutPresenterImp();
    private SdkInitPresenter sdkInitPresenterImp = new SdkInitPresenterImp();

    private GameSdk() {
    }

    private volatile static GameSdk sdkLogic;

    public static GameSdk getInstance() {
        if (sdkLogic == null) {
            synchronized (GameSdk.class) {
                if (sdkLogic == null) {
                    sdkLogic = new GameSdk();
                }
            }
        }
        return sdkLogic;
    }

    //游戏初始化:
    //这里没有商业接口,固定是初始化成功,实际开发需要根据后台去判断成功/失败
    //只有当初始化的时候才可以进行后续操作
    public void sdkInit(Activity context, String appkey, final SdkCallbackListener<String> callback) {
        ConfigInfo.setScreenIsPORTRAIT(context);
        //这里添加一个SdkInitPresenterImp

        sdkInitPresenterImp.init(appkey, context, new SdkInitPresenter.InitListener() {
            @Override
            public void success(String msg) {
                checkInit = true;

                GameSdkApplication.getInstance().setAppkey(appkey);
                //弹出悬浮窗
                sdkInitFloatView(context);
                //全局保存初始化成功的appkey
                callback.callback(SDKStatusCode.SUCCESS, "初始化成功");
            }

            @Override
            public void fail(String msg) {
                checkInit = false;
                //提示初始化失败信息
                showToast(context,msg);

                callback.callback(SDKStatusCode.FAILURE, "初始化失败"+msg);
            }
        });


//        if (checkInit) {
//            checkInit = true;
//            //弹出悬浮窗
//            sdkInitFloatView(context);
//            //全局保存初始化成功的appkey
//            GameSdkApplication.getInstance().setAppkey(appkey);
//            callback.callback(SDKStatusCode.SUCCESS, "初始化成功");
//        }else {
//
//            //提示请先初始化
//            showToast(context,INIT_ERROR);
//            return;
//        }
    }

    //登录:
    //理论上初始化成功才可以登录 这里的接口使用的是 玩Android 开放接口
    public void sdkLogin(Activity context, final SdkCallbackListener<SDKUserResult> loginCallback) {
        LoggerUtils.i("SdkLogic Login");
        if (checkInit) {

            if (!"".equals(SPDataUtils.getInstance().getUserAccount())){

                AutoLoginDialog autoLoginDialog = new AutoLoginDialog(context);
                autoLoginDialog.show();
            }else {
                SdkLoginDialogFragment dialog = SdkLoginDialogFragment.getInstance();
                dialog.show(context.getFragmentManager(),"SdkLoginDialogFragment");
                dialog.setCancelable(false);
            }



            Delegate.loginlistener = loginCallback;
        } else {
//            loginCallback.callback(SDKStatusCode.LOGOUT_FAILURE, ConstData.LOGIN_FAILURE);
            //提示请先初始化
            showToast(context,INIT_ERROR);
            return;
        }

    }

    public void sdkLogin(Activity context) {
        LoggerUtils.i("SdkLogic Login");
        if (checkInit) {

            SdkLoginDialogFragment dialog = SdkLoginDialogFragment.getInstance();
            dialog.show(context.getFragmentManager(),"SdkLoginDialogFragment");
            dialog.setCancelable(false);
        } else {
            //提示请先初始化
            showToast(context,INIT_ERROR);
            return;
        }

    }


    //支付:
    //需要将SDK支付信息传递给具体的方式中
    public void sdkPay(Activity context, MVPPayCodeBean bean, final SdkCallbackListener<String> callback) {
        LoggerUtils.i("SdkLogic Pay");
        if (checkInit) {
            Intent intent = new Intent(context, SdkPayActivity.class);
            intent.putExtra("payBean",bean);
            context.startActivity(intent);
            Delegate.paylistener = callback;
        } else {
            //提示请先初始化
            showToast(context,INIT_ERROR);
            return;
        }
    }


    //提交游戏信息：
    public void subGameInfoMethod(Activity activity , MVPPlayerBean bean) {
        if (checkInit) {

            //doing something:
            LoggerUtils.i("submit Player Information");
        } else {
            //提示请先初始化
            showToast(activity,INIT_ERROR);
            return;
        }

        //step:
        //Build HttpRequest   ----> server get Request ------->server return ResponseBody
        //This function is mainly used to record and count player information
    }

    //直接调用登出接口
    public void sdkLogout(Activity activity){
        if (checkInit) {
            logoutPresenterImp.sdkLogout(activity);
        } else {
            //提示请先初始化
            showToast(activity,INIT_ERROR);
            return;
        }
    }

    //弹出登出提示,当SdkCallbackListener已经被初始化
/*    public void showLogoutDialog(Activity act) {
        //这里应该弹出退出窗口的确认框，确认框确认后再退出登录
        DialogTips dialogTips = new DialogTips(act);
        dialogTips.setTitle("退出登录");
        dialogTips.setMessage("确认要退出当前账号吗？");
        dialogTips.setCancelOnClickListener(new DialogTips.onCancelOnClickListener() {
            @Override
            public void onCancelClick() {
                dialogTips.dismiss();
            }
        });
        dialogTips.setLoginOnClickListener(new DialogTips.onLoginOnClickListener() {
            @Override
            public void onLoginClick() {
                SPDataUtils.getInstance().clearLogin();
                dialogTips.dismiss();
                SdkUserCenterDialogFragment.getInstance().dismiss();
                GameSdkLogic.getInstance().sdkFloatViewHide();
                sdkLogin(act);

                Delegate.loginlistener.callback(SDKStatusCode.LOGOUT_SUCCESS, new SDKUserResult());
            }
        });
        dialogTips.show();

    }*/

    public void sdkInitFloatView(Activity context) {
//        floatIconView = FloatIconView.getInstance(context);
        floatIconView = new FloatIconView(context);
        floatIconView.setOnFloatIconViewClickListener(new FloatIconView.OnFloatIconViewClickListener() {
            @Override
            public void onItemClick() {
                boolean loginStatus = SPDataUtils.getInstance().getLoginStatus();
                if (loginStatus) {

                    SdkUserCenterDialogFragment dialog = SdkUserCenterDialogFragment.getInstance();
                    if (!dialog.isAdded()){
                        dialog.show(context.getFragmentManager(),"SdkLoginDialogFragment");
                    }

                } else {

                    //弹出登陆界面
                    sdkLogin(context);

                }

            }
        });
        //初始化成功后先隐藏
        sdkFloatViewHide();
    }

    public void sdkFloatViewShow() {
        floatIconView.show();
    }

    public void sdkFloatViewHide() {
        floatIconView.hide();
    }

    public void showToast(Activity activity,String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

}
