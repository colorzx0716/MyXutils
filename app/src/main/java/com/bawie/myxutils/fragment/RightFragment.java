package com.bawie.myxutils.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawie.myxutils.LiXianActivity;
import com.bawie.myxutils.R;

/**
 * Created by 张肖肖 on 2017/8/31.
 */

public class RightFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    private TextView lixian;
    private ImageView tuisong01;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView = inflater.inflate(R.layout.right_item,container,false);

            tuisong01 = mRootView.findViewById(R.id.iv_tuisong01);
            lixian = mRootView.findViewById(R.id.tv_lixian);


            lixian.setOnClickListener(this);
            tuisong01.setOnClickListener(this);

        }

        return mRootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_lixian:
                //点击按钮跳转到下载页面
                Intent intent = new Intent(getActivity(), LiXianActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_tuisong01:

               /* tuisong01.setImageResource(R.drawable.rightoff);*/

                break;
        }
    }
}
