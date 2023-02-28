package com.sdk.sdklibrary.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.GameSdkApplication;
import com.sdk.sdklibrary.base.SdkBaseActivity;
import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.config.HttpUrlConstants;
import com.sdk.sdklibrary.config.SDKStatusCode;
import com.sdk.sdklibrary.mvp.model.MVPPayCodeBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
/**
 *@author colin
 * Date:2023-02-08
 * 支付类
 */
public class WebPayActivity extends SdkBaseActivity {

    WebView mWebView;
    String orderId ;

    private MVPPayCodeBean payBean;
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
        orderId = getIntent().getStringExtra("orderId");

        webViewSetting();
        Map extraHeaders = new HashMap();
        extraHeaders.put("referer", "https://wy.373yx.com");
        mWebView.addJavascriptInterface(new JavascriptInterface(),"Android");
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
//        try {//拼接post提交参数
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
                "\"name\": \"元宝\",\n" +
                "\"money\": 1000,\n" +
                "\"quantity\": 2,\n" +
                "\"desc\": \"游戏通用货币\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"银两\",\n" +
                "\"money\": 2000,\n" +
                "\"quantity\": 1,\n" +
                "\"desc\": \"建筑消耗物品\"\n" +
                "}\n" +
                "],\n" +
                "\"applicationContext\": {\n" +
                "\"brandName\": \"起源游戏\",\n" +
                "\"returnUrl\": \"https://xiyecode.free.svipss.top/index.html\",\n" +
                "\"cancelUrl\": \"https://xiyecode.free.svipss.top/50x.html\"\n" +
                "},\n" +
                "\"useAuthorize\": false,\n" +
                "\"remark\": \"这是订单备注，仅商家可见\"\n" +
                "}";
//        mWebView.postUrl("https://xiyecode.free.svipss.top/api/paypal/pay", EncodingUtils.getBytes(postData, "UTF-8"));

//        postURL("https://xiyecode.free.svipss.top/api/paypal/pay", postData);
//        postURL("https://xiyecode.free.svipss.top/api/stripe/pay", postData);

        //这是stripe的H5支付，暂时不用
//        postURL("https://xiyecode.free.svipss.top/api/pay/10000", postData);

    }
*/

    private void paycallback(){
        //发货

    }

    private void webViewSetting() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);                       //可执行js
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);   //设置页面默认缩放密度
        webSettings.setDefaultTextEncodingName("UTF-8");              //设置默认的文本编码名称，以便在解码html页面时使用
        webSettings.setAllowContentAccess(true);                      //启动或禁用WebView内的内容URL访问
        webSettings.setAppCacheEnabled(false);                        //设置是否应该启用应用程序缓存api
        webSettings.setBuiltInZoomControls(false);                    //设置WebView是否应该使用其内置的缩放机制
        webSettings.setUseWideViewPort(true);                         //设置WebView是否应该支持viewport
        webSettings.setLoadWithOverviewMode(true);                    //不管WebView是否在概述模式中载入页面，将内容放大适合屏幕宽度
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);          //重写缓存的使用方式
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);   //告知js自动打开窗口
        webSettings.setLoadsImagesAutomatically(true);                //设置WebView是否应该载入图像资源
        webSettings.setAllowFileAccess(true);                         //启用或禁用WebView内的文件访问
        webSettings.setDomStorageEnabled(true);                       //设置是否启用了DOM存储API,默认为false

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //True（拦截WebView加载Url），False（允许WebView加载Url）
                //通过浏览器调起微信支付（可使用）
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

                        String appId = GameSdkApplication.getInstance().getAppkey();
                        String ticket = GameSdkApplication.getInstance().getTicket(); ;
                        String tagurl = HttpUrlConstants.getPayUrl_wxpay_query_url + appId;
                        String redirect_url = HttpUrlConstants.getPayUrl_wxpay_redirect_url+
                                "?orderId=" + orderId +
                                "&appId=" +appId +
                                "&ticket=" +ticket +
                                "&url=" +URLEncoder.encode(tagurl,"utf-8");
                        view.loadUrl(redirect_url);

                        return true;
                    }

                } catch (Exception e) {

                }

                //H5调起微信app支付方法一（待验证）
//                if (url.contains("wx.tenpay")) {
//                    Map<String, String> extraHeaders = new HashMap<String, String>();
//                    extraHeaders.put("Referer", "http://zxpay.fss518.cn");
//                    view.loadUrl(url, extraHeaders);
//                    return true;
//                }

                //H5调起微信app支付方法二（可使用）
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
                                .setMessage("未检测到支付宝客户端，请安装后重试。")
                                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                        startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                    }
                                }).setNegativeButton("取消", null).show();
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
                        //使用接入的渠道支付

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

    //2.创建接收js调用的类

    class JavascriptInterface{
        //sdk>=17要添加这个注解
        @android.webkit.JavascriptInterface
        public void showMsg(String text){
            /*代码块,里面写被调用执行的代码*/
            Toast.makeText(WebPayActivity.this, text, Toast.LENGTH_SHORT).show();
            if (text.equals("success")){
                Delegate.paylistener.callback(SDKStatusCode.PAY_SUCCESS, "pay success");
            }else if (text.equals("fail")){
                Delegate.paylistener.callback(SDKStatusCode.PAY_FAILURE, "pay cancel");
            }

            finish();
        }
    }
}