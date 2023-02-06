package com.sdk.sdklibrary.mvp.model;

/**
 * Created by bolin
 * 注册
 */

public class MVPForgetPasswordBean {

    private String phoneNumber;
    private String code;
    private String newPassword;

    public MVPForgetPasswordBean(String phoneNumber, String code, String newPassword) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "MVPForgetPasswordBean{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", code='" + code + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
