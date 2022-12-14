package com.example.sdklibrary.call;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.sdklibrary.base.GameSdkApplication;
import com.example.sdklibrary.callback.SdkCallbackListener;
import com.example.sdklibrary.config.ConfigInfo;
import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.model.MVPPayCodeBean;
import com.example.sdklibrary.mvp.model.MVPPlayerBean;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.example.sdklibrary.ui.SdkPayActivity;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.example.sdklibrary.ui.dialogfragment.SdkUserCenterDialogFragment;
import com.example.sdklibrary.ui.view.DialogTips;
import com.example.sdklibrary.ui.view.FloatIconView;

/**
 * Created cbl
 * 供接入使用SDK开发人员调用的核心类
 */

public class GameSdkLogic {
    private boolean checkInit;

    private FloatIconView floatIconView;

    private GameSdkLogic() {
    }

    private volatile static GameSdkLogic sdkLogic;

    public static GameSdkLogic getInstance() {
        if (sdkLogic == null) {
            synchronized (GameSdkLogic.class) {
                if (sdkLogic == null) {
                    sdkLogic = new GameSdkLogic();
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
        checkInit = true;
        if (checkInit) {
            //弹出悬浮窗
            sdkInitFloatView(context);
            //全局保存初始化成功的appkey
            GameSdkApplication.getInstance().setAppkey(appkey);
            callback.callback(SDKStatusCode.SUCCESS, "初始化成功");
        }
    }

    //登录:
    //理论上初始化成功才可以登录 这里的接口使用的是 玩Android 开放接口
    public void sdkLogin(Activity context, String appkey, final SdkCallbackListener<String> loginCallback) {
        LoggerUtils.i("SdkLogic Login");
        if (checkInit) {

            SdkLoginDialogFragment dialog = SdkLoginDialogFragment.getInstance();
            dialog.show(context.getFragmentManager(),"SdkLoginDialogFragment");

            Delegate.loginlistener = loginCallback;
        } else {
            loginCallback.callback(SDKStatusCode.FAILURE, ConstData.INIT_FAILURE);

            return;
        }

    }

    public void sdkLogin(Activity context) {
        LoggerUtils.i("SdkLogic Login");
        if (checkInit) {

            SdkLoginDialogFragment dialog = SdkLoginDialogFragment.getInstance();
            dialog.show(context.getFragmentManager(),"SdkLoginDialogFragment");

        } else {
            if (Delegate.loginlistener != null) {
                Delegate.loginlistener.callback(SDKStatusCode.FAILURE, ConstData.INIT_FAILURE);
            }
            return;
        }

    }


    //支付:
    //需要将SDK支付信息传递给具体的方式中
    public void sdkPay(Context context, MVPPayCodeBean bean, final SdkCallbackListener<String> callback) {
        LoggerUtils.i("SdkLogic Pay");
        if (checkInit) {
            Intent intent = new Intent(context, SdkPayActivity.class);
            intent.putExtra("payBean",bean);
            context.startActivity(intent);
            Delegate.paylistener = callback;
        } else {
            callback.callback(SDKStatusCode.PAY_FAILURE, ConstData.PAY_FAILURE);
            return;
        }
    }


    //提交游戏信息：
    public void subGameInfoMethod(MVPPlayerBean bean) {
        //doing something:
        LoggerUtils.i("submit Player Information");
        //step:
        //Build HttpRequest   ----> server get Request ------->server return ResponseBody
        //This function is mainly used to record and count player information
    }


    //弹出登出提示,当SdkCallbackListener已经被初始化
    public void showLogoutDialog(Activity act) {
        //这里应该弹出退出窗口的确认框，确认框确认后再退出登录
        DialogTips dialogTips = new DialogTips(act);
        dialogTips.setTitle("退出登录");
        dialogTips.setMessage("确认要退出当前账号吗？");
        dialogTips.setCancelOnClickListener(new DialogTips.onCancelOnClickListener() {
            @Override
            public void onCancelClick() {
                dialogTips.dismiss();
                Delegate.loginlistener.callback(SDKStatusCode.LOGOUT_CANCEL, "logout cancel");
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
                Delegate.loginlistener.callback(SDKStatusCode.LOGOUT_SUCCESS, "logout success");
            }
        });
        dialogTips.show();

    }

    public void sdkInitFloatView(Activity context) {
        floatIconView = new FloatIconView(context);
        floatIconView.setOnFloatIconViewClickListener(new FloatIconView.OnFloatIconViewClickListener() {
            @Override
            public void onItemClick() {
                boolean loginStatus = SPDataUtils.getInstance().getLoginStatus();
                if (loginStatus) {

                    SdkUserCenterDialogFragment dialog = SdkUserCenterDialogFragment.getInstance();
                    dialog.show(context.getFragmentManager(),"SdkLoginDialogFragment");


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

    public void onActivityResult(int requestCode, int resultCode, Intent data,Activity activity) {

    }


}
