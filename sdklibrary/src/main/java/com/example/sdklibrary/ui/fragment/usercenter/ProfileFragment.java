package com.example.sdklibrary.ui.fragment.usercenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseFragment;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.tools.SPDataUtils;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;


/**
 * Created by bolin
 * 个人档案界面
 */

public class ProfileFragment extends SdkBaseFragment {


    private Button logout;
    private LinearLayout profile_btn1,profile_btn2,profile_btn3;
    private TextView profile_nickname,profile_userid;
    private SettingFragment settingFragment;

    private ImageView profile_icon;
    private String mFrom;

    public static ProfileFragment newInstance(String from){
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.usercenter_profilefragment_layout;
    }

    @Override
    public void initViews(View view) {
//        TextView textView = (TextView) view.findViewById(R.id.title_from);
//        TextView content = (TextView) view.findViewById(R.id.fragment_content);
//        textView.setText(mFrom);
//        content.setText("ProfileFragment");
        logout = (Button) view.findViewById(R.id.profile_logout);
        profile_icon = (ImageView) view.findViewById(R.id.profile_icon);
        profile_btn1 =(LinearLayout) view.findViewById(R.id.usercenter_phone);
        profile_btn2 =(LinearLayout) view.findViewById(R.id.usercenter_certification);
        profile_btn3 =(LinearLayout) view.findViewById(R.id.usercenter_changepassword);
        profile_nickname = (TextView) view.findViewById(R.id.profile_nickname);
        profile_userid = (TextView) view.findViewById(R.id.profile_userid);

    }

    @Override
    public void initListener() {
        setOnClick(logout);
        setOnClick(profile_btn1);
        setOnClick(profile_btn2);
        setOnClick(profile_btn3);
    }

    @Override
    public void initData() {
        settingFragment = SettingFragment.newInstance("setting");

        String nickname = SPDataUtils.getInstance().getNickName();
        String userid = SPDataUtils.getInstance().getUserId();
        profile_nickname.setText(nickname);
        profile_userid.setText(userid);

//        profile_icon.setImageURI("");

    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.profile_logout) {
            logoutMethod();
//            Toast.makeText(getContext(),"注销",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.usercenter_phone) {

            Toast.makeText(getActivity(),"绑定手机号码",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.usercenter_certification) {

            Toast.makeText(getActivity(),"实名认证",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.usercenter_changepassword) {
            getFragmentManager().beginTransaction().replace(R.id.home_container,settingFragment)
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(getActivity(),"修改密码",Toast.LENGTH_SHORT).show();
        } else {

        }
    }


    //登出:
    private void logoutMethod() {
        GameSdkLogic.getInstance().showLogoutDialog(getActivity());
    }

}
