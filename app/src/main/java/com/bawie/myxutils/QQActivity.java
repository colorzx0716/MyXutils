package com.bawie.myxutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class QQActivity extends AppCompatActivity {

    private TextView username;
    private ImageView iv_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        initView();
    }

    //初始化控件
    private void initView() {
        username = (TextView) findViewById(R.id.username);
        iv_user = (ImageView) findViewById(R.id.iv_user);
    }

    /**
     * 获取平台用户信息的接口、
     * qq方式
     * @param view
     */
    public void qqClick(View view) {
      //qq方式
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
    }

    /**
     * 授权的回调接口类
     */
    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
            String name = data.get("name");
            String gender = data.get("gender");
            String photoUrl = data.get("iconurl");
            username.setText(name + " " + gender);
            ImageLoader.getInstance().displayImage(photoUrl, iv_user);

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            if (UMShareAPI.get(QQActivity.this).isInstall(QQActivity.this, SHARE_MEDIA.QQ)) {
                Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "没有安装QQ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

    //关闭
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 从qq第三方授权页面返回数据的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
