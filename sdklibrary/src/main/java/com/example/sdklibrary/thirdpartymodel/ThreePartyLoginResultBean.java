package com.example.sdklibrary.thirdpartymodel;

import java.util.List;

/**
 * Created by colin 2022.9.14
 * 第三方支付回调使用
 */

public class ThreePartyLoginResultBean {

    private String type;
    private String uid;
    private String email;
    private String getIdToken;
    private String msg; //登陆失败，登陆成功

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGetIdToken() {
        return getIdToken;
    }

    public void setGetIdToken(String getIdToken) {
        this.getIdToken = getIdToken;
    }
}
