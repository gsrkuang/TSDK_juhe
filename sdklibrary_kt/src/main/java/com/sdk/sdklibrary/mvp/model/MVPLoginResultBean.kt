package com.sdk.sdklibrary.mvp.model

/**
 * @author colin
 * Date:2023-02-08
 */
class MVPLoginResultBean {
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
    var appId: String? = null
    var username: String? = null
    var password: String? = null
    var ticket: String? = null
    var uid: String? = null
    var phone: String? = null
    var realName: Boolean? = null
    var adult: Boolean? = null

    override fun toString(): String {
        return "MVPLoginResultBean{" +
                "appId='" + appId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ticket='" + ticket + '\'' +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", realName=" + realName +
                ", isAdult=" + adult +
                '}'
    }
}