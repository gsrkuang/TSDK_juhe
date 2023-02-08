package com.sdk.sdklibrary.mvp.model;

/**
 *@author colin
 * Date:2023-02-08
 */

public class MVPUserInfoResultBean {

    //成功
    /*
        {
            "ts": 1675218030,
            "code": 20000,
            "msg": "success",
            "data": {
                "account": "lin2023",
                "openId": null,
                "username": "lin2023",
                "phone": "13246642620",
                "email": null,
                "ticket": "8e6633bf27948c8976ad02cb29647bed250842c6c0a68615322121fd12003590",
                "uid": "5469464757858304"
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

    private String account;
    private String openId;
    private String username;
    private String phone;
    private String email;
    private String ticket;
    private String uid;
    private Boolean realName;

    @Override
    public String toString() {
        return "MVPUserInfoResultBean{" +
                "account='" + account + '\'' +
                ", openId='" + openId + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", ticket='" + ticket + '\'' +
                ", uid='" + uid + '\'' +
                ", realName=" + realName +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getRealName() {
        return realName;
    }

    public void setRealName(Boolean realName) {
        this.realName = realName;
    }
}
