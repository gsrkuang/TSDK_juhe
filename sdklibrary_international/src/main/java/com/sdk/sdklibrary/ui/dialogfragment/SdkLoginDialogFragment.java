package com.sdk.sdklibrary.ui.dialogfragment;

import android.app.Fragment;
import android.view.View;

import com.example.sdklibrary.R;
import com.sdk.sdklibrary.ui.fragment.DataGenerator;

/**
 * Date:2022-10-20
 * Time:17:08
 * author:colin
 * 用户中心
 */
public class SdkLoginDialogFragment extends SdkBaseDialogFragment {
    private Fragment[] mFragments;
    private View home_space;

    private static volatile SdkLoginDialogFragment singleton;

    public static SdkLoginDialogFragment getInstance() {
        if (singleton == null) {
            synchronized (SdkLoginDialogFragment.class) {
                if (singleton == null) {
                    singleton = new SdkLoginDialogFragment();
                }
            }
        }
        return singleton;
    }


    private SdkLoginDialogFragment sdkLoginDialogFragment;


    @Override
    public int getLayoutId() {
        //因为横竖屏的UI需要适配,大家可以先写两套UI 通过适配文件进行配置，写法如下：

        //目前登陆界面不区分横竖屏
        if (ConfigInfo.allowPORTRAIT) {
//            return "竖屏布局";
            return R.layout.login_dialogfragment;
        } else {
//            return "横屏布局";
            return R.layout.login_dialogfragment;
        }

    }

    @Override
    public void initViews() {
        mFragments = DataGenerator.getLoginFragments("Login Tab");

    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Fragment mFragment = mFragments[0];

        getChildFragmentManager().beginTransaction()
                .replace(R.id.login_container, mFragment).commit();


    }

    @Override
    public void processClick(View v) {

    }


}
