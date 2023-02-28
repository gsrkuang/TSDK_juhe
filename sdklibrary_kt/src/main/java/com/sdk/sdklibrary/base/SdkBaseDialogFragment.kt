package com.sdk.sdklibrary.base

import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.sdk.sdklibrary.R

/**
Data:2023/2/22
Time:14:36
author:colin handsome

 * 用于弹窗，Sdk弹出登陆 注册 用户中心等界面的弹窗
 */
abstract class SdkBaseDialogFragment : DialogFragment(),View.OnClickListener {
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

    var act: Activity? = activity

    private var fragmentView: View? = null

    override fun onStart() {
        super.onStart()
        var dm = DisplayMetrics();
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        var width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window.setLayout(width, dm.heightPixels)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.SdkBaseDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //设置布局相关
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //不显示弹框title

        //设置默认布局位置
        val window = this.dialog.window
        window.decorView.setPadding(0, 0, 0, 0) //去掉dialog默认的padding

        //window.setBackgroundDrawable(new ColorDrawable());
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mViews = SparseArray()

        //layout
        fragmentView = inflater.inflate(getLayoutId(), container, false)

        initViews()
        initData()
        initListener()
        return fragmentView

    }

    override fun onClick(v: View?) {
        processClick(v)
    }

    open fun <E : View?> setOnClick(view: E) {
        view!!.setOnClickListener(this)
    }

    open fun showToast(msg: String?) {
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show()
    }

    open fun <E : View?> `$`(viewId: Int): E? {
        var view: E? = mViews!![viewId] as E
        if (view == null) {
            view = fragmentView!!.findViewById<View>(viewId) as E
            mViews!!.put(viewId, view)
        }
        return view
    }


}