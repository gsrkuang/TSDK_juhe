package com.sdk.sdklibrary.base

/**
Data:2023/2/21
Time:19:07
author:colin handsome
 * 绑定BaseView
 * 解绑BaseView
 */
open interface BasePresenter<T : BaseView?> {
    fun attachView(t: T)
    fun detachView()
}