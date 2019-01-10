package com.ying.administrator.footballmessagedemo01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.FixedPagerAdapter;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.CategoriesBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.utils.CategoryDataUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 当前类注释:主界面

 */
public class MainInfoFragment extends BaseFragment implements DefineView,ViewPager.OnPageChangeListener {
    private View mView;
    private TabLayout tab_Layout;
    private ViewPager info_viewpager;
    private List<Fragment> fragments;
    private FixedPagerAdapter fixedPagerAdapter;
    private static List<CategoriesBean> categoriesBeans = CategoryDataUtils.getCategoryBeans();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null){
            mView = inflater.inflate(R.layout.main_info_fragment_layout,container,false);
            initView();
            initValidata();
            initListener();
            bindData();
        }
        return mView;
    }

    @Override
    public void initView() {
        tab_Layout=mView.findViewById(R.id.tab_layout);
        info_viewpager=mView.findViewById(R.id.info_viewpager);
    }

    @Override
    public void initValidata() {
    fixedPagerAdapter=new FixedPagerAdapter(getChildFragmentManager());
    fixedPagerAdapter.setCategoriesBeans(categoriesBeans);

    fragments=new ArrayList<Fragment>();
    for (int i=0;i<categoriesBeans.size();i++){

        BaseFragment fragment =null;
        if (i==0){     //i为0时加载主页fragment
            fragment=HomeFragment.newInstance(categoriesBeans.get(i));
        }else {
             fragment=PageFragment.newInstance(categoriesBeans.get(i));
        }
        fragments.add(fragment);
    }
    fixedPagerAdapter.setFragments(fragments);

    info_viewpager.setAdapter(fixedPagerAdapter);
    tab_Layout.setupWithViewPager(info_viewpager);
    tab_Layout.setTabMode(TabLayout.MODE_SCROLLABLE);



    }

    @Override
    public void initListener() {
    info_viewpager.addOnPageChangeListener(this);   // set已经被add取代

    }

    @Override
    public void bindData() {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
