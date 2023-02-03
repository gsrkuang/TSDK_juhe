package com.example.sdklibrary.mvp.Imp;

import android.app.Activity;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.mvp.model.ApiResponse;
import com.example.sdklibrary.mvp.presenter.SdkInitPresenter;
import com.example.sdklibrary.mvp.view.MVPSdkInitView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Date:2023-02-02
 * Time:10:48
 * author:colin
 *
 * 登出使用
 */
public class SdkInitPresenterImp implements SdkInitPresenter {

    private MVPSdkInitView mvpSdkInitView;

    @Override
    public void attachView(MVPSdkInitView mvpSdkInitView) {
        this.mvpSdkInitView = mvpSdkInitView;
    }

    @Override
    public void detachView() {
        this.mvpSdkInitView = null;
    }


    @Override
    public void init(String appId ,Activity activity,InitListener initListener) {

        Map<String,String> map = new HashMap<>();
        map.put("appId",appId);

        HttpRequestUtil.okPostFormRequest(HttpUrlConstants.getInitUrl(), map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.init,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    initListener.success(msg);
                    LoggerUtils.i(LogTAG.init,"sdkinit success");
                }else {
                    initListener.fail(msg);
                    LoggerUtils.i(LogTAG.init,"sdkinit fail");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                initListener.fail(request);
                LoggerUtils.i(LogTAG.init,"sdkinit fail" + HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                initListener.fail(msg);
                LoggerUtils.i(LogTAG.init,"sdkinit fail" + HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }



}
