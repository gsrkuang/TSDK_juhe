package com.example.sdklibrary.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseFragment;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.callback.SdkCallbackListener;
import com.example.sdklibrary.config.ConstData;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;


/**
 * Created by zhouwei on 17/4/23.
 */

public class ProfileFragment extends SdkBaseFragment {


    private Button logout;
    private LinearLayout profile_btn1,profile_btn2,profile_btn3,profile_btn4;
    private SettingFragment settingFragment;
    private TextView profile_nickname,profile_userid;

    private String mFrom;
    static ProfileFragment newInstance(String from){
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
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
        return R.layout.profile_fragment_layout;
    }

    @Override
    public void initViews(View view) {
//        TextView textView = (TextView) view.findViewById(R.id.title_from);
//        TextView content = (TextView) view.findViewById(R.id.fragment_content);
//        textView.setText(mFrom);
//        content.setText("ProfileFragment");
        logout = (Button) view.findViewById(R.id.profile_logout);
        profile_btn1 =(LinearLayout) view.findViewById(R.id.profile_btn1);
        profile_btn2 =(LinearLayout) view.findViewById(R.id.profile_btn2);
        profile_btn3 =(LinearLayout) view.findViewById(R.id.profile_btn3);
        profile_btn4 =(LinearLayout) view.findViewById(R.id.profile_btn4);
        profile_nickname = (TextView) view.findViewById(R.id.profile_nickname);
        profile_userid = (TextView) view.findViewById(R.id.profile_userid);

    }

    @Override
    public void initListener() {
        setOnClick(logout);
        setOnClick(profile_btn1);
        setOnClick(profile_btn2);
        setOnClick(profile_btn3);
        setOnClick(profile_btn4);
    }

    @Override
    public void initData() {
        settingFragment = SettingFragment.newInstance("setting");
        String nickname = SPDataUtils.getInstance().getNickName();
        int userid = SPDataUtils.getInstance().getUserId();
        profile_nickname.setText(nickname);
        profile_userid.setText(userid+"");
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.profile_logout) {
            logoutMethod();
//            Toast.makeText(getContext(),"注销",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn1) {

            Toast.makeText(getContext(),"打开App",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn2) {

            Toast.makeText(getContext(),"钱包",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn3) {

            Toast.makeText(getContext(),"打开钱包",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn4) {

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_container,settingFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) //打开和返回的动画
                    .addToBackStack(null)
                    .commit();
        } else {

        }
    }


    //登出:
    private void logoutMethod() {
        GameSdkLogic.getInstance().sdkLogout(getActivity(), new SdkCallbackListener<String>() {
            @Override
            public void callback(int code, String response) {
                switch (code) {
                    case SDKStatusCode.SUCCESS:
                        Toast.makeText(getActivity(), ConstData.LOGOUT_SUCCESS + response,Toast.LENGTH_SHORT).show();
                        //这里就可以获取登出成功以后的信息:
                        LoggerUtils.i( "login callBack data : "+response);
                        break;
                    case SDKStatusCode.FAILURE:
                        Toast.makeText(getActivity(), ConstData.LOGOUT_FAILURE,Toast.LENGTH_SHORT).show();
                        break;
                    case SDKStatusCode.CANCEL:
                        Toast.makeText(getActivity(), ConstData.LOGOUT_CANCEL,Toast.LENGTH_SHORT).show();
                        break;
                    case SDKStatusCode.OTHER:
                        Toast.makeText(getActivity(), ConstData.LOGOUT_FAILURE,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

}
