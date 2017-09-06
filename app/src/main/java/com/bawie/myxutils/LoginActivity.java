package com.bawie.myxutils;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.myxutils.bean.User;
import com.bawie.myxutils.utils.SharedPreferencesUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //倒计时时间
    private int TIME = 5;
    private final int SECOND = 1000;
    private EditText mTel;
    private TextView mCodeTv;
    private Button mSend;
    private EditText mCodeInput;

    private EventHandler eventHandler;

    Handler timeHandler = new Handler();
    Runnable timeRunable = new Runnable() {
        @Override
        public void run() {
            TIME--;
            if(TIME == 0){
                timeHandler.removeCallbacks(this);
                TIME= 5;
                mCodeTv.setEnabled(true);
                mCodeTv.setText("再次获取");
            }else{
                mCodeTv.setEnabled(false);
                mCodeTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                mCodeTv.setText(TIME + "s");
                timeHandler.postDelayed(this, SECOND);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        registerSMS();

    }

    private void registerSMS() {
        //创建EventHandler对象
        eventHandler = new EventHandler(){
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    if (result == SMSSDK.RESULT_COMPLETE) {//只有回服务器验证成功，才能允许用户登录
                        //回调完成,提交验证码成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "服务器验证成功", Toast.LENGTH_SHORT).show();
                                    User user = new User();
                                    user.uid = mTel.getText().toString();
                                    user.phone = mTel.getText().toString();

                                    SharedPreferencesUtil.putPreferences("uid", user.uid);
                                    SharedPreferencesUtil.putPreferences("phone", user.phone);
                                }
                            });

                        }

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initView() {
        //输手机号
        mTel = (EditText) findViewById(R.id.et_phone);
        //输验证码
        mCodeInput = (EditText) findViewById(R.id.et_number);
        //验证码文字
        mCodeTv = (TextView) findViewById(R.id.yanzhengma);
        //进入按钮
        mSend = (Button) findViewById(R.id.send);

        mCodeTv.setOnClickListener(this);
        mSend.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send://按钮
                verify();
                SMSSDK.submitVerificationCode("86", mTel.getText().toString(), mCodeInput.getText().toString());
                break;
            case R.id.yanzhengma: //验证码文字
                if (TextUtils.isEmpty(mTel.getText().toString())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                timeHandler.postDelayed(timeRunable, SECOND);
                SMSSDK.getVerificationCode("86", mTel.getText().toString());
                break;
        }
    }

    private void verify() {
        if (TextUtils.isEmpty(mTel.getText().toString())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mCodeInput.getText().toString())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //点击叉号返回上一页
    public void chahao(View view){
        finish();//返回
    }


}
