package com.sdk.sdklibrary.config;

import android.app.Activity;

/**
 * Created by cbl
 * 全局配置文件:
 * 切换横竖屏
 * 是否支持在窗口以外关闭界面
 * ......
 */

public class ConfigInfo {

    //允许竖屏,默认是横屏
    public static boolean allowPORTRAIT = true;

    //允许在界面以外的位置关闭窗口,默认是不允许
    public static boolean touchOUTSIDE = false;

    public static boolean setScreenIsPORTRAIT(Activity context){
        /** 1:竖屏  2:横屏 判断屏幕以旋转的方向 */
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == 1){
            return allowPORTRAIT = true;
        }else {
            return allowPORTRAIT = false;
        }
    }

}
