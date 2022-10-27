package com.example.sdklibrary.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseFragment;


/**
 * Created by zhouwei on 17/4/23.
 */

public class ProfileFragment extends SdkBaseFragment {


    private Button logout;
    private LinearLayout profile_btn1,profile_btn2,profile_btn3,profile_btn4;

    private SettingFragment settingFragment;

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
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.profile_logout) {
            Toast.makeText(getContext(),"注销",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn1) {

            Toast.makeText(getContext(),"打开App",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn2) {

            Toast.makeText(getContext(),"钱包",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn3) {

            Toast.makeText(getContext(),"打开钱包",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.profile_btn4) {

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_container,settingFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
//            Toast.makeText(getContext(),"打开设置",Toast.LENGTH_SHORT).show();
        } else {

        }
    }


}
