package com.example.sdklibrary.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseActivity;
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.config.ConfigInfo;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.PayCodePresenterImp;
import com.example.sdklibrary.mvp.model.MVPPayCodeBean;
import com.example.sdklibrary.mvp.model.pay.PayResult;
import com.example.sdklibrary.mvp.view.MVPPayCodeView;
import com.example.sdklibrary.tools.LoggerUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by bolin
 * 支付Activtiy
 * 这里没有接口，所以需要自己完成
 */

public class SdkPayActivity extends SdkBaseActivity implements MVPPayCodeView {
    private RadioGroup mRadioGroup;
    private RadioButton mAlipay,mWechatpay;
    private TextView mShopName,mShopMoney;

    private LinearLayout llAlipay,llWechatpay;

    private Button mPay;
    private ImageView mBack;

    private String payTAG = "1";
    private PayCodePresenterImp payCodePresenterImp;

    private MVPPayCodeBean payBean;
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016092500592732";

    private static final int SDK_PAY_FLAG = 1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        LoggerUtils.i("支付宝支付成功");
                        Delegate.paylistener.callback(SDKStatusCode.PAY_SUCCESS, "pay success");
//                        showAlert(SdkPayActivity.this, getString(R.string.pay_success) + payResult);
                    } else if(TextUtils.equals(resultStatus, "6001")){
                        LoggerUtils.i("支付宝支付取消");
                        Delegate.paylistener.callback(SDKStatusCode.PAY_CANCEL, "pay cancel");
                    }else {
                        LoggerUtils.i("支付宝支付失败");
                        Delegate.paylistener.callback(SDKStatusCode.FAILURE, "pay cancel");
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(SdkPayActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };


    @Override
    public int getLayoutId() {
        if (ConfigInfo.allowPORTRAIT){
//            return "竖屏布局";
            return R.layout.pay;
        }else {
//            return "横屏布局";
            return R.layout.pay_land;
        }

    }

    @Override
    public void initViews() {
        mRadioGroup = $(R.id.radioGrouppay);

        mShopName = $(R.id.shopName);
        mShopMoney = $(R.id.shopMoney);
        mAlipay = $(R.id.rbalipay);
        mWechatpay = $(R.id.rbwechatpay);

        llAlipay = $(R.id.llalipay);
        llWechatpay = $(R.id.llwechatpay);

        mPay = $(R.id.pay);
        mBack = $(R.id.goback);
    }

    @Override
    public void initListener() {
        setOnClick(mRadioGroup);
        setOnClick(llAlipay);
        setOnClick(llWechatpay);
        setOnClick(mPay);
        setOnClick(mBack);
    }

    @Override
    public void initData() {
        getIntentData();

//        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                if (mAlipay.getId() == checkedId) {
//                    payTAG = "1";
//                    cancelAllChecked();
//                    mAlipay.setChecked(true);
//                } else if (mWechatpay.getId() == checkedId) {
//                    payTAG = "2";
//                    cancelAllChecked();
//                    mWechatpay.setChecked(true);
//                } else {
//
//                }
//            }
//        });

        //支付宝沙盒支付配置（正式环境需要去掉）
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);


        payCodePresenterImp = new PayCodePresenterImp();
        payCodePresenterImp.attachView(this);



    }

    public void cancelAllChecked(){
        mAlipay.setChecked(false);
        mWechatpay.setChecked(false);
    }

    public void getIntentData() {
        payBean = (MVPPayCodeBean) getIntent().getSerializableExtra("payBean");
        mShopName.setText("商品:"+payBean.getpName());
        mShopMoney.setText(changeF2Y(payBean.getPrice())+"元");
    }

    /**
     * 分转元（除以100）四舍五入，保留2位小数
     * @param amount 金额 元
     * @return
     */
    public String changeF2Y(String amount){
        return new BigDecimal(amount).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        payCodePresenterImp.detachView();
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.pay) {
            payMethod();
        } else if (id == R.id.goback) {
            finish();
        }else if (id == R.id.llalipay) {
            payTAG = "1";
            cancelAllChecked();
            mAlipay.setChecked(true);
        }
        else if (id == R.id.llwechatpay) {
            payTAG = "2";
            cancelAllChecked();
            mWechatpay.setChecked(true);
        }
        else {

        }
    }

    private void payMethod() {

        if (payTAG.equals("1")) {
            alipayMethod();
        } else if (payTAG.equals("2")) {
            wechatpayMethod();
        } else {

        }
    }


    private void coinpayMethod() {
        Toast.makeText(this, "点击金币支付", Toast.LENGTH_LONG).show();
    }

    private void wechatpayMethod() {
        Toast.makeText(this, "点击微信支付", Toast.LENGTH_LONG).show();
    }

    private void alipayMethod() {

        if (null != payBean) {
            payCodePresenterImp.alipay(payBean, this);
        }

        Toast.makeText(this, "点击支付宝支付", Toast.LENGTH_LONG).show();

//        startActivity(new Intent(this,WebPayActivity.class));
        //测试接入Stripe
//        startActivity(new Intent(this,StripPaymentActivity.class));

        //弹出WebView支持
//        WebView mWebView = new WebView(this);



    }



    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    @Override
    public void showAppInfo(String msg, String data) {

    }

    @Override
    public void onPayCodeSuccess(String msg, String data) {
        //获取支付前的验证code
        authV2(data);
    }

    @Override
    public void onPayCodeFailed(String msg, String data) {

    }

    @Override
    public void onPayCodeCancel(String msg, String data) {

    }

    /**
     * 支付宝账户授权业务示例
     */
    public void authV2(String authInfo) {

//        String authInfo_test = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2021000122606160&biz_content=%7B%22out_trade_no%22%3A%2277734932%22%2C%22product_code%22%3A%22QUICK_WAP_WAY%22%2C%22subject%22%3A%22620568350%22%2C%22total_amount%22%3A%220.1%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fxiyecode.free.svipss.top%2Fapi%2Fv1%2Fpay%2Fnotify%2Fali_pay&return_url=https%3A%2F%2Fxiyecode.free.svipss.top%2Fapi%2Fv1%2Fpay%2Freturn%2Fali_pay&sign=XmgYVYyRZMlyn5txg42FAVlIsKCFgw0Xzs9HzbzZ2F5IBumv7ANxDCH3RTjm5IbCXKHQjWR9HLnVRjiLaOOH8YZoU2lsVW3cYKCvKzVfi8zaeFAoEKANi4rB%2BaQ7WUfnNhIfHvGBN0IXNtbgMxLo7Cy%2BXSylECDJIW4%2Fi6Xbyyzrt0SUQzidCob7DMob2Xn%2BziIiIzm4dP5oSuIO8VzJCnS1d2x8w15nlwICQb%2FWvbiCe6ZeJwCPK9ZXG9zs6YqbPWg2L4FHaBOZAnwXrhTDtVbRhjXmiR%2FFUZU192dpxOzpj5aijkvbBvDa%2FC9xI0NY6C9zfl2zuPPelH4uVzd4KQ%3D%3D&sign_type=RSA2&timestamp=2022-12-30+15%3A48%3A18&version=1.0";
//        String authInfo_test = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2021000122606160&biz_content=%7B%22out_trade_no%22%3A%2215893876518688284%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%94%BB%E5%9F%8E%E4%B8%89%E5%9B%BD-%E6%88%91%E6%98%AF%E5%95%86%E5%93%81%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fxiyecode.free.svipss.top%2Fapi%2Fv1%2Fpay%2Fnotify%2Fali%2F111111&sign=Jffm5KXnJ6d0VarQ6lXptppAaBEerea4TlBEqGbQXtBzmXIVxbEommYsu8UYXsG0gnC55pLpRM8uisUu7%2FAYO2pfVXfpWUjoc9E9VdmtmhCjIKqzPGWkJjsxoGAS7cCOJ4WlFzyQjpzxt0yD2%2F8tpD8HknTPBy92UCNo3XMV0K69oOJVhwBRTdjqX6zI3JPqVr8uEnQCSTPwiEpU%2B4%2F2Vw72PejZ4bQJiVX02fL%2F0O4vo6HmsvCz%2B63UfOha4m%2BxCJSVneelrN0dz2xwNlwoKtKVo33HFIZ715FtKocm%2Fo%2FIkOIHOt5PnarOxJmsOP2oxYgKBM1IemPa%2FzuNqrCO%2Bw%3D%3D&sign_type=RSA2&timestamp=2023-01-03+18%3A21%3A48&version=1.0";
//        String authInfo_test = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2021000122606160&biz_content=%7B%22out_trade_no%22%3A%2215893876518688294%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%94%BB%E5%9F%8E%E4%B8%89%E5%9B%BD-%E6%88%91%E6%98%AF%E5%95%86%E5%93%81%22%2C%22total_amount%22%3A%221.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fxiyecode.free.svipss.top%2Fapi%2Fv1%2Fpay%2Fnotify%2Fali%2F111111&sign=b%2FtDJa9vrB5RErfxE8sn7ReQjCT6aifQcQsMhtd%2Fs7O88Fj3W4D%2FSNR67%2BWj5n5GhjF2BlFgYwKNJ%2FgSTeDVdbzDpthH%2Fb2fMwjR%2FdMyDfYYeP4YuV3Et73XUyJh6I%2BOHuemRq%2FuGHxRHiGDtARBOb8WRzF%2BuoskSudQ6xnPJ8ff4kHB2Cqj5zSBIueRxcLD8jQ5Gh73MRElNfvA6%2Bffaa7v8RFiHH0%2F8FiIXjxSKh7g30%2F9AR%2FQ%2FNs1ngQyw2bjfcICE6NdXktYl7m1n3FeuicwKG3A7DyqIm2O9E37EKgIwQW4Cvlm1gQvpMwWghE%2FbePJ1EDaP%2FhlIFDJsTQnHg%3D%3D&sign_type=RSA2&timestamp=2023-01-03+18%3A33%3A23&version=1.0";

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(SdkPayActivity.this);
                Map<String, String> result = alipay.payV2(authInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }

//    @Override
//    public void onPaySuccess(String msg, String data) {
//        Delegate.paylistener.callback(SDKStatusCode.FAILURE, "pay success");
//        LoggerUtils.i("支付成功");
//    }
//
//    @Override
//    public void onPayFailed(String msg, String data) {
//        Delegate.paylistener.callback(SDKStatusCode.FAILURE, "pay failure");
//        LoggerUtils.i("支付失败");
//    }
//
//    @Override
//    public void onPayCancel(String msg, String data) {
//        Delegate.paylistener.callback(SDKStatusCode.FAILURE, "pay cancel");
//        LoggerUtils.i("支付取消");
//    }

}
