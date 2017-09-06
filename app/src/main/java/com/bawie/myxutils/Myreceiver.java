package com.bawie.myxutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 张肖肖 on 2017/9/6.
 */

public class Myreceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("kson.test")) {
            //走收到广播的逻辑
        }
    }
}
