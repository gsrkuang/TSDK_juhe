package com.sdk.sdklibrary.mvp.model;

import java.io.Serializable;

/**
 *@author colin
 * Date:2023-02-08
 * 支付bean
 */

public class MVPPayCodeBean implements Serializable {
    private String UId;
    private String OId;
    private String PId;
    private String PName;
    private String Price;
    private String callbackInfo;

    @Override
    public String toString() {
        return "MVPPayCodeBean{" +
                "UId='" + UId + '\'' +
                ", OId='" + OId + '\'' +
                ", PId='" + PId + '\'' +
                ", PName='" + PName + '\'' +
                ", Price='" + Price + '\'' +
                ", callbackInfo='" + callbackInfo + '\'' +
                '}';
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCallbackInfo() {
        return callbackInfo;
    }

    public void setCallbackInfo(String callbackInfo) {
        this.callbackInfo = callbackInfo;
    }
}
