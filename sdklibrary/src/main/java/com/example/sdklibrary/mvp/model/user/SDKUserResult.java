package com.example.sdklibrary.mvp.model.user;

/**
 * Date:2023-01-15
 * Time:18:52
 * author:colin
 *
 * 暴露给SDK的用户接入的信息返回接口
 */
public class SDKUserResult {
    private String username;
    private String token;
    private String uid;

    @Override
    public String toString() {
        return "SDKUserResult{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
