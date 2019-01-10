package com.ying.administrator.footballmessagedemo01.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.HomeRecyclerAdapter;
import com.ying.administrator.footballmessagedemo01.crawl.HeadManager;
import com.ying.administrator.footballmessagedemo01.crawl.HomeNewsManager;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.CategoriesBean;
import com.ying.administrator.footballmessagedemo01.entity.HeadBean;
import com.ying.administrator.footballmessagedemo01.entity.HomeNewsBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.activity.DetailsActivity;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements DefineView {

    private View mView;
    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;
    List<HomeNewsBean> homeNewsBeans;      //原始数据
    List<HomeNewsBean> nexthomeNesBeans;    //下拉刷新的数据
    //private PullToRefreshListView home_listView;
    private FrameLayout home_framelayout;
    private LinearLayout empty, error, loading;

    private List<HeadBean> headBeans; //顶部轮播实体
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView home_recyclerView;
    private HomeRecyclerAdapter adpter;
    private SwipeRefreshLayout home_swipeRefreshLayout;
    private  int lastItem;

    private boolean isMore=true; //解决上拉重复加载的bug
    private int currentpage=1;  //默认当前第一页


    private String username; //当前用户
    public static HomeFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.home_fragment_layout, container, false);
           // mInflater=LayoutInflater.from(getActivity());
            //headView = mInflater.inflate(R.layout.banner_layout,null);
            initView();
            initValidata();
            initListener();
        }
        return mView;
    }


    @Override
    public void initView() {
        //home_listView = mView.findViewById(R.id.home_listview);
        home_recyclerView=mView.findViewById(R.id.home_recyclerview);
        home_swipeRefreshLayout=mView.findViewById(R.id.home_swipeRefreshLayout);

        empty = mView.findViewById(R.id.empty);
        error = mView.findViewById(R.id.error);
        loading = mView.findViewById(R.id.loading);
        home_framelayout = mView.findViewById(R.id.home_framelayout);
        //banner= (CustomBanner)headView.findViewById(R.id.custombanner);   //获得banner组件
        //将header添加到listview的第一个
        //home_listView.addHeaderView(headView);


        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("username");

        Log.d("main2",username);

    }



    @Override
    public void initValidata() {

        //设置page_recyclerView
        home_recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false);
        home_recyclerView.setLayoutManager(linearLayoutManager); //?

        //state_have_data();
        //设置swipeRefresh
        home_swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE); //设置背景
        //设置进度条的颜色
        home_swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);

        home_swipeRefreshLayout.setProgressViewOffset(false,0,100);


          adpter=new HomeRecyclerAdapter(getActivity());



        //在请求网络数据时 显示正在加载
        state_loading();



        OkHttpManger.getAsync(categoriesBean.getHref(), new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e)
            {
                Log.d("error", "首页新闻加载失败");
            //页面加载失败
                state_error();


            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result,Config.CRAWLER_URL);
                homeNewsBeans = new HomeNewsManager().getHomeNews(document);
                headBeans=new HeadManager().getHeadBeans(document); //轮播图

              //initValidataBanner();//轮播图数据加载

                bindData();
            }
        });

    }

    @Override
    public void initListener() {

   /*
    home_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Intent intent =new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("news_href",homeNewsBeans.get((int)id).getHref()); //将地址传给详情页
        getActivity().startActivity(intent);
    }
});*/


        //下拉加载事件监听
        home_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void run() {
                        if (home_swipeRefreshLayout.isRefreshing()){
                            home_swipeRefreshLayout.setRefreshing(false);
                        }
                        //Toast.makeText(getActivity(),"下拉刷新成功",Toast.LENGTH_SHORT).show();

                        state_have_data();

                        OkHttpManger.getAsync(categoriesBean.getHref(), new OkHttpManger.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, Exception e) {
                                Log.d("error", "新闻加载失败");
                                state_error();
                            }

                            @Override
                            public void requestSuccess(String result) {
                                Document document = Jsoup.parse(result,Config.CRAWLER_URL);
                                homeNewsBeans = new HomeNewsManager().getHomeNews(document);
                                headBeans=new HeadManager().getHeadBeans(document); //轮播图
                                currentpage = 1;           //刷新后 将当前页数恢复成1
                                bindData();
                            }
                        });


                    }
                },3000);
            }
        });

        /*                  上拉加载更多              */
        home_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastItem+1==linearLayoutManager.getItemCount()){
                    if (isMore){     //防止多次重复加载
                        isMore=false;

                        String html="http://www.dongqiudi.com/archives/1?page="+(currentpage+1)+"";    //下一页数据的地址

                        /*        下拉刷新请求数据*/
                        OkHttpManger.getAsync(html, new OkHttpManger.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, Exception e) {
                                Log.d("error","数据请求出错");
                            }

                            @Override
                            public void requestSuccess(String result) {
                                //先转JsonObject
                                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                                //再转JsonArray 加上数据头
                                JsonArray jsonArray = jsonObject.getAsJsonArray("data");

                                Gson gson =new Gson();
                                nexthomeNesBeans = new ArrayList<>();

                                //循环遍历
                                for (JsonElement nextnews:jsonArray){

                                    HomeNewsBean homeNewsBean = gson.fromJson(nextnews,new TypeToken<HomeNewsBean>(){}.getType());
                                    nexthomeNesBeans.add(homeNewsBean);
                                }

                                //合并集合 将新的和旧的数据的合并
                                homeNewsBeans.addAll(nexthomeNesBeans);
                                adpter.notifyDataSetChanged();    //通知adpater更新
                                isMore=true;                //把下拉状态设置为true
                                currentpage++;           //页数加1

                            }
                        });


                    }



                }


            }
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView,dx,dy);
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        /*                         上拉加载更多                       */

