/*

package com.share.gamesdk.other;

import android.content.Intent;
import android.view.View;

import SdkBaseActivity;
import LoggerUtils;
import com.share.gamesdk.R;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * Created by tzw on 2018/6/8.
 * 选用
 *//*



public class X5InfoActivity extends SdkBaseActivity{
    MyX5WebView myX5WebView;
    private   String url;
    @Override
    public int getLayoutId() {
        Intent intent = getIntent();
         url = intent.getStringExtra("key");
        LoggerUtils.i("web url == "+url);
        return R.layout.x5layout;
    }

    @Override
    public void initViews() {
        myX5WebView = findViewById(R.id.myx5);

//        String url = "https://xiyecode.free.svipss.top/api/v1/pay?tradeType=MWEB"; //chongdignxiang
        String url = "http://192.168.0.105:8983/api/v1/pay?tradeType=MWEB"; //chongdignxiang
        Map extraHeaders = new HashMap();
        extraHeaders.put("Referer", "www.373yx.com");

        myX5WebView.loadUrl(url,extraHeaders);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}

*/
