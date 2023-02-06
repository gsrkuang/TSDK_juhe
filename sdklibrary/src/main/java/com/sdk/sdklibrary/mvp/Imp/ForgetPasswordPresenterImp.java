package com.sdk.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.model.MVPForgetPasswordBean;
import com.sdk.sdklibrary.mvp.presenter.ForgetPasswordPresenter;
import com.sdk.sdklibrary.mvp.view.MVPForgetPasswordView;
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
 * 忘记密码逻辑类 请求---响应判断---接口回调
 */

public class ForgetPasswordPresenterImp implements ForgetPasswordPresenter {

    private String phoneNumber;
    private String code;
    private String newPassword;

    private MVPForgetPasswordView mvpForgetPasswordView;

    @Override
    public void attachView(MVPForgetPasswordView mvpForgetPasswordView) {
        this.mvpForgetPasswordView =mvpForgetPasswordView;
    }

    @Override
    public void confirmReset(MVPForgetPasswordBean user, Context context) {
        phoneNumber = user.getPhoneNumber().toString().trim();
        code = user.getCode().toString().trim();
        newPassword = user.getNewPassword().toString().trim();

        if ((!TextUtils.isEmpty(phoneNumber)) && (!TextUtils.isEmpty(code)) && (!TextUtils.isEmpty(newPassword)) ) {
            confirmMethod( HttpUrlConstants.getFrogetUrl(),phoneNumber,code,newPassword);
        } else {
            mvpForgetPasswordView.showAppInfo("","手机号码或验证码或新密码输入为空");
        }

    }

    @Override
    public void forgetPasswordGetCode(String phoneNumber, Context context) {
        if ((!TextUtils.isEmpty(phoneNumber)) ) {

            getCodeMethod( HttpUrlConstants.getPhoneCodeUrl(),phoneNumber);
        }
    }

    @Override
    public void checkBindPhone(String phoneNumber, Context context) {
        if ((!TextUtils.isEmpty(phoneNumber)) ) {
            check( HttpUrlConstants.getBindPhoneCheckUrl(),phoneNumber);
        }

    }

    private void check(String url ,String phone){
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpForgetPasswordView.phoneExist(ConstData.PHONE_EXIST,msg);
                    LoggerUtils.i(LogTAG.checkphoneExist,"checkbindphone can");
                }else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpForgetPasswordView,dataCode);
                    mvpForgetPasswordView.phoneNotExist(ConstData.PHONE_NOTEXIST,msg);
                    LoggerUtils.i(LogTAG.checkphoneExist,"checkbindphone cannot");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpForgetPasswordView.phoneNotExist(ConstData.PHONECODE_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpForgetPasswordView.phoneNotExist(ConstData.PHONECODE_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    private void getCodeMethod(String url,String phone){
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpForgetPasswordView.verificationCodeSuccess(ConstData.PHONECODE_SUCCESS,msg);
                    LoggerUtils.i(LogTAG.phonecode,"phonecode Success");
                }else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpForgetPasswordView,dataCode);
                    mvpForgetPasswordView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,msg);
                    LoggerUtils.i(LogTAG.phonecode,"phonecode Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpForgetPasswordView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpForgetPasswordView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    private void confirmMethod(String url,String phone,String code,String newpassword){
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        map.put("password",newpassword);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpForgetPasswordView.resetSuccess(ConstData.RESET_SUCCESS,result);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: reset Success");
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpForgetPasswordView,dataCode);
                    mvpForgetPasswordView.resetFailed(ConstData.RESET_FAILURE,result);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: reset Failure");
                }


            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpForgetPasswordView.resetFailed(ConstData.RESET_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpForgetPasswordView.resetFailed(ConstData.RESET_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpForgetPasswordView = null;
    }

}
