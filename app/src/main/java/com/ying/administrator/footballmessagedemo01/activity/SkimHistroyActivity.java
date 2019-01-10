package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.SkimNewsQuickAdapter;
import com.ying.administrator.footballmessagedemo01.application.MyApplication;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.crawl.HomeNewsManager;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.SkimNewsBean;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SkimHistroyActivity extends BaseActivity implements DefineView {

    private Button skim_news_btn_back;
    private TextView skim_news_top_title;
    private TextView skim_news_top_clean;

    private String username;
    private RecyclerView recyclerView;
    private FrameLayout skim_news_framelayout;
    private LinearLayout skim_news_empty;
    private TextView myskim_hint;

    private Handler handler;
    private Runnable runnable;
     Context context;
    SkimNewsQuickAdapter skimNewsQuickAdapter;
    private List<SkimNewsBean> dataList;

    private SwipeRefreshLayout skim_swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skimhistroy);
        initView();
        initValidata();
        setDate();
        bindData();
    }

    private void setDate() {
        //初始化数据源

        if (dataList==null){
            dataList = new ArrayList<>();


        }else {
            dataList.clear();

        }
        String url = Config.LOCALHOST_URL+"/Myservers/MySkimNewsServlet?username="+username;

        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {

                CustomToast.showToast(getApplicationContext(),"获取失败,请检查是否开启Tomcat或者IP地址是否设置正确","login_error",3000);
            }

            @Override
            public void requestSuccess(String result) {

                if (!result.isEmpty()){


                    String json=result;
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<SkimNewsBean>>() {}.getType();

                    dataList=gson.fromJson(json,type);

                    skimNewsQuickAdapter=new SkimNewsQuickAdapter(dataList);
                    recyclerView.setAdapter(skimNewsQuickAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    initListener();
                }else {

                   // CustomToast.showToast(getApplicationContext(),"还没有浏览历史","login_error",3000);

                    skim_news_btn_back.setOnClickListener(new CustomOnClickListener());
                    skim_news_framelayout.setVisibility(View.VISIBLE);
                    skim_news_empty.setVisibility(View.VISIBLE);



                }
            }
        });

    }

    @Override
    public void initView() {
        skim_news_btn_back=findViewById(R.id.skim_news_btn_back);
        skim_news_top_title=findViewById(R.id.skim_news_top_title);
        recyclerView=findViewById(R.id.recycler_skim_news_view);
        skim_news_framelayout=findViewById(R.id.skim_news_framelayout);
        skim_news_empty=findViewById(R.id.skim_news_empty);
        skim_news_top_clean=findViewById(R.id.skim_news_top_clean);
        myskim_hint=findViewById(R.id.myskim_hint);
        skim_swipeRefreshLayout=findViewById(R.id.skim_swipeRefreshLayout);
    }

    @Override
    public void initValidata() {
        skim_news_top_title.setText("浏览历史");
         username = getIntent().getStringExtra("username");
         //  Log.d("news", this.username);
        handler=new Handler();
        handler.postDelayed(runnable = new Runnable() { //3秒后提示隐藏
            @Override
            public void run() {
                myskim_hint.setVisibility(View.GONE);
            }
        },3000);


        skim_swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE); //设置背景
        //设置进度条的颜色
        skim_swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);

        skim_swipeRefreshLayout.setProgressViewOffset(false,0,100);

    }

    @Override
    public void initListener() {
        skim_news_btn_back.setOnClickListener(new CustomOnClickListener());

        skimNewsQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_skim_news_time:
                    case R.id.item_skim_news_title:

                        Intent intent =new Intent(SkimHistroyActivity.this,DetailsActivity.class);

                        intent.putExtra("username",username);
                        intent.putExtra("news_href",((SkimNewsBean)adapter.getData().get(position)).getWeb_url());
                        intent.putExtra("thumb",((SkimNewsBean) adapter.getData().get(position)).getThumb()); //图片地址
                        intent.putExtra("title",((SkimNewsBean) adapter.getData().get(position)).getTitle());//新闻标题
                        intent.putExtra("display_time",((SkimNewsBean) adapter.getData().get(position)).getDisplay_time());//新闻发布时间


                        startActivity(intent);

                        break;


                        default:
                            break;


                }

            }
        });


        skim_news_top_clean.setOnClickListener(new CustomOnClickListener());



        OnItemDragListener onItemDragListener=new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
           Log.d("drag","开始拖动");
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.d("drag","拖动中");
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d("drag","拖动结束");
            }
        };
        OnItemSwipeListener onItemSwipeListener=new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d("swipe","开始滑动");
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d("swipe","清除view");

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                //Log.d("swipe","onItemSwiped");

               // Log.d("swipedata",dataList.get(pos).getWeb_url());

                //将数据库中的信息删除


                       skim_news_framelayout.setVisibility(View.GONE);
                       skim_news_empty.setVisibility(View.GONE);

                       String url = Config.LOCALHOST_URL + "/Myservers/DeleteSkimNewsServlet?username=" + username + "&web_url=" + dataList.get(pos).getWeb_url();


                       OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                           @Override
                           public void requestFailure(Request request, Exception e) {
                               CustomToast.showToast(getApplicationContext(), "请检查是否开启Tomcat或者IP地址是否设置正确", "cancel_error", 1000);
                           }

                           @Override
                           public void requestSuccess(String result) {
                               if (result.equals("1")) {
                                //   CustomToast.showToast(getApplicationContext(), "删除成功", "cancel_success", 500);

                               } else {
                                   CustomToast.showToast(getApplicationContext(), "删除失败", "cancel_error", 1000);
                               }
                           }
                       });
                       skimNewsQuickAdapter.remove(pos); //清除浏览历史
                   if (dataList.isEmpty()){
                   skim_news_framelayout.setVisibility(View.VISIBLE);
                   skim_news_empty.setVisibility(View.VISIBLE);

                  }

                   }




            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                Log.d("swipe","滑动中");
            }
        };
        ItemDragAndSwipeCallback itemDragAndSwipeCallback =new ItemDragAndSwipeCallback(skimNewsQuickAdapter);

        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //开始拖拽
        skimNewsQuickAdapter.enableDragItem(itemTouchHelper,R.id.ll_skim,true);
        skimNewsQuickAdapter.setOnItemDragListener(onItemDragListener);

        //开启滑动删除
        skimNewsQuickAdapter.enableSwipeItem();
        skimNewsQuickAdapter.setOnItemSwipeListener(onItemSwipeListener);


    }

    @Override
    public void bindData() {
        //下拉刷新 更新时间
        skim_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (skim_swipeRefreshLayout.isRefreshing()){
                            skim_swipeRefreshLayout.setRefreshing(false);
                        }

                        setDate();
                        /*     下拉刷新加载        */

                    }
                },1000);
            }
        });




    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.skim_news_btn_back:
                    SkimHistroyActivity.this.finish();
                    break;

                  case (R.id.skim_news_top_clean):
                /*      AlertDialog.Builder dialog = new AlertDialog.Builder(SkimHistroyActivity.this);
                      dialog.setMessage("您是否要清空历史记录？");
                      dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {

                              String url = Config.LOCALHOST_URL + "/Myservers/DeleteAllSkimNewsServlet?username=" + username;


                              OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                                  @Override
                                  public void requestFailure(Request request, Exception e) {
                                      CustomToast.showToast(getApplicationContext(), "请检查是否开启Tomcat或者IP地址是否设置正确", "cancel_error", 1000);
                                  }

                                  @Override
                                  public void requestSuccess(String result) {
                                      if (result.equals("1")) {
                                          //   CustomToast.showToast(getApplicationContext(), "删除成功", "cancel_success", 500);

                                      } else {
                                          CustomToast.showToast(getApplicationContext(), "删除失败", "cancel_error", 1000);
                                      }
                                  }
                              });
                              for (int j=0;j<dataList.size();j++){
                              skimNewsQuickAdapter.remove(0);//清除浏览历史
                              skimNewsQuickAdapter.notifyItemChanged(0);
                              }



                             // setDate();

                          }
                      });

                      dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {

                          }
                      });
                      dialog.show();*/
                   break;

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDate();
    }
}
