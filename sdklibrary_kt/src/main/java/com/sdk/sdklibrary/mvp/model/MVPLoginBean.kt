package com.sdk.sdklibrary.mvp.model

/**
 * @author colin
 * Date:2023-02-08
 * 登录
 */
class MVPLoginBean {
    var userName: String? = null
    var passWord: String? = null

    constructor() : super() {        // TODO Auto-generated constructor stub
    }

    constructor(userName: String?, passWord: String?) : super() {
        this.userName = userName
        this.passWord = passWord
    }

    override fun toString(): String {
        return ("MVPLoginBean [userName=" + userName + ", passWord=" + passWord
                + "]")
    }
}