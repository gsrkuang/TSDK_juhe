package com.sdk.sdklibrary.ui.fragment.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseFragment;
import com.sdk.sdklibrary.call.Delegate;
import com.sdk.sdklibrary.mvp.Imp.RegistPresenterImp;
import com.sdk.sdklibrary.mvp.view.MVPRegistView;
import com.sdk.sdklibrary.tools.LoggerUtils;

/**
 * Created by tzw on 2018/6/4.
 * 注册
 */

public class RegisterFragment extends SdkBaseFragment implements MVPRegistView {

    private EditText username,passWord,secpassWord;
    private Button register;
    private ImageView goback;

    private RegistPresenterImp registPresenterImp;
    private String mUserName,mPassWord,mSecPassWord;
    protected boolean accountTag,passwordTag,secpasswordTag;

    private final int ACCOUNT_MAX_LENGTH = 20;
    private final int ACCOUNT_MIN_LENGTH = 4;
    private final int PASSWORD_MAX_LENGTH = 20;
    private final int PASSWORD_MIN_LENGTH = 4;

    private final String LOGIN_FORMERROR = "帐号/密码长度格式错误";
    private final String LENGTH_EMPTY = "请检查帐号/密码输入";
    private final String CONTENT_ERROR = "两次密码输入不一致";

    private String mFrom;

    public static RegisterFragment newInstance(String from){
        RegisterFragment fragment = new RegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("register",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mFrom = getArguments().getString("from");
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.regist;
    }

    @Override
    public void initViews(View view) {
        username = view.findViewById(R.id.registusername);
        passWord = view.findViewById(R.id.registerpassword);
        secpassWord = view.findViewById(R.id.secondpwd);
        register = view.findViewById(R.id.regist);
        goback = view.findViewById(R.id.goback);
    }


    @Override
    public void initListener() {
        setOnClick(register);
        setOnClick(goback);
    }

    @Override
    public void initData() {
        registPresenterImp = new RegistPresenterImp();
        registPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.regist){
            registeMethod();
        }else if (id == R.id.goback){
            goBackMainUI();
        }
    }

    private void goBackMainUI() {
        getFragmentManager().popBackStack();
    }

    private void registeMethod(){

        mUserName = username.getText().toString().trim();
        mPassWord = passWord.getText().toString().trim();
        mSecPassWord = secpassWord.getText().toString().trim();

        accountTag = (mUserName.length() > ACCOUNT_MIN_LENGTH) && (mUserName.length() < ACCOUNT_MAX_LENGTH);
        passwordTag = (mPassWord.length() > PASSWORD_MIN_LENGTH) && (mPassWord.length() < PASSWORD_MAX_LENGTH);
        secpasswordTag = (mSecPassWord.length() > PASSWORD_MIN_LENGTH) && (mSecPassWord.length() < PASSWORD_MAX_LENGTH);

        if ((TextUtils.isEmpty(mUserName)) && (TextUtils.isEmpty(mPassWord))  &&(TextUtils.isEmpty(mSecPassWord) ) ) {
            showToast(LENGTH_EMPTY);
            return;
        } else if ( ! mPassWord.equals(mPassWord) ){
            showToast(CONTENT_ERROR);
            return;
        }
        else {
            if (accountTag && passwordTag && secpasswordTag) {
                MVPRegisterBean bean = new MVPRegisterBean(mUserName, mPassWord,mSecPassWord);
                registPresenterImp.regist(bean, getActivity());
            } else {
                showToast(LOGIN_FORMERROR);
                return;
            }
        }
    }

    @Override
    public void registSuccess(String msg, String data) {
        Delegate.loginlistener.callback(SDKStatusCode.SUCCESS,"regist success");
        LoggerUtils.i("注册成功");
    }

    @Override
    public void registFailed(String msg, String data) {
        Delegate.loginlistener.callback(SDKStatusCode.FAILURE,"regist failure");
        LoggerUtils.i("注册失败");
    }


    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registPresenterImp.detachView();
    }

}
