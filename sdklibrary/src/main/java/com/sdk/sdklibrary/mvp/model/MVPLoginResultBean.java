package com.sdk.sdklibrary.mvp.model;

import java.util.List;

/**
 *@author colin
 * Date:2023-02-08
 */

public class MVPLoginResultBean {

    //成功
   /* {
        "ts": 1676539218,
            "code": 20000,
            "msg": "success",
            "data": {
                "appId": "111111",
                "username": "13246642620",
                "password": null,
                "ticket": "3a8b62ed98976534ae0a0ac7d99dbe869ddc23f13503e1e5f82a3ee925004916",
                "uid": "884847014875136",
                "phone": null,
                "realName": false,
                "isAdult":true
        }
    }*/

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
    private Boolean isAdult;

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
                ", isAdult=" + isAdult +
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

    public Boolean getAdult() {
        return isAdult;
    }

    public void setAdult(Boolean adult) {
        isAdult = adult;
    }
}
