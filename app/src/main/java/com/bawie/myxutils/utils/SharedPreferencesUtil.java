package com.bawie.myxutils.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bawie.myxutils.MyApp;

/**
 * Created by 张肖肖 on 2017/9/1.
 */

public class SharedPreferencesUtil {
    private final static String KEY = "common_data";
    /**
     * 得到SharedPreferences对象
     * @return
     */
    public static SharedPreferences getPreferences() {
        return MyApp.mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    /**
     * 存一行数据，uid
     * @param key
     * @param value
     */

    public static void putPreferences(String key, String value) {
        SharedPreferences.Editor mEditor = getPreferences().edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }
    /**
     * 获取uid的数据
     * @param key
     * @return
     */
    public static String getPreferencesValue(String key) {
        return getPreferences().getString(key, "");
    }

    /**
     * 清除指定数据
     * @param key
     */
    public static void clearPreferences(String key) {

        SharedPreferences.Editor mEditor = getPreferences().edit();
        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * 清空所有数据
     */
    public static void clearPreferences() {

        SharedPreferences.Editor mEditor = getPreferences().edit();
        mEditor.clear();
        mEditor.commit();
    }

}
