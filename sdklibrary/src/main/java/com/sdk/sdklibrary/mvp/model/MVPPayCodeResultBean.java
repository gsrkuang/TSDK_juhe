package com.sdk.sdklibrary.mvp.model;

import java.io.Serializable;

/**
 * Created by bolin
 * 支付bean
 */

public class MVPPayCodeResultBean {

    private String data;

    @Override
    public String toString() {
        return "MVPPayCodeResultBean{" +
                "data='" + data + '\'' +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
