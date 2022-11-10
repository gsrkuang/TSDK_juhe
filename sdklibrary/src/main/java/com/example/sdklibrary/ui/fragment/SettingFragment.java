package com.example.sdklibrary.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseFragment;


/**
 * Created by zhouwei on 17/4/23.
 */

public class SettingFragment extends SdkBaseFragment {


    private LinearLayout setting_btn1, setting_btn2, setting_btn3, setting_btn4, setting_btn5, setting_btn6, setting_btn7;
    private ImageView title_bar_back;
    private TextView tv_title;
    private String mFrom;

   public static SettingFragment newInstance(String from) {
        SettingFragment fragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.setting_fragment_layout;
    }

    @Override
    public void initViews(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        title_bar_back = (ImageView) view.findViewById(R.id.title_bar_back);
        setting_btn1 = (LinearLayout) view.findViewById(R.id.setting_btn1);
        setting_btn2 = (LinearLayout) view.findViewById(R.id.setting_btn2);
        setting_btn3 = (LinearLayout) view.findViewById(R.id.setting_btn3);
        setting_btn4 = (LinearLayout) view.findViewById(R.id.setting_btn4);
        setting_btn5 = (LinearLayout) view.findViewById(R.id.setting_btn5);
        setting_btn6 = (LinearLayout) view.findViewById(R.id.setting_btn6);
        setting_btn7 = (LinearLayout) view.findViewById(R.id.setting_btn7);


    }

    @Override
    public void initListener() {
        setOnClick(title_bar_back);
        setOnClick(setting_btn1);
        setOnClick(setting_btn2);
        setOnClick(setting_btn3);
        setOnClick(setting_btn4);
        setOnClick(setting_btn5);
        setOnClick(setting_btn6);
        setOnClick(setting_btn7);
    }

    @Override
    public void initData() {
        tv_title.setText("设置");
    }

    @Override
    public void processClick(View v) {
        int id = v.getId();
        if (id == R.id.title_bar_back) {

           getFragmentManager().popBackStack();

        } else if (id == R.id.setting_btn1) {
            Toast.makeText(getActivity(), "111", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting_btn2) {
            Toast.makeText(getActivity(), "222", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting_btn3) {
            Toast.makeText(getActivity(), "333", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting_btn4) {
            Toast.makeText(getActivity(), "4444", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting_btn5) {
            Toast.makeText(getActivity(), "555", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting_btn6) {
            Toast.makeText(getActivity(), "666", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting_btn7) {
            Toast.makeText(getActivity(), "777", Toast.LENGTH_SHORT).show();
        } else {

        }
    }


}
