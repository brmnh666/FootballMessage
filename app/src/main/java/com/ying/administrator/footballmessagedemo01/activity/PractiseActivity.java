package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.TabLayoutViewPagerAdapter;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.fragment.PractiseSkillFragment;
import com.ying.administrator.footballmessagedemo01.fragment.PractiseTacticsFragment;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;

import java.util.ArrayList;


public class PractiseActivity extends BaseActivity implements DefineView {

    private TabLayout tab_practise_layout;
    private ViewPager practise_viewpager;
    private Button btn_back;
    private TextView top_title;
    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    private String username;


    //添加tablayout的内容
    private ArrayList<String> title = new ArrayList<>();

    private PractiseSkillFragment practiseSkillFragment;
    private PractiseTacticsFragment practiseTacticsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise);
        initView();
        initValidata();
        initListener();
        bindData();

    }


    @Override
    public void initView() {
        tab_practise_layout=findViewById(R.id.tab_practise_layout);
        practise_viewpager=findViewById(R.id.practise_viewpager);
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
    }

    @Override
    public void initValidata() {
        top_title.setText("练球");
        username = getIntent().getStringExtra("username");
        Log.d("pa",username);
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {
        //为tablayout添加内容
        title.add("技术");
        title.add("战术");
        practiseSkillFragment=new PractiseSkillFragment();
        practiseTacticsFragment=new PractiseTacticsFragment();

        fragmentsList.add(practiseSkillFragment);
        fragmentsList.add(practiseTacticsFragment);

        TabLayoutViewPagerAdapter practisePagerAdapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(),fragmentsList,title);
        tab_practise_layout.setTabMode(TabLayout.MODE_FIXED);
        practise_viewpager.setAdapter(practisePagerAdapter);
        tab_practise_layout.setupWithViewPager(practise_viewpager);

        practise_viewpager.setOffscreenPageLimit(1); //预加载设置为1 默认为1

         //获得传来的值判断是哪个fragment
        Intent intent =getIntent();
        String target = intent.getStringExtra("target"); //获得传来的target
        if (target.equals("intent_skill")){

            practise_viewpager.setCurrentItem(0);//技术训练界面

        }else if (target.equals("intent_tactics")){

            practise_viewpager.setCurrentItem(1);//战术训练界面

        }


    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    PractiseActivity.this.finish();
                    break;

            }
        }

    }
}
