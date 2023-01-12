package com.share.gamesdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Date:2023-01-10
 * Time:15:31
 * author:colin
 */
public class WebViewActivity extends Activity {

    final private String TAG = "WebViewActivity";
    String mReffer = "www.373yx.com";
    int tag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.myweb);

        WebSettings settings = webView.getSettings();

        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadsImagesAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setSaveFormData(true);
        settings.setDomStorageEnabled(true);

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//         当网页需要保存数时据,设置下面属性
        settings.setDomStorageEnabled(true);
//         设置为使用webview推荐的窗口，主要是为了配合下一个属性
        settings.setUseWideViewPort(true);
//         设置网页自适应屏幕大小，该属性必须和上一属性配合使用
        settings.setLoadWithOverviewMode(true);
//         启用还H5的地理定位服务
        settings.setGeolocationEnabled(true);
//         设置是否允许webview使用缩放的功能
        settings.setBuiltInZoomControls(true);
//         提高网页渲染的优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//         设置是否显示水平滚动条
        webView.setHorizontalScrollBarEnabled(false);
//         设置垂直滚动条是否有叠加样式
        webView.setVerticalScrollbarOverlay(true);
//         设置滚动条的样式
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                True（拦截WebView加载Url），False（允许WebView加载Url）

                try {
                    if (url.startsWith("https://wx.tenpay.com")) {

                        Map extraHeaders = new HashMap();
                        extraHeaders.put("referer", "https://wy.373yx.com");
                        view.loadUrl(url,extraHeaders);
                        return false;

                    }else if (url.startsWith("weixin://wap/pay?")) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);

                        return true;
                    }
                } catch (Exception e) {

                }

                return true;
            }
        });


        webView.loadUrl("https://xiyecode.free.svipss.top/api/v1/pay?tradeType=MWEB");


//        String url = "weixin://wxpay/bizpayurl?pr=0rG5ddHzz";
//        String url = "http://192.168.0.105:8983/api/v1/pay?tradeType=NATIVE";
//        String url = "http://192.168.0.105:8983/api/v1/pay/wx?tradeType=MWEB";
//        String url = "http://192.168.0.105:8983/api/v1/pay?tradeType=MWEB"; //chongdignxiang
//        String url = "https://xiyecode.free.svipss.top/api/v1/pay?tradeType=MWEB"; //chongdignxiang
//        String url = "https://files.373yx.com/wxH5.html"; //chongdignxiang
//        String url = "https://www.baidu.com"; //chongdignxiang
//        String url = "http://192.168.0.105:8980/wx.html"; //chongdignxiang
//        String url = "http://www.baidu.com";
//        String url = "https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx1017102197402806295bcad6b797e80000&package=3243444676"; //chongdignxiang

/*
        String url = "https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx111119290411983eb65992d308e1f60000&package=4025887319";

        try {
            String payurl = "https://files.373yx.com/wxH5.html?url=" + URLEncoder.encode(url, "utf-8");
//            String payurl = "https://www.baidu.com";
//            webView.loadUrl(payurl);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(payurl));
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

//

/*



        WebView webView = findViewById(R.id.myweb);
        WebSettings settings = webView.getSettings();
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadsImagesAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setSaveFormData(true);
        settings.setDomStorageEnabled(true);

        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//         当网页需要保存数时据,设置下面属性
        settings.setDomStorageEnabled(true);
//         设置为使用webview推荐的窗口，主要是为了配合下一个属性
        settings.setUseWideViewPort(true);
//         设置网页自适应屏幕大小，该属性必须和上一属性配合使用
        settings.setLoadWithOverviewMode(true);
//         启用还H5的地理定位服务
        settings.setGeolocationEnabled(true);
//         设置是否允许webview使用缩放的功能
        settings.setBuiltInZoomControls(true);
//         提高网页渲染的优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//         设置是否显示水平滚动条
        webView.setHorizontalScrollBarEnabled(false);
//         设置垂直滚动条是否有叠加样式
        webView.setVerticalScrollbarOverlay(true);
//         设置滚动条的样式
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        String referer = "xxxx";
        Map extraHeaders = new HashMap();
//        extraHeaders.put("referer", "https://wy.373yx.com");
//        extraHeaders.put("host", "wx.tenpay.com");
        String testurl = "weixin://wap/pay?prepayid%3Dwx111616251822674a9331cf3e4e32550000&package=4129209960&noncestr=1673425021&sign=53a0ac03d8ddbff0e21c306eb474bd2e";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(testurl));
        startActivity(intent);

//        webView.loadUrl(testurl,extraHeaders);

*/

    }


}
