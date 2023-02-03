package com.share.gamesdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.callback.SdkCallbackListener;
import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.model.MVPPayCodeBean;
import com.example.sdklibrary.mvp.model.MVPPlayerBean;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;
import com.example.sdklibrary.tools.LoggerUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SdkDemoActivity extends Activity implements View.OnClickListener {

    private Button loginButton, payButton, logoutButton, subInfoButton, aboutDesButton;
    private Button testLogin;
    private TextView initText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        //游戏初始化:
        init();

    }


    //游戏前必须首先进行初始化：
    //初始化主要进行匹配参数是否符合后台规定，一些赋值操作
    private void init() {
        //这里的Object 可以扩展定义一切对象 ,你懂的
        Object object = new Object();

        GameSdkLogic.getInstance().sdkInit(this, "90175042", new SdkCallbackListener<String>() {
            @Override
            public void callback(int code, String response) {
                switch (code) {
                    case SDKStatusCode.SUCCESS:
                        initText.setText("初始化成功");
                        break;
                    case SDKStatusCode.FAILURE:
                        initText.setText(response);
                        break;
                    case SDKStatusCode.OTHER:
                        Toast.makeText(SdkDemoActivity.this, ConstData.INIT_FAILURE,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    //登录:
    private void loginMethod() {
        GameSdkLogic.getInstance().sdkLogin(this, new SdkCallbackListener<SDKUserResult>() {
            @Override
            public void callback(int code, SDKUserResult user) {
                switch (code) {
                    case SDKStatusCode.SUCCESS:
                        Toast.makeText(SdkDemoActivity.this, ConstData.LOGIN_SUCCESS + user.getUid(),Toast.LENGTH_SHORT).show();
                        //这里就可以获取登录成功以后的信息:
//                        LoggerUtils.i( "login callBack data : "+user.toString());
                        break;
                    case SDKStatusCode.FAILURE:
//                        Toast.makeText(SdkDemoActivity.this, ConstData.LOGIN_FAILURE,Toast.LENGTH_SHORT).show();
                        break;
                    case SDKStatusCode.CANCEL:
                        Toast.makeText(SdkDemoActivity.this, ConstData.LOGIN_CANCEL,Toast.LENGTH_SHORT).show();
                        break;
                    case SDKStatusCode.OTHER:
                        Toast.makeText(SdkDemoActivity.this, ConstData.LOGIN_FAILURE,Toast.LENGTH_SHORT).show();
                        break;
                    case SDKStatusCode.LOGOUT_SUCCESS:
                        //前提是已经登录成功

                        Toast.makeText(SdkDemoActivity.this, ConstData.LOGOUT_SUCCESS,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    //提交 Player 信息 上传玩家信息
    private void subGameInfoMethod() {
        MVPPlayerBean player = new MVPPlayerBean();
        player.setGameName("扣扣飞车");
        player.setName("有意思的少年");
        player.setServer("女神降临");
        player.setId("8888");
        //player.setXXX.......
        GameSdkLogic.getInstance().subGameInfoMethod(this,player);

    }

    //支付：
    private void payMethod() {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddhhmmss");

        MVPPayCodeBean payBean = new MVPPayCodeBean();
        payBean.setuId("game_0001");
        payBean.setoId(dateFormat.format(date));
        payBean.setpId("yuanbao01");
        payBean.setpName("3w金币");
        payBean.setPrice("1"); //单位分
        payBean.setCallbackInfo("返回信息");

        GameSdkLogic.getInstance().sdkPay(this, payBean, new SdkCallbackListener<String>() {
            @Override
            public void callback(int code, String response) {
                switch (code) {
                    //支付成功：
                    case SDKStatusCode.PAY_SUCCESS:
                        Toast.makeText(SdkDemoActivity.this, ConstData.PAY_SUCCESS,Toast.LENGTH_SHORT).show();
                        LoggerUtils.i( "login callBack data : "+response);
                        break;
                    //支付失败：
                    case SDKStatusCode.PAY_FAILURE:
                        Toast.makeText(SdkDemoActivity.this, ConstData.PAY_FAILURE,Toast.LENGTH_SHORT).show();
                        break;
                    //支付取消：
                    case SDKStatusCode.PAY_CANCEL:
                        Toast.makeText(SdkDemoActivity.this, ConstData.PAY_CANCEL,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void findView() {
        initText = findViewById(R.id.init_text);
        loginButton = findViewById(R.id.gameLoginBtn);
        payButton = findViewById(R.id.gamePayBtn);
        logoutButton = findViewById(R.id.gameLogoutBtn);
        subInfoButton = findViewById(R.id.subInfo);
        aboutDesButton = findViewById(R.id.aboutDes);
        testLogin = findViewById(R.id.testLogin);


        loginButton.setOnClickListener(this);
        payButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        subInfoButton.setOnClickListener(this);
        aboutDesButton.setOnClickListener(this);
        testLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gameLoginBtn:
                loginMethod();
                break;
            case R.id.gamePayBtn:
                payMethod();
                break;
            case R.id.subInfo:
                subGameInfoMethod();
                break;
            case R.id.gameLogoutBtn:
                GameSdkLogic.getInstance().sdkLogout(this);
                break;
            case R.id.aboutDes:
//                jumpActivity(X5InfoActivity.class,desUrl);
                break;
            case R.id.testLogin:
                TestFloat();
                break;
        }
    }

    protected void jumpActivity(Class<?> mClass,String data){
        Intent intent = new Intent(this, mClass);
        intent.putExtra("key",data);
        startActivity(intent);
    }

    public void TestFloat(){

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        GameSdkLogic.getInstance().onActivityResult(requestCode, resultCode, data, this);

    }
}
