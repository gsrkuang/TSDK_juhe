package com.sdk.sdklibrary.mvp.model

/**
 * @author colin
 * Date:2023-02-08
 */
class MVPUserInfoResultBean {
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
    var account: String? = null
    var openId: String? = null
    var username: String? = null
    var phone: String? = null
    var email: String? = null
    var ticket: String? = null
    var uid: String? = null
    var realName: Boolean? = null

    override fun toString(): String {
        return "MVPUserInfoResultBean{" +
                "account='" + account + '\'' +
                ", openId='" + openId + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", ticket='" + ticket + '\'' +
                ", uid='" + uid + '\'' +
                ", realName=" + realName +
                '}'
    }
}