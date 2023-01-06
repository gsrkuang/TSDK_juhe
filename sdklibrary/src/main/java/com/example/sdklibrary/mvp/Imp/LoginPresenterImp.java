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
import com.example.sdklibrary.mvp.presenter.LoginPresenter;
import com.example.sdklibrary.mvp.view.MVPLoginView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bolin
 * 登录逻辑类 请求---响应判断---
 * 通过MVPLoginView将结果回调出去给View
 *
 */

public class LoginPresenterImp  implements LoginPresenter {

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
            loginMethod( HttpUrlConstants.getLoginUrl(),userName,passWord );
        } else {
            mvpLoginView.showAppInfo("","帐号或密码输入为空");
        }

    }
    //测试登录账号：colin1 123456
    private void loginMethod(String url,String userName,String passWord){

        Map<String,String> map = new HashMap<>();
        map.put("account",userName);
        map.put("password",passWord);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.login,"responseBody:"+result);
                ApiResponse<MVPLoginResultBean> mvpLoginResultBean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<MVPLoginResultBean>>(){}.getType());

                int dataCode =  mvpLoginResultBean.getCode();
                String msg = mvpLoginResultBean.getMsg();

                //保存用户名和密码，还有用户昵称
                String nickname = null;
                String uid = "";
                String ticket = "";
                if (mvpLoginResultBean.getData() != null) {
                    nickname = mvpLoginResultBean.getData().getUsername();
                    uid = mvpLoginResultBean.getData().getUid();
                    ticket = mvpLoginResultBean.getData().getTicket();
                }

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpLoginView.loginSuccess(ConstData.LOGIN_SUCCESS,result);
                    LoggerUtils.i(LogTAG.login,"responseBody: login Success");

                    GameSdkApplication.getInstance().setTicket(ticket);
                    SaveUserData(userName,passWord,nickname,uid);
                } else {
                    //根据不同dataCode做吐司提示
                    LogDataCode(dataCode);
                    mvpLoginView.loginFailed(ConstData.LOGIN_FAILURE,msg);
                    LoggerUtils.i(LogTAG.login,"responseBody: login Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpLoginView.loginFailed(HttpUrlConstants.SERVER_ERROR,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpLoginView.loginFailed(HttpUrlConstants.NET_NO_LINKING,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpLoginView = null;
    }

    //使用SharedPreference来存储登陆状态
    public void SaveUserData(String username ,String password ,String nickname ,String uid){
        LoggerUtils.i(LogTAG.login,"SaveUserData to SharedPreference");
        SPDataUtils.getInstance().saveLoginData(username,password,nickname,uid);
    }

    //根据DataCode打印日志
    public void LogDataCode(int dataCode){

        switch (dataCode){
            case HttpUrlConstants.BZ_INVALID_PARAM:
                mvpLoginView.showAppInfo("","无效参数");
                break;
            case HttpUrlConstants.BZ_INVALID_APP_ID:
                mvpLoginView.showAppInfo("","无效应用ID");
                break;
            case HttpUrlConstants.BZ_INVALID_ACCOUNT:
                mvpLoginView.showAppInfo("","账号不合法");
                break;
            case HttpUrlConstants.BZ_INVALID_TOKEN:
                mvpLoginView.showAppInfo("","Token已失效");
                break;
            case HttpUrlConstants.BZ_ERROR:
                mvpLoginView.showAppInfo("","未知错误");
                break;
            case HttpUrlConstants.BZ_ERROR_ACCOUNT_PASSWORD:
                mvpLoginView.showAppInfo("","账号或密码错误");
                break;
            case HttpUrlConstants.BZ_ERROR_SIGN:
                mvpLoginView.showAppInfo("","签名错误");
                break;
            case HttpUrlConstants.BZ_ERROR_CODE:
                mvpLoginView.showAppInfo("","验证码错误");
                break;
            case HttpUrlConstants.BZ_FAILURE:
                mvpLoginView.showAppInfo("", "账号已存在");
                break;
            default:
                break;
        }



    }



}
