package com.sdk.sdklibrary.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.sdk.sdklibrary.R;

/**
 * Date:2022-11-07
 * Time:15:49
 * author:colin
 *
 * 用于弹窗，Sdk弹出登陆 注册 用户中心等界面的弹窗(暂时不用)
 */
public abstract class SdkBaseDialog extends Dialog implements View.OnClickListener {

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

    public SdkBaseDialog(Activity act) {
        super(act, R.style.SdkBaseDialog);
        this.act =act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViews = new SparseArray<>();
        setContentView( getLayoutId() );

        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        initViews();
        initData();
        initListener();
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    public  <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }

    public <E extends View> E $(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }
    public void showToast(String msg) {
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
    }
}
