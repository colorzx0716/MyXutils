package com.bawie.myxutils;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.bawie.myxutils.bean.Constants;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 张肖肖 on 2017/8/29.
 */

public class MyApp extends Application {

    {
      PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
     //设置极光推送bug模式
        JPushInterface.setDebugMode(true);
        //初始化极光推送
        JPushInterface.init(this);

        UMShareAPI.get(this);
        initIMG();

        if(getSharedPreferences("theme",MODE_PRIVATE).getBoolean("night_theme",false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        mContext = this;
        MobSDK.init(this, Constants.KEY, Constants.SECRET);

        x.Ext.init(this);
        x.Ext.setDebug(false);//输出debug日志，开启会影响性能

    }

    private void initIMG() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(360))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);


    }
}
