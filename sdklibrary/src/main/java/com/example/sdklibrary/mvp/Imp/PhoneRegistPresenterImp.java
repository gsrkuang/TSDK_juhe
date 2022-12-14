package com.example.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.example.sdklibrary.base.GameSdkApplication;
import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.mvp.model.ApiResponse;
import com.example.sdklibrary.mvp.model.MVPLoginResultBean;
import com.example.sdklibrary.mvp.model.MVPPhoneRegisterBean;
import com.example.sdklibrary.mvp.model.MVPRegistResultBean;
import com.example.sdklibrary.mvp.model.MVPRegisterBean;
import com.example.sdklibrary.mvp.presenter.PhoneRegistPresenter;
import com.example.sdklibrary.mvp.presenter.RegistPresenter;
import com.example.sdklibrary.mvp.view.MVPPhoneRegistView;
import com.example.sdklibrary.mvp.view.MVPRegistView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bolin
 * 注册逻辑类 请求---响应判断---接口回调
 */

public class PhoneRegistPresenterImp implements PhoneRegistPresenter {

    private String phoneNumber;
    private String code;

    private MVPPhoneRegistView mvpPhoneRegistView;

    @Override
    public void attachView(MVPPhoneRegistView mvpPhoneRegistView) {
        this.mvpPhoneRegistView = mvpPhoneRegistView;
    }

    @Override
    public void phoneLoginGetCode(String phoneNumber, Context context) {
        if ((!TextUtils.isEmpty(phoneNumber)) ) {

            getCodeMethod( HttpUrlConstants.getPhoneCodeUrl(),phoneNumber);
        }
    }

    @Override
    public void phoneLogin(MVPPhoneRegisterBean user, Context context) {
        phoneNumber = user.getPhoneNumber().toString().trim();
        code = user.getCode().toString().trim();

        if ((!TextUtils.isEmpty(phoneNumber)) && (!TextUtils.isEmpty(code)) ) {
            loginMethod( HttpUrlConstants.getPhoneCodeLoginUrl(),phoneNumber,code);
        } else {
            mvpPhoneRegistView.showAppInfo("","手机号码或验证码输入为空");
        }
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
                    mvpPhoneRegistView.verificationCodeSuccess(ConstData.PHONECODE_SUCCESS,msg);
                    LoggerUtils.i(LogTAG.phonecode,"phonecode Success");
                }else {
                    mvpPhoneRegistView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,msg);
                    LoggerUtils.i(LogTAG.phonecode,"phonecode Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpPhoneRegistView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpPhoneRegistView.verificationCodeFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    private void loginMethod(String url,String phone,String code){
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.phonecode,"responseBody:"+result);

                ApiResponse<MVPLoginResultBean> mvpLoginResultBean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<MVPLoginResultBean>>(){}.getType());

                int dataCode =  mvpLoginResultBean.getCode();
                String msg = mvpLoginResultBean.getMsg();

                //保存用户名和密码，还有用户昵称
                String nickname = null;
                String md5password = null;
                String uid = "";
                String ticket = "";
                if (mvpLoginResultBean.getData() != null) {
                    nickname = mvpLoginResultBean.getData().getUsername();
                    md5password = mvpLoginResultBean.getData().getPassword();
                    uid = mvpLoginResultBean.getData().getUid();
                    ticket = mvpLoginResultBean.getData().getTicket();
                }

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpPhoneRegistView.loginSuccess(ConstData.LOGIN_SUCCESS,result);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: phonelogin Success");
                    GameSdkApplication.getInstance().setTicket(ticket);
                    SaveUserData(phone,md5password,nickname,uid);
                } else {
                    //根据不同dataCode做吐司提示
                    LogDataCode(dataCode);
                    mvpPhoneRegistView.loginFailed(ConstData.LOGIN_FAILURE,msg);
                    LoggerUtils.i(LogTAG.phonecode,"responseBody: login Failure");
                }


            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpPhoneRegistView.loginFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpPhoneRegistView.loginFailed(ConstData.PHONECODE_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpPhoneRegistView = null;
    }


    //使用SharedPreference来存储登陆状态
    public void SaveUserData(String username ,String password ,String nickname ,String uid){
        LoggerUtils.i(LogTAG.phonecode,"SaveUserData to SharedPreference");
        SPDataUtils.getInstance().saveLoginData(username,password,nickname,uid);
    }


    //根据DataCode打印日志
    public void LogDataCode(int dataCode) {

        switch (dataCode) {
            case HttpUrlConstants.BZ_INVALID_PARAM:
                mvpPhoneRegistView.showAppInfo("", "无效参数");
                break;
            case HttpUrlConstants.BZ_INVALID_APP_ID:
                mvpPhoneRegistView.showAppInfo("", "无效应用ID");
                break;
            case HttpUrlConstants.BZ_INVALID_ACCOUNT:
                mvpPhoneRegistView.showAppInfo("", "账号不合法");
                break;
            case HttpUrlConstants.BZ_INVALID_TOKEN:
                mvpPhoneRegistView.showAppInfo("", "Token已失效");
                break;
            case HttpUrlConstants.BZ_ERROR:
                mvpPhoneRegistView.showAppInfo("", "未知错误");
                break;
            case HttpUrlConstants.BZ_ERROR_ACCOUNT_PASSWORD:
                mvpPhoneRegistView.showAppInfo("", "账号或密码错误");
                break;
            case HttpUrlConstants.BZ_ERROR_SIGN:
                mvpPhoneRegistView.showAppInfo("", "签名错误");
                break;
            case HttpUrlConstants.BZ_ERROR_CODE:
                mvpPhoneRegistView.showAppInfo("", "验证码错误");
                break;
            case HttpUrlConstants.BZ_FAILURE:
                mvpPhoneRegistView.showAppInfo("", "账号已存在");
                break;
            default:
                break;
        }
    }

}
