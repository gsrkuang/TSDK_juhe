package com.sdk.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.mvp.model.MVPLoginBean;
import com.sdk.sdklibrary.mvp.model.MVPLoginResultBean;
import com.sdk.sdklibrary.mvp.presenter.LoginPresenter;
import com.sdk.sdklibrary.mvp.view.MVPLoginView;
import com.sdk.sdklibrary.tools.GsonUtils;
import com.sdk.sdklibrary.tools.HttpRequestUtil;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.SPDataUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by tzw on 2018/6/5.
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
    //测试登录账号：xiaowu 123456
    private void loginMethod(String url,String userName,String passWord){
        Map<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("password",passWord);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.login,"responseBody:"+result);
                MVPLoginResultBean mvpLoginResultBean = GsonUtils.GsonToBean(result, MVPLoginResultBean.class);

                int dataCode =  mvpLoginResultBean.getErrorCode();
                String msg = mvpLoginResultBean.getErrorMsg();

                //保存用户名和密码，还有用户昵称
                String nickname = null;
                int id = -1;
                if (mvpLoginResultBean.getData() != null) {
                    nickname = mvpLoginResultBean.getData().getUsername();
                    id = mvpLoginResultBean.getData().getId();
                }

                if (dataCode == 0){
                    mvpLoginView.loginSuccess(ConstData.LOGIN_SUCCESS,result);
                    LoggerUtils.i(LogTAG.login,"responseBody: login Success");
                    SaveUserData(userName,passWord,nickname,id);
                }else {
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
    public void SaveUserData(String username ,String password ,String nickname ,int id){
        LoggerUtils.i(LogTAG.login,"SaveUserData to SharedPreference");
        SPDataUtils.getInstance().saveLoginData(username,password,nickname,id);
    }



}
