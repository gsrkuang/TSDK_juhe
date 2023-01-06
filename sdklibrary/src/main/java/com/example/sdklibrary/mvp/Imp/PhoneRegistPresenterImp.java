package com.example.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
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
    public void regist(MVPPhoneRegisterBean user, Context context) {
        phoneNumber = user.getPhoneNumber().toString().trim();
        code = user.getCode().toString().trim();

        if ((!TextUtils.isEmpty(phoneNumber)) && (!TextUtils.isEmpty(code)) ) {
            registMethod( HttpUrlConstants.getRegisterUrl(),phoneNumber,code);
        } else {
            mvpPhoneRegistView.showAppInfo("","帐号或密码输入为空");
        }
    }

    private void registMethod(String url,String userName,String code){
        Map<String,String> map = new HashMap<>();
        map.put("username",userName);
        map.put("code",code);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.login,"responseBody:"+result);

                MVPRegistResultBean bean = GsonUtils.GsonToBean(result, MVPRegistResultBean.class);

                int dataCode =  bean.getErrorCode();
                String msg = bean.getErrorMsg();

                if (dataCode == 0){
                    mvpPhoneRegistView.registSuccess(ConstData.REGIST_SUCCESS,result);
                    LoggerUtils.i(LogTAG.register,"regist Success");

                }else {
                    mvpPhoneRegistView.registFailed(ConstData.REGIST_FAILURE,msg);
                    LoggerUtils.i(LogTAG.register,"regist Failure");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpPhoneRegistView.registFailed(ConstData.REGIST_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpPhoneRegistView.registFailed(ConstData.REGIST_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }

    @Override
    public void detachView() {
        this.mvpPhoneRegistView = null;
    }


}
