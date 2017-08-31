package com.bawie.myxutils;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.xutils.x;

/**
 * Created by 张肖肖 on 2017/8/29.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initIMG();
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