/* 点击跳转到详情页*/

        Intent intent = getActivity().getIntent();
         username = intent.getStringExtra("username");

       // Log.d("home",username);


   adpter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {
       @Override
       public void onItemClick(View view, HomeNewsBean bean) {
           Intent intent = new Intent(getActivity(), DetailsActivity.class);
           intent.putExtra("news_href",bean.getWeb_url()); //将地址传给详情页
           intent.putExtra("username",username); //将用户名传给


           intent.putExtra("thumb",bean.getThumb()); //图片地址
           intent.putExtra("title",bean.getTitle());//新闻标题
          // intent.putExtra("description",bean.getDescription());//新闻详情
           intent.putExtra("display_time",bean.getDisplay_time());//新闻发布时间



           startActivity(intent);
       }
   });


    }

    @Override
    public void bindData() {
        if (homeNewsBeans != null) {  //当主页面有数据时
           state_have_data();

               adpter.setHomeNewsBeans(homeNewsBeans);
               adpter.setHeadBeans(headBeans);      //将轮播图数据传给adpter进行组装
               home_recyclerView.setAdapter(adpter);

    }else
     //没数据
    {

        state_empty();

    }
}



    public void state_empty(){
        home_recyclerView.setVisibility(View.GONE);
        home_framelayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);

    }


    public void  state_error(){
        home_recyclerView.setVisibility(View.GONE);
        home_framelayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }


    public void  state_loading(){
        home_recyclerView.setVisibility(View.GONE);   //隐藏列表
        home_framelayout.setVisibility(View.VISIBLE); //显示 按个状态的父布局
        loading.setVisibility(View.VISIBLE);   //显示正在加载布局
        empty.setVisibility(View.GONE);           //隐藏为空的布局
        error.setVisibility(View.GONE);         //隐藏显示错误信息的布局

    }
    public void state_have_data(){ //有数据
        home_recyclerView.setVisibility(View.VISIBLE);
        home_framelayout.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

    }
}
