package com.example.sdklibrary.mvp.model;

import java.util.List;

/**
 * Created by bolin
 */

public class MVPLoginResultBean {

    //成功
    /*
        {
            "ts": 1673233978,
            "code": 20000,
            "msg": "success",
            "data": {
                "appId": "111111",
                "username": "13246642620",
                "password": "4c3a4ccfc9e268517207d9947add1413",
                "ticket": "7f97dea0ff774f522c1feac5c22d37a992ec63ab1ed77c34999f2f5d45004590",
                "uid": "884847014875136"
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

    @Override
    public String toString() {
        return "MVPLoginResultBean{" +
                "appId='" + appId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ticket='" + ticket + '\'' +
                ", uid='" + uid + '\'' +
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
}
