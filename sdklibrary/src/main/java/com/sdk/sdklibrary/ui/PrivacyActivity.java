package com.sdk.sdklibrary.ui;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseActivity;
import com.sdk.sdklibrary.config.HttpUrlConstants;

/**
 * Date:2023-02-21
 * Time:14:24
 * author:colin
 *
 * 隐私协议
 */
public class PrivacyActivity extends SdkBaseActivity {

    WebView webView;
    ImageView goback;
    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    public void initViews() {

        webView = $(R.id.loadwebview);
        goback = $(R.id.goback);

    }

    @Override
    public void initListener() {
        setOnClick(goback);
    }

    @Override
    public void initData() {
        webView.loadUrl(HttpUrlConstants.getPrivacytUrl());
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.goback) {
            finish();
        }
    }
}
