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
import com.ying.administrator.footballmessagedemo01.adapter.PageRecyclerAdapter;
import com.ying.administrator.footballmessagedemo01.crawl.HomeNewsManager;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.CategoriesBean;
import com.ying.administrator.footballmessagedemo01.entity.HomeNewsBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.activity.DetailsActivity;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;


/*
* 页面fragment
* */
public class PageFragment extends BaseFragment implements DefineView {
    private View mView;
    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;

    List<HomeNewsBean> homeNewsBeans;      // 原始页面的数据
    List<HomeNewsBean> nexthomeNesBeans;    //下拉刷新的数据

    private FrameLayout page_framelayout;
    private LinearLayout loading,empty,error;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView page_recyclerView;
    private PageRecyclerAdapter adpter;

    private SwipeRefreshLayout page_swipeRefreshLayout;

    private int lastItem;
    private boolean isMore=true; //解决上拉重复加载的bug

     private int currentPage=1; //当前页数默认为1

     private  int zhongchaocurrentPage=1; //中超当前页数
     private  int xianqingcurrentPage=1;//闲情当前页数
     private  int zhuanticurrentPage=1; //专题当前页数
     private  int yinchaocurrentPage=1; //英超当前页数
     private  int xijiacurrentPage=1; //西甲当前页数
     private  int dejiacurrentPage=1; //德甲当前页数
     private  int yijiacurrentPage=1; //意甲当前页数

     private String username;

//    private FrameLayout home_framelayout;
//    private LinearLayout empty, error, loading;

  //  private ImageLoader imageLoader;   //uil框架
  //  private DisplayImageOptions options; //显示图片

