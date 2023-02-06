package com.sdk.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.model.MVPRegisterBean;
import com.sdk.sdklibrary.mvp.presenter.RegistPresenter;
import com.sdk.sdklibrary.mvp.view.MVPRegistView;
import com.sdk.sdklibrary.tools.GsonUtils;
import com.sdk.sdklibrary.tools.HttpRequestUtil;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.ShowInfoUtils;
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
                    ShowInfoUtils.LogDataCode(mvpRegistView,dataCode);
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


}
