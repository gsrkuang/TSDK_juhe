package com.sdk.sdklibrary.ui.fragment.usercenter;

import android.os.Bundle;
import android.view.View;

import com.sdk.sdklibrary.R;
import com.sdk.sdklibrary.base.SdkBaseFragment;


/**
 * Created by bolin
 */

public class ServiceFragment extends SdkBaseFragment {
    private String mFrom;

    public static ServiceFragment newInstance(String from){
        ServiceFragment fragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.usercenter_servicefragment_layout;
    }

    @Override
    public void initViews(View view) {
//        TextView textView = (TextView) view.findViewById(R.id.title_from);
//        TextView content = (TextView) view.findViewById(R.id.fragment_content);
//        textView.setText(mFrom);
//        content.setText("Homefragment");
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
