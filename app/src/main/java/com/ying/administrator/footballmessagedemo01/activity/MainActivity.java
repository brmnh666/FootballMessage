package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.application.MyApplication;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.UserBean;
import com.ying.administrator.footballmessagedemo01.fragment.ClothFragment;
import com.ying.administrator.footballmessagedemo01.fragment.FootballFragment;
import com.ying.administrator.footballmessagedemo01.fragment.FootballVideoFragment;
import com.ying.administrator.footballmessagedemo01.fragment.MainInfoFragment;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.BottomBar;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;


public class MainActivity extends BaseActivity implements DefineView,NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private ImageView top_bar_icon;
    private NavigationView navigationView;
    private  BottomBar bottomBar;
    private TextView tv_info_name;
    private TextView tv_info_day;
    private TextView tv_info_no;
    private ImageView img_info_head;
    String username=" ";
    private long mExittime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            /*将mianactivity添加到销毁队列*/
        MyApplication.addDestoryActivity(this,"MainActivity");

        initView();
        initValidata();
        initListener();
        bindData();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);



        navigationView.setNavigationItemSelectedListener(this);



        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#272636", "#17B03B")
                .addItem(MainInfoFragment.class,
                        "首页",
                        R.drawable.item_home_before,
                        R.drawable.item_home_after)
                .addItem(FootballVideoFragment.class,
                        "视频",
                        R.drawable.item_video_before,
                        R.drawable.item_video_after)
                .addItem(ClothFragment.class,
                        "装备",
                        R.drawable.item_cloth_before,
                        R.drawable.item_cloth_after)
                .addItem(FootballFragment.class,
                        "练球",
                        R.drawable.item_football_before,
                        R.drawable.item_football_after)
                .setIconHeight(30).setIconWidth(30).setTitleSize(15)
                .build();







    }


    /*
     * 通过onBackPressed方法,当点击返回按钮的时候,如果DrawerLayout是打开状态则关闭*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void initView() {
        top_bar_icon = findViewById(R.id.top_bar_icon);  //头像
        bottomBar =findViewById(R.id.bottom_bar);
        navigationView = findViewById(R.id.nav_view);

        View headview= navigationView.getHeaderView(0); //获取到nav_header_main

        tv_info_name = headview.findViewById(R.id.info_name);
        tv_info_day = headview.findViewById(R.id.info_day);
        tv_info_no=headview.findViewById(R.id.info_No);
        img_info_head=headview.findViewById(R.id.img_head);

    }
    //初始化左侧listview中的数据
    @Override
    public void initValidata() {

        username = getIntent().getStringExtra("username");

      /*  *//*快捷入口*//*
        if (!username.equals("root")){

            String url = Config.LOCALHOST_URL+"/Myservers/SelectServlet?username="+username;

            OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                @Override
                public void requestFailure(Request request, Exception e) {
                    Log.d("error","请检查是否开启Tomcat");
                }

                @Override
                public void requestSuccess(String result) {

                    *//*解析传来的来*//*
                    Gson gson =new Gson();
                    UserBean userBean= gson.fromJson(result,UserBean.class);


                    tv_info_name.setText(userBean.getName());
                    tv_info_day.setText("注册时间"+userBean.getRegister_time());
                    tv_info_no.setText("您是本app的第 "+userBean.getId()+" 位用户");

                }

            });
        }else {
            tv_info_name.setText("root");
            tv_info_day.setText("root");
        }*/
    }
    @Override
    public void initListener() {
        top_bar_icon.setOnClickListener(this);
        img_info_head.setOnClickListener(this);

    }
    @Override
    public void bindData() {

    }


    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.icon_collect) { //我的收藏

             Intent intent=new Intent(MainActivity.this,MyCollectActivity.class);
             intent.putExtra("username",username);
             startActivity(intent);
        } else if (id == R.id.icon_history) {  //浏览历史
             Intent intent =new Intent(MainActivity.this,SkimHistroyActivity.class);
             intent.putExtra("username",username);
             startActivity(intent);


        } else if (id == R.id.icon_feedback) {   //意见反馈
            Intent intent=new Intent(MainActivity.this,SuggestFeedbackActivity.class);
            intent.putExtra("username",username); //将用户名传过去
            startActivity(intent);
        } else if (id == R.id.icon_informationcenter) {   // 消息中心

        }else if (id == R.id.icon_historyToday) { //历史今日
            Intent intent=new Intent(MainActivity.this,HistoryToDayActvity.class);
            startActivity(intent);
        }else if (id == R.id.icon_aboutme) { //关于我们
            Intent intent=new Intent(MainActivity.this,AboutMeActivity.class);
            startActivity(intent);
        }else if (id == R.id.icon_share) { //分享

        }else if (id == R.id.icon_share) { //设置

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /*
        当点击头像的时候弹出左侧菜单
        * */
    @Override
    public void onClick(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (view.getId()){
            case R.id.top_bar_icon:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.img_head:
                Intent intent =new Intent(MainActivity.this,ModifyMyInfoActivity.class);
                intent.putExtra("username",username); //将用户名传过去
                startActivity(intent);
                break;
                default:
                    break;

        }
    }



    /*
    * 在onresume里调用 查找数据库
    * */


    @Override
    protected void onResume() {
        super.onResume();


            String url = Config.LOCALHOST_URL+"/Myservers/SelectServlet?username="+username;

            OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                @Override
                public void requestFailure(Request request, Exception e) {
                    Log.d("error","请检查是否开启Tomcat");
                }

                @Override
                public void requestSuccess(String result) {

                    /*解析传来的来*/
                    Gson gson =new Gson();
                    UserBean userBean= gson.fromJson(result,UserBean.class);


                    tv_info_name.setText(userBean.getName());
                    tv_info_day.setText("注册时间"+userBean.getRegister_time());
                    tv_info_no.setText("您是本app的第 "+userBean.getId()+" 位用户");

                }

            });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了返回键
        if (keyCode==KeyEvent.KEYCODE_BACK){
            //与上次点击返回键作差
            if ((System.currentTimeMillis()-mExittime)>2000){
                //大于2秒认为是误操作
                CustomToast.showToast(getApplicationContext(),"再按一次退出","",2000);
                //并记录记录下本次点击返回键的时刻
                mExittime=System.currentTimeMillis();
            }else {
                //小于2秒 则用户希望退出
                System.exit(0);
            }
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }
}
