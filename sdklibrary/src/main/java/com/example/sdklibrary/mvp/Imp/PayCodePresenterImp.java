package com.example.sdklibrary.mvp.Imp;

import android.content.Context;

import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.HttpUrlConstants;
import com.example.sdklibrary.config.LogTAG;
import com.example.sdklibrary.mvp.model.ApiResponse;
import com.example.sdklibrary.mvp.model.MVPPayCodeBean;
import com.example.sdklibrary.mvp.presenter.PayCodePresenter;
import com.example.sdklibrary.mvp.view.MVPPayCodeView;
import com.example.sdklibrary.tools.GsonUtils;
import com.example.sdklibrary.tools.HttpRequestUtil;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.ShowInfoUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bolin
 * 支付逻辑类 请求---响应判断---
 * 通过MVPLoginView将结果回调出去给View
 */

public class PayCodePresenterImp implements PayCodePresenter {

    private MVPPayCodeView mvpPayCodeView;
    private MVPPayCodeBean bean;

    @Override
    public void attachView(MVPPayCodeView mvpPayCodeView) {
        this.mvpPayCodeView = mvpPayCodeView;
    }

    private void payCodeMethod(String url, MVPPayCodeBean payBean,String payType) {

        Map<String, String> map = new HashMap<>();
        map.put("uId", payBean.getuId());
        map.put("oId", payBean.getoId());
        map.put("pId", payBean.getpId());
        map.put("pName", payBean.getpName());
        map.put("price", payBean.getPrice());
        map.put("callbackInfo", payBean.getCallbackInfo());

        //把payBean转换成map
        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.login, "responseBody:" + result);
                ApiResponse<String> payResultBean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int ts = payResultBean.getTs();
                int dataCode = payResultBean.getCode();
                String msg = payResultBean.getMsg();
                String data = payResultBean.getData();

                if (HttpUrlConstants.BZ_SUCCESS == dataCode){
                    mvpPayCodeView.onPayCodeSuccess(ConstData.PAYCODE_SUCCESS, data,payType);
                    LoggerUtils.i(LogTAG.login, "responseBody: paycode Success");
                }else {

                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpPayCodeView,dataCode);
                    mvpPayCodeView.onPayCodeFailed(ConstData.PAYCODE_FAILURE, msg);
                    LoggerUtils.i(LogTAG.login, "responseBody: paycode Success");
                }

            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpPayCodeView.onPayCodeFailed(HttpUrlConstants.SERVER_ERROR, HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpPayCodeView.onPayCodeFailed(HttpUrlConstants.NET_NO_LINKING, HttpUrlConstants.NET_NO_LINKING);
            }
        });
    }



    @Override
    public void detachView() {
        this.mvpPayCodeView = null;
    }


    @Override
    public void pay(MVPPayCodeBean payBean, Context context) {
        bean = payBean;
        if (null != bean) {
//            payCodeMethod(HttpUrlConstants.getPayUrl(), payBean);
        } else {
            mvpPayCodeView.showAppInfo("", "支付参数输入不完整");
        }

    }

    @Override
    public void alipay(MVPPayCodeBean payBean, Context context) {
        bean = payBean;
        if (null != bean) {
            payCodeMethod(HttpUrlConstants.getPayUrl_alipay(), payBean,"alipay");
        } else {
            mvpPayCodeView.showAppInfo("", "支付参数输入不完整");
        }
    }

    @Override
    public void wxpay(MVPPayCodeBean payBean, Context context) {
        bean = payBean;
        if (null != bean) {
            payCodeMethod(HttpUrlConstants.getPayUrl_wxpay(), payBean,"wxpay");
        } else {
            mvpPayCodeView.showAppInfo("", "支付参数输入不完整");
        }
    }


}
