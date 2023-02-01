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
import com.example.sdklibrary.call.Delegate;
import com.example.sdklibrary.call.GameSdkLogic;
import com.example.sdklibrary.config.SDKStatusCode;
import com.example.sdklibrary.mvp.Imp.BindPhonePresenterImp;
import com.example.sdklibrary.mvp.Imp.UserInfoPresenterImp;
import com.example.sdklibrary.mvp.model.MVPUserInfoResultBean;
import com.example.sdklibrary.mvp.model.user.SDKUserResult;
import com.example.sdklibrary.mvp.presenter.UserInfoPresenter;
import com.example.sdklibrary.mvp.view.MVPUserInfoView;
import com.example.sdklibrary.tools.LoggerUtils;
import com.example.sdklibrary.tools.SPDataUtils;
import com.example.sdklibrary.ui.dialogfragment.SdkLoginDialogFragment;
import com.example.sdklibrary.ui.dialogfragment.SdkUserCenterDialogFragment;
import com.example.sdklibrary.ui.fragment.usercenter.dialog.BindPhoneDialog;
import com.example.sdklibrary.ui.fragment.usercenter.dialog.ChangePasswordDialog;


/**
 * Created by bolin
 * 个人档案界面
 */

public class ProfileFragment extends SdkBaseFragment implements MVPUserInfoView {


    private Button logout;
    private LinearLayout profile_btn1,profile_btn2,profile_btn3;
    private TextView profile_nickname,profile_userid,profile_phone,profile_realName;
//    private SettingFragment settingFragment;

    private ImageView profile_icon;
    private String mFrom;

    private UserInfoPresenter userInfoPresenterImp;

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

        profile_phone = (TextView) view.findViewById(R.id.tx_phoneNumber);
        profile_realName = (TextView) view.findViewById(R.id.tx_realName);

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


        userInfoPresenterImp = new UserInfoPresenterImp();
        userInfoPresenterImp.attachView(this);
        userInfoPresenterImp.getUserInfo(getActivity());

//        String nickname = SPDataUtils.getInstance().getNickName();
//        String userid = SPDataUtils.getInstance().getUserId();
//        String userPhone = SPDataUtils.getInstance().getUserPhone();
//        boolean realName = SPDataUtils.getInstance().getUserRealName();




    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.profile_logout) {
            logoutMethod();
//            Toast.makeText(getContext(),"注销",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.usercenter_phone) {
//            Toast.makeText(getActivity(),"绑定手机号码",Toast.LENGTH_SHORT).show();
            BindPhoneDialog bindPhoneDialog = new BindPhoneDialog(getActivity());
            bindPhoneDialog.show();
        } else if (id == R.id.usercenter_certification) {

            Toast.makeText(getActivity(),"实名认证",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.usercenter_changepassword) {
            ChangePasswordDialog dialog = new ChangePasswordDialog(getActivity());
            dialog.setCancelOnClickListener(new ChangePasswordDialog.onCancelOnClickListener() {
                @Override
                public void onCancelClick() {
                    dialog.cancel();
                }
            });
            dialog.setConfirmSuccessListener(new ChangePasswordDialog.onConfirmSuccessListener() {
                @Override
                public void success() {
                    //登出游戏
                    SPDataUtils.getInstance().clearLogin();
                    dialog.dismiss();
                    SdkUserCenterDialogFragment.getInstance().dismiss();
                    GameSdkLogic.getInstance().sdkFloatViewHide();

                    SdkLoginDialogFragment dialog = SdkLoginDialogFragment.getInstance();
                    dialog.show(getActivity().getFragmentManager(),"SdkLoginDialogFragment");

                    Delegate.loginlistener.callback(SDKStatusCode.LOGOUT_SUCCESS, new SDKUserResult());
                }
            });
            dialog.show();
//            getFragmentManager().beginTransaction().replace(R.id.home_container,settingFragment)
//                    .addToBackStack(null)
//                    .commit();
//            Toast.makeText(getActivity(),"修改密码",Toast.LENGTH_SHORT).show();

        } else {

        }
    }

    //登出:
    private void logoutMethod() {
        GameSdkLogic.getInstance().showLogoutDialog(getActivity());
    }

    private String hintPhoneNumber(String number){
        if (number.length() == 11){
            String a = number.substring(0,3);
            String b = number.substring(3,7);
            String c = number.substring(7,11);

            String hintStr = a + "****" +c;
            return hintStr;
        }else {
            return number;
        }

    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(data);
    }

    @Override
    public void getUserInfo_Success(String msg, MVPUserInfoResultBean user) {
        //更新用户数据
        String uid = user.getUid();
        String username = user.getUsername();
        String phone = user.getPhone();
        boolean realName = user.getRealName();

        profile_nickname.setText(username);
        profile_userid.setText(uid);


        if ("".equals(phone)){
            profile_phone.setText("未绑定");
        }else {
            //隐藏中间四位数
            profile_phone.setText(hintPhoneNumber(phone));
        }

        if (false == realName){
            profile_realName.setText("未实名");
        }else {
            profile_realName.setText("已实名");
        }

    }

    @Override
    public void getUserInfo_Failed(String msg, String data) {
        LoggerUtils.i("更新用户数据失败");
    }
}
