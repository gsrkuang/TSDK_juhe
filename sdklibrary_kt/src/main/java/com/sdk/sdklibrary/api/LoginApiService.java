package com.sdk.sdklibrary.api;

import com.sdk.sdklibrary.mvp.model.ApiResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Data:2023/2/24
 * Time:16:57
 * author:colin handsome
 */
public interface LoginApiService {
        @FormUrlEncoded
        @POST("api/v1/app/init")
        Call<ApiResponse<String>> init(@Field("appId") String appId);

}
