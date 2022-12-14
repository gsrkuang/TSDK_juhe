package com.example.sdklibrary.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sdklibrary.BuildConfig;
import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseActivity;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.config.SDKStatusCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebPayActivity extends SdkBaseActivity {

    WebView mWebView;

    @Override
    public int getLayoutId() {
       return R.layout.activity_web_pay;
    }

    @Override
    public void initViews() {
        mWebView = findViewById(R.id.pay_webview);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();



    }

    @Override
    public void initData() {
        String webUrl = getIntent().getStringExtra("webUrl");
        webViewSetting();
        Map extraHeaders = new HashMap();
        extraHeaders.put("referer", "https://wy.373yx.com");
        mWebView.loadUrl(webUrl,extraHeaders);

    }

    @Override
    public void processClick(View v) {

    }
/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_pay);


//        mWebView.loadUrl("http://shengtai.polms.cn/index.php/Bzcs/Index/index/t/7/p/6");
//        mWebView.loadUrl("https://www.baidu.com");
//        mWebView.postUrl("https://www.baidu.com");


//        StringBuilder builder1 = new StringBuilder();
//        try {//??????post????????????
//            builder1.append("interfaceName=").append(params.get("interfaceName")).append("&")
//                    .append("interfaceVersion=").append(params.get("interfaceVersion")).append("&")
//                    .append("tranData=").append(URLEncoder.encode(params.get("tranData"), "UTF-8")).append("&")
//                    .append("merSignMsg=").append(URLEncoder.encode(params.get("merSignMsg"), "UTF-8")).append("&")
//                    .append("appId=").append(params.get("appId")).append("&")
//                    .append("transType=").append(params.get("transType"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        String postData = builder1.toString();

        int money = 1;

        String postData = "{\n" +
                "\"currency\": \"HKD\",\n" +
                "\"items\": [\n" +
                "{\n" +
                "\"name\": \"??????\",\n" +
                "\"money\": 1000,\n" +
                "\"quantity\": 2,\n" +
                "\"desc\": \"??????????????????\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"??????\",\n" +
                "\"money\": 2000,\n" +
                "\"quantity\": 1,\n" +
                "\"desc\": \"??????????????????\"\n" +
                "}\n" +
                "],\n" +
                "\"applicationContext\": {\n" +
                "\"brandName\": \"????????????\",\n" +
                "\"returnUrl\": \"https://xiyecode.free.svipss.top/index.html\",\n" +
                "\"cancelUrl\": \"https://xiyecode.free.svipss.top/50x.html\"\n" +
                "},\n" +
                "\"useAuthorize\": false,\n" +
                "\"remark\": \"????????????????????????????????????\"\n" +
                "}";
//        mWebView.postUrl("https://xiyecode.free.svipss.top/api/paypal/pay", EncodingUtils.getBytes(postData, "UTF-8"));

//        postURL("https://xiyecode.free.svipss.top/api/paypal/pay", postData);
//        postURL("https://xiyecode.free.svipss.top/api/stripe/pay", postData);

        //??????stripe???H5?????????????????????
//        postURL("https://xiyecode.free.svipss.top/api/pay/10000", postData);

    }
*/

    private void paycallback(){
        //??????

    }

    private void webViewSetting() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);                       //?????????js
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);   //??????????????????????????????
        webSettings.setDefaultTextEncodingName("UTF-8");              //???????????????????????????????????????????????????html???????????????
        webSettings.setAllowContentAccess(true);                      //???????????????WebView????????????URL??????
        webSettings.setAppCacheEnabled(false);                        //??????????????????????????????????????????api
        webSettings.setBuiltInZoomControls(false);                    //??????WebView??????????????????????????????????????????
        webSettings.setUseWideViewPort(true);                         //??????WebView??????????????????viewport
        webSettings.setLoadWithOverviewMode(true);                    //??????WebView????????????????????????????????????????????????????????????????????????
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);          //???????????????????????????
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);   //??????js??????????????????
        webSettings.setLoadsImagesAutomatically(true);                //??????WebView??????????????????????????????
        webSettings.setAllowFileAccess(true);                         //???????????????WebView??????????????????
        webSettings.setDomStorageEnabled(true);                       //?????????????????????DOM??????API,?????????false
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //True?????????WebView??????Url??????False?????????WebView??????Url???
                //????????????????????????????????????????????????
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
                    }else if (url.startsWith("https://www.373yx.com/")) {
                        //??????
//                        Delegate.paylistener.callback(SDKStatusCode.PAY_SUCCESS, "pay success");
//                        finish();

                        view.loadUrl(url);
//                        https://www.373yx.com?orderid=878787
                        return true;
                    }

                } catch (Exception e) {

                }

                //H5????????????app??????????????????????????????
//                if (url.contains("wx.tenpay")) {
//                    Map<String, String> extraHeaders = new HashMap<String, String>();
//                    extraHeaders.put("Referer", "http://zxpay.fss518.cn");
//                    view.loadUrl(url, extraHeaders);
//                    return true;
//                }

                //H5????????????app??????????????????????????????
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);

                    return true;
                }
                if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                        new AlertDialog.Builder(WebPayActivity.this)
                                .setMessage("??????????????????????????????????????????????????????")
                                .setPositiveButton("????????????", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("??????", null).show();
                    }
                    return true;
                }
//                if (!(url.startsWith("http") || url.startsWith("https"))) {
//                    return true;
//                }
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void postURL(final String url, String postData) {

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Origin", "null") //Optional
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", mWebView.getSettings().getUserAgentString())
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Language", Locale.getDefault().getLanguage())
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Cookie", CookieManager.getInstance().getCookie(url))

//                .addHeader("X-Requested-With", BuildConfig.APPLICATION_ID)
                .post(RequestBody.create(MediaType.parse("application/json"), postData))
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Timber.e(e.getMessage());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String htmlString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(htmlString);
                    Boolean useWeb = jsonObject.getBoolean("useWeb");
                    String url = jsonObject.getString("url");
                    if (useWeb){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mWebView.loadUrl(url);
                            }
                        });
                    }else {
                        //???????????????????????????

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                mWebView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mWebView.clearCache(true);
//                        mWebView.loadDataWithBaseURL(url, htmlString, "text/html", "utf-8", null);
//                    }
//                });

            }
        });
    }
}