    public static PageFragment newInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        PageFragment fragment = new PageFragment();
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
        Log.d("aa",categoriesBean.getRel());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment_layout, container, false);
            initView();
            initValidata();
            initListener();

        }
        return mView;

    }

    @Override
    public void initView() {

        page_framelayout=mView.findViewById(R.id.page_framelayout);
        loading=mView.findViewById(R.id.loading);
        empty=mView.findViewById(R.id.empty);
        error=mView.findViewById(R.id.error);
        page_recyclerView=mView.findViewById(R.id.page_recyclerview);
        page_swipeRefreshLayout =mView.findViewById(R.id.page_swipeRefreshLayout);

    }


    @Override
    public void initValidata() {

        //设置page_recyclerView
        page_recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false);
        page_recyclerView.setLayoutManager(linearLayoutManager); //?


        page_swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE); //设置背景
     //设置进度条的颜色
        page_swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);

        page_swipeRefreshLayout.setProgressViewOffset(false,0,100);

         adpter=new PageRecyclerAdapter(getActivity());

        //在请求网络数据时 显示正在加载
        state_loading();

        OkHttpManger.getAsync(categoriesBean.getHref(), new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Log.d("error", "新闻加载失败");
                state_error();
            }

            @Override
            public void requestSuccess(String result) {
                homeNewsBeans = new HomeNewsManager().getHomeNews(Jsoup.parse(result, Config.CRAWLER_URL));
               // Log.d("aa",homeNewsBeans.toString());
                // Log.d("aa",categoriesBean.getHref());
                bindData();
            }
        });




    }


    @Override
    public void initListener() {
        //下拉加载事件监听
        page_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
              @Override
              public void onRefresh() {
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          if (page_swipeRefreshLayout.isRefreshing()){
                              page_swipeRefreshLayout.setRefreshing(false);
                          }
                          //Toast.makeText(getActivity(),"下拉刷新成功",Toast.LENGTH_SHORT).show();

                          state_have_data();
                             /*     下拉刷新加载        */
                          OkHttpManger.getAsync(categoriesBean.getHref(), new OkHttpManger.DataCallBack() {
                              @Override
                              public void requestFailure(Request request, Exception e) {
                                  Log.d("error", "新闻加载失败");
                                  state_error();
                              }

                              @Override
                              public void requestSuccess(String result) {
                                  homeNewsBeans = new HomeNewsManager().getHomeNews(Jsoup.parse(result, Config.CRAWLER_URL));


                                              /*
                                              *
                                              * 下拉刷新判断当前页面,并把当前页面的页数设置为1
                                              *
                                              * */
                                  switch (categoriesBean.getRel()){
                                      case "56":     //中超当前页数
                                          zhongchaocurrentPage=1;
                                          break;
                                      case "37":     //闲情当前页数
                                          xianqingcurrentPage=1;
                                          break;
                                      case "99":       //专题当前页数
                                          zhuanticurrentPage=1;
                                          break;
                                      case "3":        //英超当前页数
                                          yinchaocurrentPage=1;
                                          break;
                                      case "5":        //西甲当前页数
                                          xijiacurrentPage=1;
                                          break;
                                      case "6":         //德甲当前页数
                                          dejiacurrentPage=1;
                                          break;
                                      case "4":         //意甲当前页数
                                          yijiacurrentPage=1;
                                          break;
                                       default:
                                           break;

                                  }


                                  bindData();
                              }
                          });
                          /*     下拉刷新加载        */

                      }
                  },3000);
              }
          });

         /*                  上拉加载更多              */
        page_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastItem+1==linearLayoutManager.getItemCount()){
    if (isMore){     //防止多次重复加载
        isMore=false;


                  /*
                  * 上拉加载时判断当前在那个页面里 再选择那个页面的页数
                  * */
        switch (categoriesBean.getRel()){
            case "56":     //中超当前页数
                currentPage=zhongchaocurrentPage;
                break;
            case "37":     //闲情当前页数
                currentPage=xianqingcurrentPage;
                break;
            case "99":       //专题当前页数
                currentPage=zhuanticurrentPage;
                break;
            case "3":        //英超当前页数
                currentPage=yinchaocurrentPage;
                break;
            case "5":        //西甲当前页数
                currentPage=xijiacurrentPage;
                break;
            case "6":         //德甲当前页数
                currentPage=dejiacurrentPage;
                break;
            case "4":         //意甲当前页数
                currentPage=yijiacurrentPage;
                break;
            default:
                break;

        }


        String html="http://www.dongqiudi.com/archives/"+categoriesBean.getRel()+"?page="+(currentPage+1)+"";

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


                //合并集合 将新的和旧的合并
                homeNewsBeans.addAll(nexthomeNesBeans);
                adpter.notifyDataSetChanged();    //通知adpater更新
                isMore=true;


                /*
                * 判断当前在哪个页面 并且当前页面数加1
                *
                * */
                switch (categoriesBean.getRel()){
                    case "56":     //中超当前页数
                        zhongchaocurrentPage++;
                        break;
                    case "37":     //闲情当前页数
                        xianqingcurrentPage++;
                        break;
                    case "99":       //专题当前页数
                        zhuanticurrentPage++;
                        break;
                    case "3":        //英超当前页数
                        yinchaocurrentPage++;
                        break;
                    case "5":        //西甲当前页数
                        xijiacurrentPage++;
                        break;
                    case "6":         //德甲当前页数
                        dejiacurrentPage++;
                        break;
                    case "4":         //意甲当前页数
                        yijiacurrentPage++;
                        break;
                    default:
                        break;

                }
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


        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");


adpter.setOnItemClickListener(new PageRecyclerAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(View view, HomeNewsBean bean) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("news_href",bean.getWeb_url()); //将地址传给详情页
        intent.putExtra("username",username);
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
            //绑定数据
            adpter.setHomeNewsBeans(homeNewsBeans);
            page_recyclerView.setAdapter(adpter);
        }else {
            //没有数据
            state_empty();
        }
    }


    public void state_empty(){
        page_recyclerView.setVisibility(View.GONE);
        page_framelayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);

    }


    public void  state_error(){
        page_recyclerView.setVisibility(View.GONE);
        page_framelayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }


    public void  state_loading(){
        page_recyclerView.setVisibility(View.GONE);   //隐藏列表
        page_framelayout.setVisibility(View.VISIBLE); //显示 按个状态的父布局
        loading.setVisibility(View.VISIBLE);   //显示正在加载布局
        empty.setVisibility(View.GONE);           //隐藏为空的布局
        error.setVisibility(View.GONE);         //隐藏显示错误信息的布局



    }


    public void  state_have_data(){
        page_recyclerView.setVisibility(View.VISIBLE);
        page_framelayout.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);

    }


}
