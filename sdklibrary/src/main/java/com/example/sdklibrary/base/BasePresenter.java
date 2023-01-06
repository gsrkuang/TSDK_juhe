package com.example.sdklibrary.base;


/**
 * <p>Description:
 * @author cbl
 * 
 * 绑定BaseView
 * 解绑BaseView
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T t);
    void detachView();
}
