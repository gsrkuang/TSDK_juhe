package com.sdk.sdklibrary.mvp.Imp;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.sdk.sdklibrary.config.ConstData;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.LogTAG;
import com.sdk.sdklibrary.mvp.model.AntiAddictionBean;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.presenter.AntiAddictionPresenter;
import com.sdk.sdklibrary.mvp.view.MVPAntiAddictionView;
import com.sdk.sdklibrary.tools.GsonUtils;
import com.sdk.sdklibrary.tools.HttpRequestUtil;
import com.sdk.sdklibrary.tools.LoggerUtils;
import com.sdk.sdklibrary.tools.ShowInfoUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Data:2023/2/17
 * Time:17:15
 * author:colin handsome
 */
public class AntiAddictionPresenterImp implements AntiAddictionPresenter {

    private String name; //姓名
    private String idnumber; //身份证号码

    private MVPAntiAddictionView mvpAntiAddictionView;

    @Override
    public void attachView(MVPAntiAddictionView mvpAntiAddictionView) {
        this.mvpAntiAddictionView = mvpAntiAddictionView;
    }

    @Override
    public void detachView() {
        this.mvpAntiAddictionView = null;
    }

    @Override
    public void confirm(AntiAddictionBean bean, Context context) {
        name = bean.getName().toString().trim();
        idnumber = bean.getIdnumber().toString().trim();

        if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(idnumber))) {
            confirmMethod( HttpUrlConstants.getAntiAddictionView(),name,idnumber);
        } else {
            mvpAntiAddictionView.showAppInfo("","身份证或姓名为空");
        }
    }

    private void confirmMethod(String url, String name, String idnumber) {
        Map<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("idnumber",idnumber);

        HttpRequestUtil.okPostFormRequest(url, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.antiaddiction,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    mvpAntiAddictionView.bindId_success(ConstData.BINDID_SUCCESS,result);
                    LoggerUtils.i(LogTAG.antiaddiction,"responseBody: bind Success");
                } else {
                    //根据不同dataCode做吐司提示
                    ShowInfoUtils.LogDataCode(mvpAntiAddictionView,dataCode);
                    mvpAntiAddictionView.bindId_fail(ConstData.BINDID_FAILURE,result);
                    LoggerUtils.i(LogTAG.antiaddiction,"responseBody: bind Failure");
                }


            }

            @Override
            public void requestFailure(String request, IOException e) {
                mvpAntiAddictionView.bindId_fail(ConstData.BINDID_FAILURE,HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                mvpAntiAddictionView.bindId_fail(ConstData.BINDID_FAILURE,HttpUrlConstants.NET_NO_LINKING);
            }
        });

    }

    public static boolean checkAdult(Date date) {

        Calendar current = Calendar.getInstance();
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTime(date);
        Integer year = current.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        if (year > 18) {
            return true;
        } else if (year < 18) {
            return false;
        }
        // 如果年相等，就比较月份
        Integer month = current.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
        if (month > 0) {
            return true;
        } else if (month < 0) {
            return false;
        }
        // 如果月也相等，就比较天
        Integer day = current.get(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH);
        return  day >= 0;
    }
}
