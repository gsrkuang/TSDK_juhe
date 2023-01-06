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
import com.example.sdklibrary.mvp.model.MVPLoginResultBean;
import com.example.sdklibrary.mvp.model.MVPRegistResultBean;
import com.example.sdklibrary.mvp.model.MVPRegisterBean;
import com.example.sdklibrary.mvp.presenter.RegistPresenter;
import com.example.sdklibrary.mvp.view.MVPRegistView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bolin
 * 注册逻辑类 请求---响应判断---接口回调
 */

public class RegistPresenterImp implements RegistPresenter {

    private String userName;
    private String passWord;

    private MVPRegistView mvpRegistView;

    @Override
    public void attachView(MVPRegistView mvpRegistView) {
        this.mvpRegistView = mvpRegistView;
    }

    @Override
    public void regist(MVPRegisterBean user, Context context) {
        userName = user.getUserName().toString().trim();
        passWord = user.getPassWord().toString().trim();

        if ((!TextUtils.isEmpty(userName)) && (!TextUtils.isEmpty(passWord)) ) {
            registMethod( HttpUrlConstants.getRegisterUrl(),userName,passWord);
        } else {
            mvpRegistView.showAppInfo("","帐号或密码输入为空");
        }
    }

    private void registMethod(String url,String userName,String passWord){
        Map<String,String> map = new HashMap<>();
        map.put("account",userName);
        map.put("password",passWord);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.register,"responseBody:"+result);
                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpRegistView.registSuccess(ConstData.REGIST_SUCCESS,bean.getData());
                    LoggerUtils.i(LogTAG.register,"regist Success");
                }else {
                    //根据不同dataCode做吐司提示
                    LogDataCode(dataCode);
                    mvpRegistView.registFailed(ConstData.REGIST_FAILURE,msg);
                    LoggerUtils.i(LogTAG.register,"regist Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpRegistView.registFailed(ConstData.REGIST_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpRegistView.registFailed(ConstData.REGIST_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpRegistView = null;
    }

    //根据DataCode打印日志
    public void LogDataCode(int dataCode) {

        switch (dataCode) {
            case HttpUrlConstants.BZ_INVALID_PARAM:
                mvpRegistView.showAppInfo("", "无效参数");
                break;
            case HttpUrlConstants.BZ_INVALID_APP_ID:
                mvpRegistView.showAppInfo("", "无效应用ID");
                break;
            case HttpUrlConstants.BZ_INVALID_ACCOUNT:
                mvpRegistView.showAppInfo("", "账号不合法");
                break;
            case HttpUrlConstants.BZ_INVALID_TOKEN:
                mvpRegistView.showAppInfo("", "Token已失效");
                break;
            case HttpUrlConstants.BZ_ERROR:
                mvpRegistView.showAppInfo("", "未知错误");
                break;
            case HttpUrlConstants.BZ_ERROR_ACCOUNT_PASSWORD:
                mvpRegistView.showAppInfo("", "账号或密码错误");
                break;
            case HttpUrlConstants.BZ_ERROR_SIGN:
                mvpRegistView.showAppInfo("", "签名错误");
                break;
            case HttpUrlConstants.BZ_ERROR_CODE:
                mvpRegistView.showAppInfo("", "验证码错误");
                break;
            case HttpUrlConstants.BZ_FAILURE:
                mvpRegistView.showAppInfo("", "账号已存在");
                break;
            default:
                break;
        }
    }
}
