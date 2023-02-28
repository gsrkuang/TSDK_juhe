package com.sdk.sdklibrary.base

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
Data:2023/2/22
Time:15:35
author:colin handsome
 */
abstract class SdkBaseFragment : Fragment(),View.OnClickListener {


    //  获取布局id(setContentView)
    abstract fun getLayoutId(): Int

    //  初始化view
    abstract fun initViews(view: View?)

    //  初始化点击事件
    abstract fun initListener()

    //  初始化数据
    abstract fun initData()

    //  处理具体的点击事件
    abstract fun processClick(v: View?)

    override fun onClick(v: View?) {
        processClick(v)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), null)
        initViews(view)
        initListener()
        initData()
        return view
    }

    open fun <E : View?> setOnClick(view: E) {
        view!!.setOnClickListener(this)
    }

    open fun showToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }


}