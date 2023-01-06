package com.example.sdklibrary.mvp.model;

/**
 * Date:2023-01-05
 * Time:15:24
 * author:colin
 */
public class ApiResponse<T> {

    private int ts;
    private int code;
    private String msg;
    private T data;

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
