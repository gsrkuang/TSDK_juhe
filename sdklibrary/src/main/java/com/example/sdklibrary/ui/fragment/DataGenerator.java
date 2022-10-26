package com.example.sdklibrary.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.sdklibrary.R;


/**
 * Created by zhouwei on 17/4/23.
 */

public class DataGenerator {

    public static final int []mTabRes = new int[]{R.drawable.tab_home_selector,R.drawable.tab_discovery_selector,R.drawable.tab_attention_selector,R.drawable.tab_profile_selector};
    public static final int []mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_category_selected,R.drawable.ic_tab_strip_icon_pgc_selected,R.drawable.ic_tab_strip_icon_profile_selected};
    public static final String []mTabTitle = new String[]{"首页","发现","我的"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[3];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = DiscoveryFragment.newInstance(from);
        fragments[2] = ProfileFragment.newInstance(from);
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context,int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
