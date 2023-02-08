package com.sdk.sdklibrary.base;

/**
 * @param <T>
 *@author colin
  * Date:2023-02-08
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    protected T mPresenterView;

    @Override
    public void attachView(T t) {
        this.mPresenterView = t;
    }

    @Override
    public void detachView() {
        this.mPresenterView = null;
    }
}
