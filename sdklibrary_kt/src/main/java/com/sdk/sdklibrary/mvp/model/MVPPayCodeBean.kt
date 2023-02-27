package com.sdk.sdklibrary.mvp.model

import java.io.Serializable

/**
 * @author colin
 * Date:2023-02-08
 * 支付bean
 */
class MVPPayCodeBean : Serializable {
    var uId: String? = null
    var oId: String? = null
    var pId: String? = null
    var pName: String? = null
    var price: String? = null
    var callbackInfo: String? = null

    override fun toString(): String {
        return "MVPPayBean{" +
                "uId='" + uId + '\'' +
                ", oId='" + oId + '\'' +
                ", pId='" + pId + '\'' +
                ", pName='" + pName + '\'' +
                ", price='" + price + '\'' +
                ", callbackInfo='" + callbackInfo + '\'' +
                '}'
    }


}