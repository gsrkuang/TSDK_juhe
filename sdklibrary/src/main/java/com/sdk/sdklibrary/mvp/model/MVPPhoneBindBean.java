package com.sdk.sdklibrary.mvp.model;

/**
 *@author colin
 * Date:2023-02-08
 * 手机号码绑定
 */

public class MVPPhoneBindBean {

    private String phoneNumber;
    private String code;

    public MVPPhoneBindBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MVPPhoneBindBean(String phoneNumber, String code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MVPPhoneRegisterBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
