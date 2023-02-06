package com.sdk.sdklibrary.mvp.Imp;

import android.app.Activity;
import android.content.Context;

import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.call.GameSdkLogic;
import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;
import com.sdk.sdklibrary.mvp.presenter.LogoutPresenter;
import com.sdk.sdklibrary.mvp.view.MVPLogoutView;
import com.sdk.sdklibrary.tools.GsonUtils;
import com.sdk.sdklibrary.tools.HttpRequestUtil;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.SPDataUtils;
import com.sdk.sdklibrary.tools.ShowInfoUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Date:2023-02-01
 * Time:10:48
 * author:colin
 *
 * 登出使用
 */
public class LogoutPresenterImp implements LogoutPresenter {

    private MVPLogoutView mvpLogoutView;
    @Override
    public void attachView(MVPLogoutView mvpLogoutView) {
        this.mvpLogoutView = mvpLogoutView;
    }

    @Override
    public void detachView() {
        this.mvpLogoutView = null;
    }


    @Override
    public void logout(Context context) {

        Map<String,String> map = new HashMap<>();

        HttpRequestUtil.okPostFormRequest(HttpUrlConstants.getLogoutUrl(), map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){


                    mvpLogoutView.logoutSuccess(ConstData.LOGOUT_SUCCESS,new SDKUserResult());
                    LoggerUtils.i(LogTAG.userinfo,"logout success");
                }else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpLogoutView,dataCode);
                    mvpLogoutView.logoutFailed(ConstData.LOGOUT_FAILURE,bean.getData());
                    LoggerUtils.i(LogTAG.userinfo,"logout fail");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpLogoutView.logoutFailed(ConstData.LOGOUT_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpLogoutView.logoutFailed(ConstData.LOGOUT_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void sdkLogout(Activity act) {
        Map<String,String> map = new HashMap<>();

        HttpRequestUtil.okPostFormRequest(HttpUrlConstants.getLogoutUrl(), map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){

                    SPDataUtils.getInstance().clearLogin();
                    GameSdkLogic.getInstance().sdkFloatViewHide();
                    GameSdkLogic.getInstance().sdkLogin(act);

                    Delegate.loginlistener.callback(SDKStatusCode.LOGOUT_SUCCESS, new SDKUserResult());

                    LoggerUtils.i(LogTAG.userinfo,"sdklogout success");
                }else {
                    //登出失败，请先登录

                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpLogoutView,dataCode);
                    mvpLogoutView.logoutFailed(ConstData.LOGOUT_FAILURE,bean.getData());
                    LoggerUtils.i(LogTAG.userinfo,"sdklogout fail");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpLogoutView.logoutFailed(ConstData.LOGOUT_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpLogoutView.logoutFailed(ConstData.LOGOUT_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }
}
