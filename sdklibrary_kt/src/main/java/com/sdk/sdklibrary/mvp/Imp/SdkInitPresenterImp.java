package com.sdk.sdklibrary.mvp.Imp;

import android.app.Activity;

import com.sdk.sdklibrary.api.LoginApiService;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.mvp.model.ApiResponse;
import com.sdk.sdklibrary.mvp.presenter.SdkInitPresenter;
import com.sdk.sdklibrary.mvp.view.MVPSdkInitView;
import com.sdk.sdklibrary.tools.LoggerUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Date:2023-02-02
 * Time:10:48
 * author:colin
 *
 * 初始化使用
 */
public class SdkInitPresenterImp implements SdkInitPresenter {

    private MVPSdkInitView mvpSdkInitView;

    @Override
    public void attachView(MVPSdkInitView mvpSdkInitView) {
        this.mvpSdkInitView = mvpSdkInitView;
    }

    @Override
    public void detachView() {
        this.mvpSdkInitView = null;
    }


    @Override
    public void init(String appId ,Activity activity,InitListener initListener) {

        //1.创建Retrofit实例对象
        Retrofit retrofit = new Retrofit.Builder()
                //设置服务器主机地址,要求url必须以/结尾
                .baseUrl(HttpUrlConstants.SDK_BASE_URL)
                //使用Gson作为json数据的转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2.创建接口的实现类对象：让retrofit创建一个实例对象
        //Retrofit内部是通过动态代理来创建实例对象，并且监听对象方法的调用;
        //当我们调用业务方法时，Retrofit内部就获取方法的注解信息，这些信息
        //包含的请求方式，url，和请求参数等，于是它会自动的利用okhttp发送这些请求
        LoginApiService service = retrofit.create(LoginApiService.class);

        //3.得到的是请求的封装对象，此时请求还没有执行呢，这个请求中封装了url和参数，
        //以及你期望解析的类型等
        Call<ApiResponse<String>> call = service.init(appId);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                ApiResponse<String> data = response.body();

            }

            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable throwable) {

            }
        });


//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody>> call, Response<ApiResponse<ResponseBody>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<ResponseBody>> call, Throwable throwable) {
//                initListener.fail(call.toString());
//                LoggerUtils.i(LogTAG.init,"sdkinit fail" + HttpUrlConstants.SERVER_ERROR);
//            }
//        });


       /* Map<String,String> map = new HashMap<>();
        map.put("appId",appId);

        HttpRequestUtil.okPostFormRequest(HttpUrlConstants.getInitUrl, map, new HttpRequestUtil.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LoggerUtils.i(LogTAG.init,"responseBody:"+result);

                ApiResponse<String> bean = GsonUtils.gson.fromJson(result, new TypeToken<ApiResponse<String>>(){}.getType());

                int dataCode =  bean.getCode();
                String msg = bean.getMsg();

                if (dataCode == HttpUrlConstants.BZ_SUCCESS){
                    initListener.success(msg);
                    LoggerUtils.i(LogTAG.init,"sdkinit success");
                }else {
                    initListener.fail(msg);
                    LoggerUtils.i(LogTAG.init,"sdkinit fail");
                }
            }

            @Override
            public void requestFailure(String request, IOException e) {
                initListener.fail(request);
                LoggerUtils.i(LogTAG.init,"sdkinit fail" + HttpUrlConstants.SERVER_ERROR);
            }

            @Override
            public void requestNoConnect(String msg, String data) {
                initListener.fail(msg);
                LoggerUtils.i(LogTAG.init,"sdkinit fail" + HttpUrlConstants.NET_NO_LINKING);
            }
        });*/
    }



}
