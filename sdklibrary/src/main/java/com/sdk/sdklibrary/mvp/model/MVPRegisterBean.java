package com.sdk.sdklibrary.mvp.model;

/**
 * Created by bolin
 * 注册
 */

public class MVPRegisterBean {

    private String userName;
    private String passWord;

    public MVPRegisterBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MVPRegisterBean(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "MVPRegisterBean{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}
