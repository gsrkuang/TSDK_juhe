package com.sdk.sdklibrary.callback;

/**
 * Created by cbl
 * 全局回调
 */

public interface SdkCallbackListener<T> {
    void callback(int code, T response);
}
