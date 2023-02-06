package com.sdk.sdklibrary.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.sdk.sdklibrary.R;

/**
 * Date:2022-10-25
 * Time:16:52
 * author:colin
 */
public abstract class SdkBaseFragment extends Fragment implements View.OnClickListener {

    //  获取布局id(setContentView)
    public abstract int getLayoutId();
    //  初始化view
    public abstract void initViews(View view);
    //  初始化点击事件
    public abstract void initListener();
    //  初始化数据
    public abstract void initData();

    //  处理具体的点击事件
    public abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(),null);
        initViews(view);
        initListener();
        initData();

        return view;
    }

    public  <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


}
