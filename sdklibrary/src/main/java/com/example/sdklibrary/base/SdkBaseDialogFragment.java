package com.example.sdklibrary.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;


import com.example.sdklibrary.R;

/**
 * Date:2022-11-07
 * Time:15:49
 * author:colin
 *
 * 用于弹窗，Sdk弹出登陆 注册 用户中心等界面的弹窗
 */
public abstract class SdkBaseDialogFragment extends DialogFragment implements View.OnClickListener {

    private SparseArray<View> mViews;
    //  获取布局id(setContentView)
    public abstract int getLayoutId();
    //  初始化view
    public abstract void initViews();
    //  初始化点击事件
    public abstract void initListener();
    //  初始化数据
    public abstract void initData();

    //  处理具体的点击事件
    public abstract void processClick(View v);

    public Activity act;

    private View fragmentView;
    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setLayout(width, dm.heightPixels);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SdkBaseDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置布局相关
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //不显示弹框title

        //设置默认布局位置
        Window window = this.getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0); //去掉dialog默认的padding
//        window.setBackgroundDrawable(new ColorDrawable());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        mViews = new SparseArray<>();
        //layout
        fragmentView = inflater.inflate(getLayoutId(), container, false);

        initViews();
        initData();
        initListener();
        return fragmentView;

    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    public  <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }


    public void showToast(String msg) {
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
    }

    public <E extends View> E $(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) fragmentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }
}
