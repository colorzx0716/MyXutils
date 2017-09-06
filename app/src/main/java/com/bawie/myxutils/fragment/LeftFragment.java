package com.bawie.myxutils.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bawie.myxutils.LoginActivity;
import com.bawie.myxutils.MainActivity;
import com.bawie.myxutils.QQActivity;
import com.bawie.myxutils.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 张肖肖 on 2017/8/31.
 */

public class LeftFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private ImageView jiantou;
    private ImageView qq;
    private ImageView iv_night;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView = inflater.inflate(R.layout.left_item,container,false);
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jiantou = mRootView.findViewById(R.id.iv_jiantou);
        qq = mRootView.findViewById(R.id.iv_qq);
        //夜间模式点击图片
        iv_night = mRootView.findViewById(R.id.iv_night);
        iv_night.setOnClickListener(this);

        //点击qq图片按钮跳转到授权页面
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),QQActivity.class);
                startActivity(intent);
            }
        });

        //箭头图片点击到登录页面
        jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //切换夜间模式
    @Override
    public void onClick(View view) {
        int uiMode;
        uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(uiMode == Configuration.UI_MODE_NIGHT_YES){
            ( (MainActivity)getActivity()).getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            getActivity().getSharedPreferences("theme",MODE_PRIVATE).edit().putBoolean("night_theme",false).commit();

        }else if(uiMode == Configuration.UI_MODE_NIGHT_NO){

            ( (MainActivity)getActivity()).getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            getActivity().getSharedPreferences("theme",MODE_PRIVATE).edit().putBoolean("night_theme", true).commit();

        }

        getActivity().recreate();

    }
}

















