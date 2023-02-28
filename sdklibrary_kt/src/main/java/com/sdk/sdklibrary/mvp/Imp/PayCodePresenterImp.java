package com.sdk.sdklibrary.mvp.Imp;

import android.content.Context;

import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.model.MVPPayCodeBean;
import com.sdk.sdklibrary.mvp.presenter.PayCodePresenter;
import com.sdk.sdklibrary.mvp.view.MVPPayCodeView;
import com.sdk.sdklibrary.tools.GsonUtils;
import com.sdk.sdklibrary.tools.HttpRequestUtil;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.ShowInfoUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *@author colin
 * Date:2023-02-08
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
        map.put("uId", payBean.getUId());
        map.put("oId", payBean.getOId());
        map.put("pId", payBean.getPId());
        map.put("pName", payBean.getPName());
        map.put("price", payBean.getPrice());
        map.put("callbackInfo", payBean.getCallbackInfo());

        //把payBean转换成map
        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.pay, "responseBody:" + result);
                ApiResponse<String> payResultBean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int ts = payResultBean.getTs();
                int dataCode = payResultBean.getCode();
                String msg = payResultBean.getMsg();
                String data = payResultBean.getData();

                if (HttpUrlConstants.BZ_SUCCESS == dataCode){
                    mvpPayCodeView.onPayCodeSuccess(ConstData.PAYCODE_SUCCESS, data,payType);
                    LoggerUtils.i(LogTAG.pay, "responseBody: paycode Success");
                }else {
                    mvpPayCodeView.onPayCodeFailed(ConstData.PAYCODE_FAILURE, msg);
                    LoggerUtils.i(LogTAG.pay, "responseBody: paycode Success");

                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpPayCodeView,dataCode);
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
            payCodeMethod(HttpUrlConstants.getPayUrl_alipay, payBean,"alipay");
        } else {
            mvpPayCodeView.showAppInfo("", "支付参数输入不完整");
        }
    }

    @Override
    public void wxpay(MVPPayCodeBean payBean, Context context) {
        bean = payBean;
        if (null != bean) {
            payCodeMethod(HttpUrlConstants.getPayUrl_wxpay, payBean,"wxpay");
        } else {
            mvpPayCodeView.showAppInfo("", "支付参数输入不完整");
        }
    }


}
