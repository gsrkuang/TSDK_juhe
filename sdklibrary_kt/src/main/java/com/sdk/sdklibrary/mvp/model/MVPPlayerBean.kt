package com.sdk.sdklibrary.mvp.model

/**
 * @author colin
 * Date:2023-02-08
 */
class MVPPlayerBean {
    var name: String? = null
    var server: String? = null
    var gameName: String? = null
    var id: String? = null

    override fun toString(): String {
        return "MVPPlayerBean{" +
                "name='" + name + '\'' +
                ", server='" + server + '\'' +
                ", gameName='" + gameName + '\'' +
                ", id='" + id + '\'' +
                '}'
    }
}