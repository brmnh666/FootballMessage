package com.ying.administrator.footballmessagedemo01.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.activity.DetailsActivity;
import com.ying.administrator.footballmessagedemo01.adapter.CollectionNewsQuickAdapter;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MycollectNewsFragment extends BaseFragment implements DefineView {

    private View mView;
    private String username;
    private RecyclerView recyclerView;
    private FrameLayout collection_news_framelayout;
   private LinearLayout mycollection_news_empty;
    private List<MyColloectNewsBean> dataList;
    CollectionNewsQuickAdapter collectionNewsQuickAdapter;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null){
            mView=inflater.inflate(R.layout.fragment_mycollect_news,container,false);
            initView();
            initValidata();
            setDate();

            bindData();
        }
        return mView;
    }

    private void setDate() {

        //初始化数据源

        if (dataList==null){
            dataList = new ArrayList<>();

       }else {
            dataList.clear();

       }


        String url = Config.LOCALHOST_URL+"/Myservers/MyCollectionNewsServlet?username="+username;



        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                // Log.d("error","请检查是否开启Tomcat");
                CustomToast.showToast(getContext(),"获取失败,请检查是否开启Tomcat或者IP地址是否设置正确","login_error",3000);
            }

            @Override
            public void requestSuccess(String result) {
                if(!result.isEmpty()){ //返回有数据

                    collection_news_framelayout.setVisibility(View.GONE);
                    mycollection_news_empty.setVisibility(View.GONE);
                    //保存数据

                    //   Log.d("b",result);
                    // Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                    //  CustomToast.showToast(getContext(),"获取成功","login_success",300);

                    String json=result;
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<MyColloectNewsBean>>() {}.getType();

                    dataList=gson.fromJson(json,type);



                    collectionNewsQuickAdapter = new CollectionNewsQuickAdapter(dataList);
                    recyclerView.setAdapter(collectionNewsQuickAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));


                    // Log.d("dataList",dataList.toString());
                    // collectionNewsQuickAdapter = new CollectionNewsQuickAdapter(dataList);
                    initListener();



                }else if (result.isEmpty()){ //用户还未收藏

                   // CustomToast.showToast(getContext(),"用户未收藏","login_error",1000);
                    collection_news_framelayout.setVisibility(View.VISIBLE);
                    mycollection_news_empty.setVisibility(View.VISIBLE);
                }

            }
        });



    }

    @Override
    public void initView() {
       collection_news_framelayout=mView.findViewById(R.id.collection_news_framelayout);
       mycollection_news_empty=mView.findViewById(R.id.mycollection_news_empty);
        recyclerView=mView.findViewById(R.id.recycler_collection_news_view);
    }

    @Override
    public void initValidata() {

        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
      //  Log.d("news",username);



    }

    @Override
    public void initListener() {
        collectionNewsQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()){
                    case R.id.item_mycloection_new_cardview:
                    case R.id.item_collection_news_img:
                    case R.id.item_collection_news_title:
                    case R.id.item_collection_time:

                        Intent intent =new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("username",username);
                        intent.putExtra("news_href", ((MyColloectNewsBean) adapter.getData().get(position)).getWeb_url()); //将地址传给详情页
                        intent.putExtra("thumb",((MyColloectNewsBean) adapter.getData().get(position)).getThumb()); //图片地址
                        intent.putExtra("title",((MyColloectNewsBean) adapter.getData().get(position)).getTitle());//新闻标题
                        // intent.putExtra("description",bean.getDescription());//新闻详情
                        intent.putExtra("display_time",((MyColloectNewsBean) adapter.getData().get(position)).getDisplay_time());//新闻发布时间

                        //Log.d("qwe","点击了");
                        startActivity(intent);
                        break;
                }
            }


        });

        //长按取消收藏
        collectionNewsQuickAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_mycloection_new_cardview:
                    case R.id.item_collection_news_img:
                    case R.id.item_collection_news_title:
                    case R.id.item_collection_time:


                        //将数据库中的信息删除
                        String url = Config.LOCALHOST_URL+"/Myservers/CancelCollectionNewsServlet?username="+username+"&web_url="+((MyColloectNewsBean) adapter.getData().get(position)).getWeb_url();

                        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, Exception e) {
                                CustomToast.showToast(getContext(),"请检查是否开启Tomcat或者IP地址是否设置正确","cancel_error",1000);
                            }

                            @Override
                            public void requestSuccess(String result) {

                                if (result.equals("1")){
                                   // CustomToast.showToast(getContext(),"取消成功","cancel_success",500);
                                }else {
                                    CustomToast.showToast(getContext(),"取消失败","cancel_error",1000);
                                }
                            }
                        });



//                        Log.d("aaaaa", String.valueOf(dataList.size()));
//                        Log.d("aaaaa", String.valueOf(position));
                        collectionNewsQuickAdapter.remove(position);


                        //数据为空时显示提示
                        if (dataList.isEmpty()){
                            collection_news_framelayout.setVisibility(View.VISIBLE);
                            mycollection_news_empty.setVisibility(View.VISIBLE);

                        }
                      // collectionNewsQuickAdapter.notifyItemRemoved(position);



                        break;
                }
                return false;
            }
        });


    }

    @Override
    public void bindData() {



    }
}
