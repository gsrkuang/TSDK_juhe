package com.example.sdklibrary.mvp.model;

import java.util.List;

/**
 * Created by bolin
 */

public class MVPLoginResultBean {

    //成功
    /*
        {
            "ts": 1675061284,
            "code": 20000,
            "msg": "success",
            "data": {
                "appId": "111111",
                "username": "lin2023",
                "password": null,
                "ticket": "099fe79345672e6f2d43838f9e35b091dfe4c6b2e16228d4e51e2b0490004999",
                "uid": "5469464757858304",
                "phone": null,
                "realName": false
            }
        }
    */

    //失败
   /* {
        "ts": 1672888385,
            "code": 12000,
            "msg": "Invalid appId: 111112",
            "data": null
    }*/

    private String appId;
    private String username;
    private String password;
    private String ticket;
    private String uid;
    private String phone;
    private Boolean realName;

    @Override
    public String toString() {
        return "MVPLoginResultBean{" +
                "appId='" + appId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ticket='" + ticket + '\'' +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", realName=" + realName +
                '}';
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getRealName() {
        return realName;
    }

    public void setRealName(Boolean realName) {
        this.realName = realName;
    }
}
