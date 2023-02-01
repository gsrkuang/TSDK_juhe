package com.example.sdklibrary.mvp.Imp;

import android.content.Context;

import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.mvp.model.ApiResponse;
import com.example.sdklibrary.mvp.model.MVPUserInfoResultBean;
import com.example.sdklibrary.mvp.presenter.UserInfoPresenter;
import com.example.sdklibrary.mvp.view.MVPUserInfoView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.ShowInfoUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Date:2023-02-01
 * Time:10:48
 * author:colin
 *
 * 在个人中心获得用户数据，刷新用户数据
 */
public class UserInfoPresenterImp implements UserInfoPresenter {

    private MVPUserInfoView mvpUserInfoView;
    @Override
    public void attachView(MVPUserInfoView mvpUserInfoView) {
        this.mvpUserInfoView = mvpUserInfoView;
    }

    @Override
    public void detachView() {
        this.mvpUserInfoView = null;
    }

    @Override
    public void getUserInfo(Context context) {

        Map<String,String> map = new HashMap<>();


        HttpRequestUtil.okPostFormRequest(HttpUrlConstants.getUserInfoUrl(), map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<MVPUserInfoResultBean> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<MVPUserInfoResultBean>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpUserInfoView.getUserInfo_Success(ConstData.USERINFO_SUCCESS, bean.getData());
                    LoggerUtils.i(LogTAG.userinfo,"userinfo success");
                }else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpUserInfoView,dataCode);

                    mvpUserInfoView.getUserInfo_Failed(ConstData.USERINFO_FAILURE, msg);
                    LoggerUtils.i(LogTAG.userinfo,"userinfo fail");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpUserInfoView.getUserInfo_Failed(ConstData.USERINFO_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpUserInfoView.getUserInfo_Failed(ConstData.USERINFO_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }
}
