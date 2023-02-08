package com.sdk.sdklibrary.mvp.model;

import java.io.Serializable;

/**
 *@author colin
 * Date:2023-02-08
 * 支付bean
 */

public class MVPPayCodeBean implements Serializable {
    private String uId;
    private String oId;
    private String pId;
    private String pName;
    private String price;
    private String callbackInfo;

    @Override
    public String toString() {
        return "MVPPayBean{" +
                "uId='" + uId + '\'' +
                ", oId='" + oId + '\'' +
                ", pId='" + pId + '\'' +
                ", pName='" + pName + '\'' +
                ", price='" + price + '\'' +
                ", callbackInfo='" + callbackInfo + '\'' +
                '}';
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCallbackInfo() {
        return callbackInfo;
    }

    public void setCallbackInfo(String callbackInfo) {
        this.callbackInfo = callbackInfo;
    }
}
