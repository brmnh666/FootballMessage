package com.ying.administrator.footballmessagedemo01.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.TabLayoutViewPagerAdapter;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.fragment.MycollectNewsFragment;
import com.ying.administrator.footballmessagedemo01.fragment.MycolloectClothesFragment;
import com.ying.administrator.footballmessagedemo01.fragment.MycolloectShoesFragment;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.Timer;

public class MyCollectActivity extends BaseActivity implements DefineView {

    private TextView mycollection_hint;
    private Handler handler;
    private Runnable runnable;
    private Button btn_back;
    private TextView top_title;
    private TabLayout tab_mycollect_layout;
    private ViewPager mycolloect_viewpager;

    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    //添加tablayout的内容
    private ArrayList<String> title = new ArrayList<>();

    private MycollectNewsFragment mycollectNewsFragment;
    private MycolloectClothesFragment mycolloectClothesFragment;
    private MycolloectShoesFragment mycolloectShoesFragment;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollect);
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        tab_mycollect_layout=findViewById(R.id.tab_mycollect_layout);
        mycolloect_viewpager=findViewById(R.id.mycolloect_viewpager);
        mycollection_hint=findViewById(R.id.mycollection_hint);
    }

    @Override
    public void initValidata() {
        top_title.setText("收藏栏");

      // username = getIntent().getStringExtra("username");


    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());

        handler=new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                mycollection_hint.setVisibility(View.GONE);
            }
        },3000);
    }

    @Override
    public void bindData() {

        //为tablayout添加内容
        title.add("资讯");
        title.add("球衣");
        title.add("球鞋");
        mycollectNewsFragment=new MycollectNewsFragment();
        mycolloectClothesFragment=new MycolloectClothesFragment();
        mycolloectShoesFragment=new MycolloectShoesFragment();

        fragmentsList.add(mycollectNewsFragment);
        fragmentsList.add(mycolloectClothesFragment);
        fragmentsList.add(mycolloectShoesFragment);

        TabLayoutViewPagerAdapter PagerAdapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(),fragmentsList,title);
        tab_mycollect_layout.setTabMode(TabLayout.MODE_FIXED);
        mycolloect_viewpager.setAdapter(PagerAdapter);
        tab_mycollect_layout.setupWithViewPager(mycolloect_viewpager);
        mycolloect_viewpager.setOffscreenPageLimit(2); //预加载设置为1 默认为1
        mycolloect_viewpager.setCurrentItem(0);//新闻收藏界面




    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    MyCollectActivity.this.finish();
                    break;

            }
        }

    }
}
