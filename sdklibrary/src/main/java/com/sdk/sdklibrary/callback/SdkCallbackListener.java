package com.sdk.sdklibrary.callback;

/**
 *@author colin
 * Date:2023-02-08
 * 全局回调
 */

public interface SdkCallbackListener<T> {
    void callback(int code, T response);
}
