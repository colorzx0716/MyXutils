package com.bawie.myxutils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bawie.myxutils.bean.ScrollBean;
import com.bawie.myxutils.fragment.Fragment1;
import com.bawie.myxutils.fragment.LeftFragment;
import com.bawie.myxutils.fragment.RightFragment;
import com.bawie.myxutils.view.HorizontalScollTabhost;
import com.kson.slidingmenu.SlidingMenu;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private HorizontalScollTabhost mytabhost;

    private List<Fragment> fragmentList;
    private List<ScrollBean> beanList;
    private ImageView user,shezhi;
    private SlidingMenu menu;
    private ImageView dot1;
    private List<ChannelBean> channelBeanList;

    //头部
    private String[] titles = {"推荐","热点","北京","视频","社会",
            "订阅","娱乐","图片","科技","汽车",
            "体育","财经","军事","国际","段子",
            "趣图","美女","健康","正能量","特卖",
            "中国好声音","历史","时尚","辟谣","探索","美国",
            "搞笑","故事","奇葩","情感"};

    private String jsonstr;
    private SharedPreferences pre;
    private  ScrollBean sb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
        //静态广播发送
        sendBroadcast(new Intent("kson.test"));
        //头部横条
        mytabhost = (HorizontalScollTabhost) findViewById(R.id.tabhost);

        pre = getSharedPreferences("setting",MODE_PRIVATE);//创建sp

        initView();
        initData();//滑动头部
        initMenu();//左右侧拉

    }

    //寻找控件
    private void initView() {

        user = (ImageView) findViewById(R.id.tou_iv_user);
        shezhi = (ImageView) findViewById(R.id.tou_iv_shezhi);
        dot1 = (ImageView) findViewById(R.id.dot1);

        //图片点击事件
        user.setOnClickListener(this);
        shezhi.setOnClickListener(this);
        dot1.setOnClickListener(this);
    }

    /**
     * 滑动头部
     */
    private void initData() {

        fragmentList = new ArrayList<>();
        beanList = new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            sb = new ScrollBean();
            sb.username = titles[i];
            beanList.add(sb);
            fragmentList.add(new Fragment1());
        }

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
            case R.id.dot1://s
                jsonstr = pre.getString("user_setting", null);
                channelBeanList = new ArrayList<>();
                if(jsonstr == null){

                    ChannelBean channelBean;

                    for (int i = 0; i <titles.length ; i++) {
                        if(i<10){
                            channelBean = new ChannelBean(titles[i],true);
                            sb.username = titles[i];
                        }else{
                            channelBean = new ChannelBean(titles[i],false);
                        }
                        channelBeanList.add(channelBean);
                    }
                }else{
                    //不为空使用之前回传的数据
                    try {
                        JSONArray arr =new JSONArray(jsonstr);
                        System.out.println("arr.toString() = " + arr.toString());
                        for (int i = 0; i <arr.length() ; i++) {
                            JSONObject oo = (JSONObject) arr.get(i);
                            String name = oo.getString("name");
                            boolean isSelected = oo.getBoolean("isSelect");
                            ChannelBean channelBean = new ChannelBean(name,isSelected);
                            channelBeanList.add(channelBean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ChannelActivity.startChannelActivity(MainActivity.this,channelBeanList);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        //判断回传吗是否相同
        if(requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE){
            //获取回传的值
            jsonstr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
            //存入sp
            pre.edit().putString("user_setting",jsonstr).commit();

            beanList.clear();//清空
            List<Fragment> fragmentList2 = new ArrayList<>();

            try {
                JSONArray arr =new JSONArray(jsonstr);
                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject oo = (JSONObject) arr.get(i);
                    String name = oo.getString("name");
                    boolean isSelected = oo.getBoolean("isSelect");
                    if(isSelected){
                        ScrollBean s = new ScrollBean();
                        s.username = name;

                        beanList.add(s);
                        fragmentList2.add(fragmentList.get(i));
                    }
                }
                mytabhost.remove();
                mytabhost.diaplay(beanList,fragmentList2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
