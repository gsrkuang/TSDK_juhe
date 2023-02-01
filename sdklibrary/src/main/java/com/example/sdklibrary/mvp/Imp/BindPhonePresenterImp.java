package com.example.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.mvp.model.ApiResponse;
import com.example.sdklibrary.mvp.model.MVPPhoneBindBean;
import com.example.sdklibrary.mvp.presenter.BindPhonePresenter;
import com.example.sdklibrary.mvp.view.MVPBindPhoneView;
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
 * Date:2023-01-31
 * Time:17:07
 * author:colin
 * 用户绑定手机号码
 */

public class BindPhonePresenterImp implements BindPhonePresenter {

    private String phoneNumber;
    private String code;

    private MVPBindPhoneView mvpBindPhoneView;


    @Override
    public void attachView(MVPBindPhoneView mvpBindPhoneView) {
        this.mvpBindPhoneView = mvpBindPhoneView;
    }


    @Override
    public void confirm(MVPPhoneBindBean bean, Context context) {
        phoneNumber = bean.getPhoneNumber().toString().trim();
        code = bean.getCode().toString().trim();

        if ((!TextUtils.isEmpty(phoneNumber)) && (!TextUtils.isEmpty(code))) {
            confirmMethod( HttpUrlConstants.getBindPhoneUrl(),phoneNumber,code);
        } else {
            mvpBindPhoneView.showAppInfo("","手机号码或验证码或新密码输入为空");
        }

    }



    @Override
    public void bindGetCode(String phoneNumber, Context context) {
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

                if (dataCode == HttpUrlConstants.BZ_UNBIND){

                    mvpBindPhoneView.phoneCanBind(ConstData.PHONE_EXIST,msg);
                    LoggerUtils.i(LogTAG.checkphoneExist,"checkbindphone can");
                }else {
                    //根据不同dataCode做吐司提示
//                    ShowInfoUtils.LogDataCode(mvpBindPhoneView,dataCode);
                    mvpBindPhoneView.phoneCanNotBind(ConstData.PHONE_NOTEXIST,msg);
                    LoggerUtils.i(LogTAG.checkphoneExist,"checkbindphone cannot");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpBindPhoneView.phoneCanNotBind(ConstData.PHONECODE_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpBindPhoneView.phoneCanNotBind(ConstData.PHONECODE_FAILURE,HttpUrlConstants.NET_NO_LINKING);
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
                    mvpBindPhoneView.verificationCodeSuccess(ConstData.PHONECODE_SUCCESS,msg);
                    LoggerUtils.i(LogTAG.phonecode,"phonecode Success");
                }else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpBindPhoneView,dataCode);
                    mvpBindPhoneView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,msg);
                    LoggerUtils.i(LogTAG.phonecode,"phonecode Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpBindPhoneView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpBindPhoneView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    private void confirmMethod(String url,String phone,String code){
        Map<String,String> map = new HashMap<>();
        map.put("uid", SPDataUtils.getInstance().getUserId());
        map.put("phone",phone);
        map.put("code",code);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpBindPhoneView.bind_success(ConstData.BIND_SUCCESS,result);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: bind Success");
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpBindPhoneView,dataCode);
                    mvpBindPhoneView.bind_fail(ConstData.BIND_FAILURE,result);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: bind Failure");
                }


            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpBindPhoneView.bind_fail(ConstData.BIND_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpBindPhoneView.bind_fail(ConstData.BIND_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpBindPhoneView = null;
    }

}
