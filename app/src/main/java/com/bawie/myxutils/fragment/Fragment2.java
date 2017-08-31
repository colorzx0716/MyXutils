package com.bawie.myxutils.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawie.myxutils.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by 张肖肖 on 2017/8/31.
 */

@ContentView(R.layout.fragment2)
public class Fragment2 extends Fragment {
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = x.view().inject(this,inflater,container);
        }
        return mRootView;
    }
}
