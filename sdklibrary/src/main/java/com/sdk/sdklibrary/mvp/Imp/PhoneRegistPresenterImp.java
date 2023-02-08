package com.sdk.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.sdk.sdklibrary.base.GameSdkApplication;
import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.model.MVPLoginResultBean;
import com.sdk.sdklibrary.mvp.model.MVPPhoneRegisterBean;
import com.sdk.sdklibrary.mvp.model.user.SDKUserResult;
import com.sdk.sdklibrary.mvp.presenter.PhoneRegistPresenter;
import com.sdk.sdklibrary.mvp.view.MVPPhoneRegistView;
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
 *@author colin
 * Date:2023-02-08
 * 注册逻辑类 请求---响应判断---接口回调
 */

public class PhoneRegistPresenterImp implements PhoneRegistPresenter {

    private String phoneNumber;
    private String code;

    private MVPPhoneRegistView mvpPhoneRegistView;

    @Override
    public void attachView(MVPPhoneRegistView mvpPhoneRegistView) {
        this.mvpPhoneRegistView = mvpPhoneRegistView;
    }

    @Override
    public void phoneLoginGetCode(String phoneNumber, Context context) {
        if ((!TextUtils.isEmpty(phoneNumber))) {

            getCodeMethod(HttpUrlConstants.getPhoneCodeUrl(), phoneNumber);
        }
    }

    @Override
    public void phoneLogin(MVPPhoneRegisterBean user, Context context) {
        phoneNumber = user.getPhoneNumber().toString().trim();
        code = user.getCode().toString().trim();

        if ((!TextUtils.isEmpty(phoneNumber)) && (!TextUtils.isEmpty(code))) {
            loginMethod(HttpUrlConstants.getPhoneCodeLoginUrl(), phoneNumber, code);
        } else {
            mvpPhoneRegistView.showAppInfo("", "手机号码或验证码输入为空");
        }
    }

    private void getCodeMethod(String url, String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode, "responseBody:" + result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>() {
                }.getType());

                int dataCode = bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS) {
                    mvpPhoneRegistView.verificationCodeSuccess(ConstData.PHONECODE_SUCCESS, msg);
                    LoggerUtils.i(LogTAG.phonecode, "phonecode Success");
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpPhoneRegistView, dataCode);
                    mvpPhoneRegistView.verificationCodeFailed(ConstData.PHONECODE_FAILURE, msg);
                    LoggerUtils.i(LogTAG.phonecode, "phonecode Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpPhoneRegistView.verificationCodeFailed(ConstData.PHONECODE_FAILURE, HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpPhoneRegistView.verificationCodeFailed(ConstData.PHONECODE_FAILURE, HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    private void loginMethod(String url, String phone, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode, "responseBody:" + result);

                ApiResponse<MVPLoginResultBean> mvpLoginResultBean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<MVPLoginResultBean>>() {
                }.getType());

                int dataCode = mvpLoginResultBean.getCode();
                String msg = mvpLoginResultBean.getMsg();


                SDKUserResult user = new SDKUserResult();
                //保存用户名和密码，还有用户昵称
                String nickname = null;
                String md5password = null;
                String uid = "";
                String ticket = "";

                String phone = "";
                boolean realname = false;
                if (mvpLoginResultBean.getData() != null) {
                    nickname = mvpLoginResultBean.getData().getUsername();
                    uid = mvpLoginResultBean.getData().getUid();
                    ticket = mvpLoginResultBean.getData().getTicket();
                    phone = mvpLoginResultBean.getData().getPhone();
                    realname = mvpLoginResultBean.getData().getRealName();

                    user.setUid(uid);
                    user.setUsername(nickname);
                    user.setToken(ticket);
                }

                if (dataCode == HttpUrlConstants.BZ_SUCCESS) {
                    mvpPhoneRegistView.loginSuccess(ConstData.LOGIN_SUCCESS, user);
                    LoggerUtils.i(LogTAG.phonecode, "responseBody: phonelogin Success");
                    GameSdkApplication.getInstance().setTicket(ticket);
                    SaveUserData(phone, md5password, nickname, uid, phone, realname);
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpPhoneRegistView, dataCode);
                    mvpPhoneRegistView.loginFailed(ConstData.LOGIN_FAILURE, msg);
                    LoggerUtils.i(LogTAG.phonecode, "responseBody: phonelogin Failure");
                }


            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpPhoneRegistView.loginFailed(ConstData.PHONECODE_FAILURE, HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpPhoneRegistView.loginFailed(ConstData.PHONECODE_FAILURE, HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpPhoneRegistView = null;
    }


    //使用SharedPreference来存储登陆状态
    public void SaveUserData(String username, String password, String nickname, String uid, String phone, boolean realname) {
        LoggerUtils.i(LogTAG.phonecode, "SaveUserData to SharedPreference");
        SPDataUtils.getInstance().saveLoginData(username, password, nickname, uid, phone, realname);
    }

}
