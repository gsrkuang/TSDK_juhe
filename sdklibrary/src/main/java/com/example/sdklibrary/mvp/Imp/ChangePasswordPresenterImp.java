package com.example.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.mvp.model.ApiResponse;
import com.example.sdklibrary.mvp.model.MVPChangePasswordBean;
import com.example.sdklibrary.mvp.presenter.ChangePasswordPresenter;
import com.example.sdklibrary.mvp.view.MVPChangePasswordView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.example.sdklibrary.tools.ShowInfoUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Date:2023-01-18
 * Time:16:44
 * author:colin
 *
 * 修改密码
 */
public class ChangePasswordPresenterImp implements ChangePasswordPresenter {

    private String oldPassword;
    private String newPassword;
    private String newConfirmPassword;

    MVPChangePasswordView mvpChangePasswordView;

    @Override
    public void change(MVPChangePasswordBean bean, Context context) {
        oldPassword = bean.getOldPassword().toString().trim();
        newPassword = bean.getNewPassword().toString().trim();
        newConfirmPassword = bean.getNewConfirmPassword().toString().trim();

        if ((!TextUtils.isEmpty(oldPassword)) && (!TextUtils.isEmpty(newPassword)) && (!TextUtils.isEmpty(newConfirmPassword)) ) {
            confirmMethod( HttpUrlConstants.getChangePasswordUrl(),oldPassword,newPassword,newConfirmPassword);
        } else {
            mvpChangePasswordView.showAppInfo("","输入的值为空，请检查重新输入");
        }
    }

    private void confirmMethod(String url,String oldpass,String newpassword,String confirmpassword) {
        Map<String,String> map = new HashMap<>();
        map.put("uid", SPDataUtils.getInstance().getUserId());
        map.put("password",oldpass);
        map.put("newPassword",newpassword);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){

                    //保存用户新密码
                    SaveUserData(SPDataUtils.getInstance().getNickName(), newpassword, SPDataUtils.getInstance().getUserAccount(), SPDataUtils.getInstance().getUserId());

                    mvpChangePasswordView.success(ConstData.PASS_CHANGE_SUCCESS,result);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: change Success");
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpChangePasswordView,dataCode);
                    mvpChangePasswordView.fail(ConstData.PASS_CHANGE_FAILURE,result);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: change Failure");
                }


            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpChangePasswordView.fail(ConstData.PASS_CHANGE_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpChangePasswordView.fail(ConstData.PASS_CHANGE_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });

    }

    @Override
    public void attachView(MVPChangePasswordView mvpChangePasswordView) {
        this.mvpChangePasswordView = mvpChangePasswordView;
    }

    @Override
    public void detachView() {
        this.mvpChangePasswordView = null;
    }

    //使用SharedPreference来存储登陆状态
    public void SaveUserData(String username, String password, String nickname, String uid) {
        LoggerUtils.i(LogTAG.login, "SaveUserData to SharedPreference");
        SPDataUtils.getInstance().saveLoginData(username, password, nickname, uid);
    }
}
