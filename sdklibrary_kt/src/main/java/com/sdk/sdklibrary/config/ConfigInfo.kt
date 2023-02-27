package com.sdk.sdklibrary.config

import android.app.Activity

/**
 * @author colin
 * Date:2023-02-08
 * 全局配置文件:
 * 切换横竖屏
 * 是否支持在窗口以外关闭界面
 * ......
 */
object ConfigInfo {
    //允许竖屏,默认是横屏
    @JvmField
    var allowPORTRAIT = true
    //允许在界面以外的位置关闭窗口,默认是不允许
    @JvmField
    var touchOUTSIDE = false
    @JvmStatic
    fun setScreenIsPORTRAIT(context: Activity): Boolean {
        /** 1:竖屏  2:横屏 判断屏幕以旋转的方向  */
        val orientation = context.resources.configuration.orientation
        return if (orientation == 1) {
            true.also { allowPORTRAIT = it }
        } else {
            false.also { allowPORTRAIT = it }
        }
    }
}