package com.sdk.sdklibrary.mvp.Imp;

import android.content.Context;

import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.model.MVPUserInfoResultBean;
import com.sdk.sdklibrary.mvp.presenter.UserInfoPresenter;
import com.sdk.sdklibrary.mvp.view.MVPUserInfoView;
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

                //保存用户名和密码，还有用户昵称
                String nickname = null;
                String uid = "";
                String ticket = "";
                String phone = "";
                boolean realname = false;
                if (bean.getData() != null) {
                    nickname = bean.getData().getUsername();
                    uid = bean.getData().getUid();
                    ticket = bean.getData().getTicket();
                    phone = bean.getData().getPhone();
                    realname = bean.getData().getRealName();
                }

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpUserInfoView.getUserInfo_Success(ConstData.USERINFO_SUCCESS, bean.getData());

                    SaveUserData(nickname,
                            SPDataUtils.getInstance().getUserPassword(),
                            nickname,uid,phone,realname);

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

    //使用SharedPreference来存储登陆状态
    public void SaveUserData(String username, String password, String nickname, String uid,String phone,boolean realname) {
        LoggerUtils.i(LogTAG.login, "SaveUserData to SharedPreference");
        SPDataUtils.getInstance().saveLoginData(username, password, nickname, uid,phone,realname);
    }
}
