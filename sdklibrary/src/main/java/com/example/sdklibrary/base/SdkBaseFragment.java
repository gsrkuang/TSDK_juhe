package com.example.sdklibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sdklibrary.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(),null);
//        TextView textView = (TextView) view.findViewById(R.id.title_from);
//        TextView content = (TextView) view.findViewById(R.id.fragment_content);
//        textView.setText(mFrom);
//        content.setText("Homefragment");
        initViews(view);
        initListener();
        initData();

        return view;
    }

    public  <E extends View> void setOnClick(E view){
        view.setOnClickListener(this);
    }

}
