package com.bawie.myxutils.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawie.myxutils.LiXianActivity;
import com.bawie.myxutils.R;

/**
 * Created by 张肖肖 on 2017/8/31.
 */

public class RightFragment extends Fragment {

    private View mRootView;
    private TextView lixian;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView==null){
            mRootView = inflater.inflate(R.layout.right_item,container,false);

            lixian = mRootView.findViewById(R.id.tv_lixian);
            lixian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击按钮跳转到下载页面
                    Intent intent = new Intent(getActivity(), LiXianActivity.class);
                    startActivity(intent);
                }
            });

        }

        return mRootView;
    }

}
