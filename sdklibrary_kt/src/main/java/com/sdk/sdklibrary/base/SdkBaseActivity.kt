package com.sdk.sdklibrary.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.sdk.sdklibrary.config.ConfigInfo
import com.sdk.sdklibrary.tools.ActivityUtils

/**
Data:2023/2/21
Time:19:55
author:colin handsome
 */
abstract class SdkBaseActivity :Activity(),View.OnClickListener  {

      private var mViews: SparseArray<View>? = null
      //  获取布局id(setContentView)
      abstract fun getLayoutId(): Int

      //  初始化view
      abstract fun initViews()

      //  初始化点击事件
      abstract fun initListener()

      //  初始化数据
      abstract fun initData()

      //  处理具体的点击事件
      abstract fun processClick(v: View?)

      override fun onClick(v: View?) {
            processClick(v)
      }

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            mViews = SparseArray<View>()

            //设置横竖屏
            requestedOrientation = if (ConfigInfo.allowPORTRAIT) {
                  //强制为竖屏
                  ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                  //强制为横屏
                  ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            if (ConfigInfo.touchOUTSIDE) {
                  //允许点击窗口外部区域关闭窗口
                  setFinishOnTouchOutside(true)
            } else {
                  //不允许点击窗口外部区域关闭窗口
                  setFinishOnTouchOutside(false)
            }

            //取消标题
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            //取消状态栏
            //取消状态栏
            window.setFlags(
                  WindowManager.LayoutParams.FLAG_FULLSCREEN,
                  WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

            window.setBackgroundDrawable(ColorDrawable())
            setContentView(getLayoutId())
            initViews()
            initListener()
            initData()

            ActivityUtils.getInstance().attach(this)

      }

      open fun <E : View?> `$`(viewId: Int): E? {
            var view: E? = mViews!![viewId] as E
            if (view == null) {
                  view = findViewById<View>(viewId) as E
                  mViews!!.put(viewId, view)
            }
            return view
      }

      override fun onBackPressed() {
            finish()
      }

      open fun <E : View?> setOnClick(view: E) {
            view!!.setOnClickListener(this)
      }

      /** 子类可以重写改变状态栏颜色  */
      protected open fun setStatusBarColor(): Int {
            return getColorPrimary()
      }

      /** 子类可以重写决定是否使用透明状态栏 */
      protected open fun translucentStatusBar():Boolean{
            return false;
      }

      /** 设置状态栏颜色  */
      protected open fun initSystemBarTint() {
            val window = window
            if (translucentStatusBar()) {
                  // 设置状态栏全透明
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.decorView.systemUiVisibility =
                              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.statusBarColor = Color.TRANSPARENT
                  } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                  }
                  return
            }
            // 沉浸式状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                  //5.0以上使用原生方法
                  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                  window.statusBarColor = setStatusBarColor()
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                  //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
      }


      //获取主题色
      open fun getColorPrimary():Int{
            var typedValue = TypedValue()
//        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            return typedValue.data
      }

      // 获取深主题色
      open fun getDarkColorPrimary(): Int {
            val typedValue = TypedValue()
            //        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            return typedValue.data
      }

      fun showToast(msg:String){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
      }

      private var dialog: ProgressDialog? = null
      fun showLoading(){
            if (dialog != null && dialog!!.isShowing){
                  return;
            }
            dialog = ProgressDialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog!!.setMessage("请求网络中...")
            dialog!!.show()

      }
      fun dismissLoading(){
            if (dialog!=null && dialog!!.isShowing){
                  dialog!!.dismiss()
            }
      }

      override fun onDestroy() {
            super.onDestroy()
            ActivityUtils.getInstance().detach(this)
      }

      protected open fun jumpActivity(mClass: Class<*>?, bundle: Bundle?) {
            val intent = Intent(this, mClass)
            intent.putExtras(bundle)
            startActivity(intent)
      }

      protected open fun jumpActivity(mClass: Class<*>?) {
            val intent = Intent(this, mClass)
            startActivity(intent)
      }


}