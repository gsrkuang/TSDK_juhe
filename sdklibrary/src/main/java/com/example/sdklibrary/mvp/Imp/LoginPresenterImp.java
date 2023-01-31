package com.example.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.example.sdklibrary.base.GameSdkApplication;
import com.example.sdklibrary.callback.SdkCallbackListener;
import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.mvp.model.ApiResponse;
import com.example.sdklibrary.mvp.model.MVPLoginBean;
import com.example.sdklibrary.mvp.model.MVPLoginResultBean;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;
import com.example.sdklibrary.mvp.presenter.LoginPresenter;
import com.example.sdklibrary.mvp.view.MVPLoginView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.example.sdklibrary.tools.ShowInfoUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bolin
 * 登录逻辑类 请求---响应判断---
 * 通过MVPLoginView将结果回调出去给View
 */

public class LoginPresenterImp implements LoginPresenter {

    private String userName;
    private String passWord;
    private MVPLoginView mvpLoginView;


    @Override
    public void attachView(MVPLoginView mvpLoginView) {
        this.mvpLoginView = mvpLoginView;
    }

    @Override
    public void login(MVPLoginBean user, Context context) {
        userName = user.getUserName().toString().trim();
        passWord = user.getPassWord().toString().trim();

        if ((!TextUtils.isEmpty(userName)) && (!TextUtils.isEmpty(passWord))) {
            loginMethod(HttpUrlConstants.getLoginUrl(), userName, passWord);
        } else {
            mvpLoginView.showAppInfo("", "帐号或密码输入为空");
        }

    }

    @Override
    public void onekey(Context context) {

        //随机用户和密码
        String userName = getRandomAccount(ConstData.ONEKEY_ACCOUNT_DIGITS);
        String passWord = getRandomPassword(ConstData.ONEKEY_PASSWORD_DIGITS);

        if ((!TextUtils.isEmpty(userName)) && (!TextUtils.isEmpty(passWord))) {
            onekeyMethod(HttpUrlConstants.getOneKeyLoginUrl(), userName, passWord);
        } else {
            mvpLoginView.showAppInfo("", "帐号或密码输入为空");
        }
    }

    //测试登录账号：无
    private void onekeyMethod(String url, String userName, String passWord) {

        Map<String, String> map = new HashMap<>();
        map.put("account", userName);
        map.put("password", passWord);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.onekey, "responseBody:" + result);
                ApiResponse<MVPLoginResultBean> mvpLoginResultBean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<MVPLoginResultBean>>() {
                }.getType());

                int dataCode = mvpLoginResultBean.getCode();
                String msg = mvpLoginResultBean.getMsg();

                SDKUserResult user = new SDKUserResult();
                //保存用户名和密码，还有用户昵称
                String nickname = null;
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

                    user.setUsername(nickname);
                    user.setUid(uid);
                    user.setToken(ticket);
                }

                if (dataCode == HttpUrlConstants.BZ_SUCCESS) {

                    LoggerUtils.i(LogTAG.login, "responseBody: onekeylogin Success");

                    GameSdkApplication.getInstance().setTicket(ticket);

                    SaveOneKeyUserData(userName, passWord, nickname, uid,phone,realname);
                    SaveUserData(userName, passWord, nickname, uid,phone,realname);

                    mvpLoginView.onekeyloginSuccess(ConstData.LOGIN_SUCCESS, user);
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpLoginView, dataCode);
                    mvpLoginView.loginFailed(ConstData.LOGIN_FAILURE, msg);
                    LoggerUtils.i(LogTAG.login, "responseBody: login Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpLoginView.loginFailed(HttpUrlConstants.SERVER_ERROR, HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpLoginView.loginFailed(HttpUrlConstants.NET_NO_LINKING, HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    //测试登录账号：colin1 123456
    private void loginMethod(String url, String userName, String passWord) {

        Map<String, String> map = new HashMap<>();
        map.put("account", userName);
        map.put("password", passWord);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.login, "responseBody:" + result);
                ApiResponse<MVPLoginResultBean> mvpLoginResultBean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<MVPLoginResultBean>>() {
                }.getType());

                int dataCode = mvpLoginResultBean.getCode();
                String msg = mvpLoginResultBean.getMsg();

                SDKUserResult user = new SDKUserResult();
                //保存用户名和密码，还有用户昵称
                String nickname = null;
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

                    user.setUsername(nickname);
                    user.setUid(uid);
                    user.setToken(ticket);
                }

                if (dataCode == HttpUrlConstants.BZ_SUCCESS) {

                    mvpLoginView.loginSuccess(ConstData.LOGIN_SUCCESS, user);
                    LoggerUtils.i(LogTAG.login, "responseBody: login Success");

                    GameSdkApplication.getInstance().setTicket(ticket);

                    SaveUserData(userName, passWord, nickname, uid,phone,realname);
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpLoginView, dataCode);
                    mvpLoginView.loginFailed(ConstData.LOGIN_FAILURE, msg);
                    LoggerUtils.i(LogTAG.login, "responseBody: login Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpLoginView.loginFailed(HttpUrlConstants.SERVER_ERROR, HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpLoginView.loginFailed(HttpUrlConstants.NET_NO_LINKING, HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpLoginView = null;
    }

    //使用SharedPreference来存储登陆状态
    public void SaveUserData(String username, String password, String nickname, String uid,String phone,boolean realname) {
        LoggerUtils.i(LogTAG.login, "SaveUserData to SharedPreference");
        SPDataUtils.getInstance().saveLoginData(username, password, nickname, uid,phone,realname);
    }
    //使用SharedPreference来存储登陆状态
    public void SaveOneKeyUserData(String username, String password, String nickname, String uid,String phone,boolean realname) {
        LoggerUtils.i(LogTAG.login, "SaveOneKeyUserData to SharedPreference");
        SPDataUtils.getInstance().saveOneKeyLoginData(username, password, nickname, uid,phone,realname);
    }


    /**
     * 随机生成由数字、字母组成的N位密码
     *
     * @return 返回一个字符串
     */
    public static String getRandomPassword(int n) {
        char arr[] = new char[n];
        int i = 0;
        while (i < n) {
            char ch = (char) (int) (Math.random() * 124);
            if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9') {
                arr[i++] = ch;
            }
        }
        //将数组转为字符串
        return new String(arr);
    }

    /**
     * 随机生成由数字、字母组成的N位账号
     *
     * @return 返回一个字符串
     */
    public static String getRandomAccount(int n) {
        int y = 2;
        if (n>2){
            n = n-y;
        }
        //生成2位随机英文
        char arr[] = new char[y];
        int i = 0;
        while (i < y) {
            char ch = (char) (int) (Math.random() * 124);
            if (ch >= 'a' && ch <= 'z') {
                arr[i++] = ch;
            }
        }

        //生成8位随机英文
        char numarr[] = new char[n];
        int j = 0;
        while (j < n) {
            char ch = (char) (int) (Math.random() * 124);
            if (ch >= '0' && ch <= '9') {
                numarr[j++] = ch;
            }
        }

        String randomAccount = new String(arr) + new String(numarr);
        //将数组转为字符串
        return randomAccount ;
    }
}
