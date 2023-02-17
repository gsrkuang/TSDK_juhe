package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;

/**
 * Date:2023-02-10
 * Time:18:26
 * author:colin
 *
 * 防沉迷view
 */
public interface MVPAntiAddictionView extends BaseView {
    void bindId_success(String msg, String data);
    void bindId_fail(String msg, String data);
}
