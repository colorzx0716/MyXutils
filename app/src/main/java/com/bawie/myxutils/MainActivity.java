package com.bawie.myxutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bawie.myxutils.bean.ScrollBean;
import com.bawie.myxutils.fragment.Fragment1;
import com.bawie.myxutils.fragment.Fragment2;
import com.bawie.myxutils.fragment.LeftFragment;
import com.bawie.myxutils.fragment.RightFragment;
import com.bawie.myxutils.view.HorizontalScollTabhost;
import com.kson.slidingmenu.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private HorizontalScollTabhost mytabhost;

    private List<Fragment> fragmentList;
    private List<ScrollBean> beanList;
    private ImageView user,shezhi;
    private SlidingMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
        //静态广播发送
        sendBroadcast(new Intent("kson.test"));
        //头部横条
        mytabhost = (HorizontalScollTabhost) findViewById(R.id.tabhost);

        initView();
        initData();//滑动头部
        initMenu();//左右侧拉

    }

    //寻找控件
    private void initView() {

        user = (ImageView) findViewById(R.id.tou_iv_user);
        shezhi = (ImageView) findViewById(R.id.tou_iv_shezhi);

        //图片点击事件
        user.setOnClickListener(this);
        shezhi.setOnClickListener(this);
    }


    /**
     * 滑动头部
     */
    private void initData() {

        fragmentList = new ArrayList<>();
        beanList = new ArrayList<>();

      ScrollBean sb = new ScrollBean();
        sb.id = "top";
        sb.username = "头条";
        beanList.add(sb);

        sb = new ScrollBean();
        sb.username = "娱乐";
        sb.id = "yule";
        beanList.add(sb);

        sb = new ScrollBean();
        sb.username = "社会";
        sb.id = "shehui";
        beanList.add(sb);

        sb = new ScrollBean();
        sb.username = "体育";
        sb.id = "tiyu";
        beanList.add(sb);

        sb = new ScrollBean();
        sb.username = "科技";
        sb.id = "keji";
        beanList.add(sb);

        sb = new ScrollBean();
        sb.username = "财经";
        sb.id = "caijing";
        beanList.add(sb);

        sb = new ScrollBean();
        sb.username = "时尚";
        sb.id = "shishang";
        beanList.add(sb);

        sb = new ScrollBean();
        sb.username = "军事";
        sb.id = "junshi";
        beanList.add(sb);

        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment2());

        mytabhost.diaplay(beanList,fragmentList);
    }

    /**
     * 左右侧拉
     */
    private void initMenu() {

        //添加菜单
        menu = new SlidingMenu(this);
        menu.setMenu(R.layout.left_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu,new LeftFragment()).commit();

        //设置slidingmenu相关属性
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.BehindOffsetRes);  // 设置滑动菜单视图的宽度

        //设置右菜单
        menu.setSecondaryMenu(R.layout.right_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu,new RightFragment()).commit();

        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tou_iv_user:
                menu.showMenu();
                break;
            case R.id.tou_iv_shezhi:
                menu.showSecondaryMenu();
                break;
        }

    }
}
