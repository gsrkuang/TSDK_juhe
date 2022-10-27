package com.example.sdklibrary.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by tzw on 2018/6/4.
 */

public class SPDataUtils {

    private static SharedPreferences shared = null;
    private static SharedPreferences.Editor editor = null;
    private static String xmlName = "PERMANENT";

    private static SPDataUtils sp;

    private SPDataUtils() {
    }

    public static void init(Context context) {
        if (sp == null) {
            synchronized (SPDataUtils.class) {
                if (sp == null) {
                    sp = new SPDataUtils();
                    shared = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
                    editor = shared.edit();
                }
            }
        }
    }

    public static SPDataUtils getInstance() {
        return sp;
    }

    public void saveString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public String getString(String key, String value) {
        return shared.getString(key, value);
    }
    public int getInt(String key, int value) {
        return shared.getInt(key, value);
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return shared.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean value) {
        return shared.getBoolean(key, value);
    }

    public void saveLong(String key, long value) {

        editor.putLong(key, value).commit();
    }

    public void saveInt(String key, int value) {
        editor.putInt(key, value).commit();
    }

    //使用SharedPreference来存储登陆后的数据
    public void saveLoginData(String username ,String password ,String nickname ,int id){
        SPDataUtils.getInstance().saveString("user_account",username);
        SPDataUtils.getInstance().saveString("user_password",password);
        SPDataUtils.getInstance().saveString("user_nickname",nickname);
        SPDataUtils.getInstance().saveInt("user_id",id);
    }

    public String getNickName(){
        return SPDataUtils.getInstance().getString("user_nickname","");
    }

    public int getUserId(){
        return SPDataUtils.getInstance().getInt("user_id",0);
    }

}
