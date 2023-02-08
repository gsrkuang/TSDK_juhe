package com.sdk.sdklibrary.base;


/**
 * <p>Description:
 * @author colin
 * Date:2023-02-08
 * 绑定BaseView
 * 解绑BaseView
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T t);
    void detachView();
}
