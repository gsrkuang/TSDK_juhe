package com.example.sdklibrary.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
                        Delegate.paylistener.callback(SDKStatusCode.PAY_FAILURE, "pay cancel");
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(SdkPayActivity.this, getString(R.string.pay_failed) + payResult);
                    }

                    finish();
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


    private void wechatpayMethod() {
        Toast.makeText(this, "点击微信支付", Toast.LENGTH_LONG).show();
        if (null != payBean) {
            payCodePresenterImp.wxpay(payBean, this);
        }

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
        showToast(data);
    }

    @Override
    public void onPayCodeSuccess(String msg, String data,String payType) {
        if (payType.equals("alipay")){
            //获取支付前的验证code
            authV2(data);
        }else if (payType.equals("wxpay")){
            Intent intent = new Intent(this,WebPayActivity.class);
            intent.putExtra("webUrl",data);
            intent.putExtra("orderId",payBean.getoId());
            startActivity(intent);
        }
        finish();
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


}
