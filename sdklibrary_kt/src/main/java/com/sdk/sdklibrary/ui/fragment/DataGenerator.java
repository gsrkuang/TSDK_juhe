package com.sdk.sdklibrary.ui.fragment;

import android.app.Fragment;


import com.sdk.sdklibrary.ui.fragment.login.ForgetPasswordFragment;
import com.sdk.sdklibrary.ui.fragment.login.LoginFragment;
import com.sdk.sdklibrary.ui.fragment.login.PhoneRegisterFragment;
import com.sdk.sdklibrary.ui.fragment.login.RegisterFragment;
import com.sdk.sdklibrary.ui.fragment.usercenter.ProfileFragment;
import com.sdk.sdklibrary.ui.fragment.usercenter.ServiceFragment;


/**
 *@author colin
 * Date:2023-02-08
 */

public class DataGenerator {

//    public static final int []mTabRes = new int[]{R.drawable.tab_home_selector,R.drawable.tab_discovery_selector,R.drawable.tab_attention_selector,R.drawable.tab_profile_selector};
//    public static final int []mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_category_selected,R.drawable.ic_tab_strip_icon_pgc_selected,R.drawable.ic_tab_strip_icon_profile_selected};
    public static final String []mTabTitle = new String[]{"我的","客服"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[2];

        fragments[0] = ProfileFragment.newInstance(from);
        fragments[1] = ServiceFragment.newInstance(from);
//        fragments[1] = DiscoveryFragment.newInstance(from);
//        fragments[2] = ProfileFragment.newInstance(from);
        return fragments;
    }

    public static Fragment[] getLoginFragments(String from){
        Fragment fragments[] = new Fragment[3];
        fragments[0] = LoginFragment.newInstance(from);
        fragments[1] = RegisterFragment.newInstance(from);
        fragments[2] = PhoneRegisterFragment.newInstance(from);
        fragments[2] = ForgetPasswordFragment.newInstance(from);
        return fragments;
    }


    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
//    public static View getTabView(Context context,int position){
////        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
////        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
////        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
////        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
//        tabText.setText(mTabTitle[position]);
//        return view;
//    }

}
