package com.example.sdklibrary.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseFragment;


/**
 * Created by zhouwei on 17/4/23.
 */

public class DiscoveryFragment extends SdkBaseFragment {

    private String mFrom;
    static DiscoveryFragment newInstance(String from){
        DiscoveryFragment fragment = new DiscoveryFragment();
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
        return R.layout.discovery_fragment_layout;
    }

    @Override
    public void initViews(View view) {
//        TextView textView = (TextView) view.findViewById(R.id.title_from);
//        TextView content = (TextView) view.findViewById(R.id.fragment_content);
//        textView.setText(mFrom);
//        content.setText("DiscoveryFragment");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
