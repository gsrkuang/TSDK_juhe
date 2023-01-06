package com.example.sdklibrary.mvp.model;

/**
 * Created by bolin
 * 注册
 */

public class MVPPhoneRegisterBean {

    private String phoneNumber;
    private String code;

    public MVPPhoneRegisterBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MVPPhoneRegisterBean(String phoneNumber, String code) {
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
