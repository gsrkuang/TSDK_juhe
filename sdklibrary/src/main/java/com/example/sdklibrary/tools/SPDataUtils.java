package com.example.sdklibrary.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by bolin
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
    public void saveLoginData(String username ,String password ,String nickname ,String id,String phone,boolean realname ){
        SPDataUtils.getInstance().saveString("user_account",username);
        SPDataUtils.getInstance().saveString("user_password",password);
        SPDataUtils.getInstance().saveString("user_nickname",nickname);
        SPDataUtils.getInstance().saveString("user_id",id);
        SPDataUtils.getInstance().saveString("user_phone",phone);
        SPDataUtils.getInstance().saveBoolean("user_realname",realname);
        SPDataUtils.getInstance().saveBoolean("loginStatus",true);
    }
    //使用SharedPreference来存储登陆后的数据
    public void saveOneKeyLoginData(String username ,String password ,String nickname ,String id,String phone,boolean realname){
        SPDataUtils.getInstance().saveString("onekey_account",username);
        SPDataUtils.getInstance().saveString("onekey_password",password);
        SPDataUtils.getInstance().saveString("onekey_nickname",nickname);
        SPDataUtils.getInstance().saveString("onekey_id",id);

        SPDataUtils.getInstance().saveString("onekey_phone",phone);
        SPDataUtils.getInstance().saveBoolean("onekey_realname",realname);
        SPDataUtils.getInstance().saveBoolean("loginStatus",true);
    }



    //点击注销，清除登陆数据
    public void clearLogin(){
        SPDataUtils.getInstance().saveBoolean("loginStatus",false);
    }
    //获取登陆状态
    public boolean getLoginStatus(){
        return SPDataUtils.getInstance().getBoolean("loginStatus",false);
    }
    public String getNickName(){
        return SPDataUtils.getInstance().getString("user_nickname","");
    }

    public String getUserId(){
        return SPDataUtils.getInstance().getString("user_id","");
    }

    public String getUserAccount(){
        return SPDataUtils.getInstance().getString("user_account","");
    }
    public String getUserPassword(){
        return SPDataUtils.getInstance().getString("user_password","");
    }

    public String getUserPhone(){
        return SPDataUtils.getInstance().getString("user_phone","");
    }
    public boolean getUserRealName(){
        return SPDataUtils.getInstance().getBoolean("user_realname",false);
    }

    public String getOneKeyNickName(){
        return SPDataUtils.getInstance().getString("onekey_nickname","");
    }

    public String getOneKeyId(){
        return SPDataUtils.getInstance().getString("onekey_id","");
    }

    public String getOneKeyAccount(){
        return SPDataUtils.getInstance().getString("onekey_account","");
    }
    public String getOneKeyPassword(){
        return SPDataUtils.getInstance().getString("onekey_password","");
    }

}
