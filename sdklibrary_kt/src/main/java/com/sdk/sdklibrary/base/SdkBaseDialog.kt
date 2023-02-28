package com.sdk.sdklibrary.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import com.sdk.sdklibrary.R


/**
 * Date:2022-11-07
 * Time:15:49
 * author:colin
 *
 * 用于弹窗，Sdk弹出登陆 注册 用户中心等界面的弹窗
 */
abstract class SdkBaseDialog(var act: Activity?) : Dialog(act , R.style.SdkBaseDialog),
    View.OnClickListener {
    private var mViews: SparseArray<View?>? = null

    //  获取布局id(setContentView)
    abstract val layoutId: Int

    //  初始化view
    abstract fun initViews()

    //  初始化点击事件
    abstract fun initListener()

    //  初始化数据
    abstract fun initData()

    //  处理具体的点击事件
    abstract fun processClick(v: View?)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViews = SparseArray()
        setContentView(layoutId)

        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)
        initViews()
        initData()
        initListener()
    }

    override fun onClick(v: View) {
        processClick(v)
    }

    fun <E : View?> setOnClick(view: E) {
        view!!.setOnClickListener(this)
    }

    fun <E : View?> `$`(viewId: Int): E? {
        var view = mViews!![viewId] as E?
        if (view == null) {
            view = findViewById<View>(viewId) as E
            mViews!!.put(viewId, view)
        }
        return view
    }

    fun showToast(msg: String?) {
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show()
    }
}
