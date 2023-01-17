package com.example.sdklibrary.ui;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseActivity;
import com.example.sdklibrary.config.HttpUrlConstants;

/**
 * Date:2023-01-16
 * Time:14:24
 * author:colin
 *
 * 用户协议
 */
public class AgreementActivity extends SdkBaseActivity {

    WebView webView;
    ImageView goback;
    @Override
    public int getLayoutId() {
        return R.layout.activity_agreement;
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
