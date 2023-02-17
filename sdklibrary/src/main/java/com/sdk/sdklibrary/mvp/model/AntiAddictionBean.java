package com.sdk.sdklibrary.mvp.model;

/**
 * Data:2023/2/17
 * Time:17:34
 * author:colin handsome
 */
public class AntiAddictionBean {
    String name;
    String idnumber;

    @Override
    public String toString() {
        return "AntiAddictionBean{" +
                "name='" + name + '\'' +
                ", idnumber='" + idnumber + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
