package com.sdk.sdklibrary.mvp.view;

import com.sdk.sdklibrary.base.BaseView;

/**
 * Date:2023-01-18
 * Time:16:57
 * author:colin
 */
public interface MVPChangePasswordView extends BaseView {
    void success(String msg, String data);
    void fail(String msg, String data);
}
