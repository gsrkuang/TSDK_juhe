package com.example.sdklibrary.mvp.model;

import java.util.List;

/**
 * Created by bolin
 */

public class MVPLoginResultBean {

    //成功
    /*
        {
        "ts": 1672888642,
        "code": 20000,
        "msg": "success",
        "data": {
            "appId": "111111",
            "username": "colin1",
            "ticket": "1842e4e23d1bcd79c659f0b99b6402c7",
            "uid": "675944100554240"
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
    private String ticket;
    private String uid;

    @Override
    public String toString() {
        return "MVPLoginResultBean{" +
                "appId='" + appId + '\'' +
                ", username='" + username + '\'' +
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
