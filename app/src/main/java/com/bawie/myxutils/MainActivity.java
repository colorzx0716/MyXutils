package com.bawie.myxutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_xiao)
    TextView tv_xiao;

    //解析地址
    private String url = "http://v.juhe.cn/toutiao/index?type=&key=22a108244dbb8d1f49967cd74a0c144d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        x.view().inject(this);

        RequestParams params = new RequestParams(url);
        //post请求
        params.addBodyParameter("username","abc");
        params.addParameter("password","123");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //请求的结果
                System.out.println("result = " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        /*
         Get请求
         params.addQueryStringParameter("username","abc");
         params.addQueryStringParameter("password","123");

         x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                System.out.println("结果在这里" + result);
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
*/


    }

}
