package com.bawie.myxutils.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawie.myxutils.Bean;
import com.bawie.myxutils.R;
import com.bawie.myxutils.adapter.MyListAdapter;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.List;

import view.xlistview.XListView;

/**
 * Created by 张肖肖 on 2017/8/31.
 */
@ContentView(R.layout.fragment1)
public class Fragment1 extends Fragment implements XListView.IXListViewListener {


    private View mRootView;
    private XListView f1_xlv;

    //请求网址
    private String url = "http://v.juhe.cn/toutiao/index?type=&key=22a108244dbb8d1f49967cd74a0c144d";
    private List<Bean.ResultBean.DataBean> data;
    private MyListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = x.view().inject(this,inflater,container);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        f1_xlv = mRootView.findViewById(R.id.f1_xlv);
        f1_xlv.setXListViewListener(this);
        f1_xlv.setPullLoadEnable(true);
        f1_xlv.setPullRefreshEnable(true);
        initPost();
    }

    private void initPost() {
        x.view().inject(getActivity());

        RequestParams params = new RequestParams(url);
        //post请求
        params.addBodyParameter("username","abc");
        params.addParameter("password","123");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //请求的结果
                System.out.println("result = " + result);
                Gson gson = new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                Bean.ResultBean result1 = bean.getResult();
                data = result1.getData();

                //适配器
                adapter = new MyListAdapter(getContext(),data);
                f1_xlv.setAdapter(adapter);

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






    }

    //刷新
    @Override
    public void onRefresh() {
        data.addAll(data);
        adapter.notifyDataSetChanged();
        onLoad();
    }

    //停止刷新
    private void onLoad() {
        f1_xlv.stopLoadMore();
        f1_xlv.stopRefresh();
    }

    //加载更多
    @Override
    public void onLoadMore() {
        data.addAll(data);
        adapter.notifyDataSetChanged();
        onLoad();
    }
}
