package com.bawie.myxutils;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 张肖肖 on 2017/8/29.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);//输出debug日志，开启会影响性能



    }
}
