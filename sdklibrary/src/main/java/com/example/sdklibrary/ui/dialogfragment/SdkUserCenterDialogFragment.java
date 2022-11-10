package com.example.sdklibrary.ui.dialogfragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sdklibrary.R;
import com.example.sdklibrary.base.SdkBaseDialog;
import com.example.sdklibrary.base.SdkBaseDialogFragment;
import com.example.sdklibrary.config.ConfigInfo;
import com.example.sdklibrary.ui.fragment.DataGenerator;

/**
 * Date:2022-10-20
 * Time:17:08
 * author:colin
 * 用户中心
 */
public class SdkUserCenterDialogFragment extends SdkBaseDialogFragment {
    private RadioGroup mRadioGroup;
    private Fragment[] mFragments;
    private RadioButton mRadioButtonHome;
    private View home_space;

    @Override
    public int getLayoutId() {
        //因为横竖屏的UI需要适配,大家可以先写两套UI 通过适配文件进行配置，写法如下：
        if (ConfigInfo.allowPORTRAIT){
//            return "竖屏布局";
            return R.layout.usercenter;
        }else {
//            return "横屏布局";
            return R.layout.usercenter_land;
        }

    }

    @Override
    public void initViews() {
        home_space  =  $(R.id.home_space);
        mRadioGroup  =  $(R.id.radio_group_button);
        mRadioButtonHome  = $(R.id.radio_button_home);
        mFragments = DataGenerator.getFragments("RadioGroup Tab");

    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            Fragment mFragment = null;
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button_home) {
                    mFragment = mFragments[0];
                } else if (checkedId == R.id.radio_button_discovery) {
                    mFragment = mFragments[1];
                } else if (checkedId == R.id.radio_button_profile) {
                    mFragment = mFragments[2];
                }
                if(mFragments!=null){
//                    act.getFragmentManager().beginTransaction().replace(R.id.home_container,mFragment).commit();

                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.home_container,mFragment).commit();

//                    FragmentManager fg = act.getSupportFragmentManager();
//                    FragmentTransaction ft = fg.beginTransaction();
//                    ft.replace(R.id.fragment_container,hm).commit();


                }
            }
        });

        // 保证第一次会回调OnCheckedChangeListener
        mRadioButtonHome.setChecked(true);

        //点击外部空间，自动隐藏Activity
        home_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Override
    public void processClick(View v) {

    }





}
