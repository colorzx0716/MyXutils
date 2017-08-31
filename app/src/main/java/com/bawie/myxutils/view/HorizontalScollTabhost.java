package com.bawie.myxutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawie.myxutils.R;
import com.bawie.myxutils.bean.ScrollBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张肖肖 on 2017/8/31.
 */

public class HorizontalScollTabhost extends LinearLayout implements ViewPager.OnPageChangeListener {

    private Context myContext;

    private int myColor;

    private HorizontalScrollView myhscrollview;
    private LinearLayout myMenuLayout;
    private ViewPager vp;

    //创建集合存放数据
    private List<ScrollBean> beanList;
    private List<Fragment> fragmentList;
    private List<TextView> topTV;

    private int count;//数量

    private MyPager myPager;

    public HorizontalScollTabhost(Context context) {
        this(context,null);
    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalScollTabhost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.myContext = context;
        init(context,attrs);//初始化自定义属性和view
    }
    /**
     * 初始化自定义属性和view
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalScollTabhost);
        myColor = typedArray.getColor(R.styleable.HorizontalScollTabhost_top_background, 0xffffff);
        typedArray.recycle();

        initView();//初始化View
    }

    private void initView() {

        View view = LayoutInflater.from(myContext).inflate(R.layout.horizontal_scroll_tabhost, this, true);
         myhscrollview = view.findViewById(R.id.hst_view);
        myMenuLayout = view.findViewById(R.id.hst_layout);
        vp = view.findViewById(R.id.vp);
        vp.addOnPageChangeListener(this);
    }

    /**
     * 供调用者调用，在main主页中会调用这个方法，保证数据的独立
     */
    public void diaplay(List<ScrollBean> list,List<Fragment> fragments){
        this.beanList = list;
        this.count = beanList.size();
        this.fragmentList = fragments;
        topTV = new ArrayList<>(count);
        drawUi();//绘制页面的所有元素
    }

    private void drawUi() {
        drawHorizontal();//绘制横向滑动菜单
        drawViewpager();//绘制viewpager
    }

    //绘制viewpager
    private void drawViewpager() {
        myPager = new MyPager(((FragmentActivity)myContext).getSupportFragmentManager());
        vp.setAdapter(myPager);

    }

    private void drawHorizontal() {
        myMenuLayout.setBackgroundColor(myColor);
        for (int i = 0; i <count ; i++) {
            ScrollBean scrollBean = beanList.get(i);
            final TextView tv = (TextView) View.inflate(myContext, R.layout.top_tv_news, null);
            tv.setText(scrollBean.username);

            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    vp.setCurrentItem(finalI);
                    moveIitemToCenter(tv);//点击让文字居中的方法
                }
            });
            myMenuLayout.addView(tv);
            topTV.add(tv);
        }
        //默认设置第一项为选中，
        topTV.get(0).setSelected(true);
    }

    //移动view对象到中间
    private void moveIitemToCenter(TextView tv) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int[] locations = new int[2];
        tv.getLocationInWindow(locations);
        int rbWidth = tv.getWidth();
        myhscrollview.smoothScrollBy((locations[0] + rbWidth/2 - screenWidth/2),0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(myMenuLayout != null && myMenuLayout.getChildCount() > 0){

            for (int i = 0; i < myMenuLayout.getChildCount(); i++) {
                if(i == position){
                    myMenuLayout.getChildAt(i).setSelected(true);
                }else{
                    myMenuLayout.getChildAt(i).setSelected(false);
                }
            }
        }
        //移动view，水平居中
        moveIitemToCenter(topTV.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * viewpager适配器
     */
    private class MyPager extends FragmentPagerAdapter {
        public MyPager(FragmentManager fm) {
         super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}
