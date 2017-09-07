package com.bawie.myxutils;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class XiangQingActivity extends BaseActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);

        wv = (WebView) findViewById(R.id.wv);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        wv.getSettings().setJavaScriptEnabled(true);

        wv.loadUrl(url);

    }

    @Override
    public void onBackPressed() {

        scrollToFinishActivity();//左滑退出activity
    }
}
