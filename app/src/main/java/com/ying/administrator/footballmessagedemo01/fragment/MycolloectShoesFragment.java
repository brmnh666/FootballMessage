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
import com.ying.administrator.footballmessagedemo01.activity.ShoesActivity;
import com.ying.administrator.footballmessagedemo01.activity.ShoesDetailsActivity;
import com.ying.administrator.footballmessagedemo01.adapter.CollectionNewsQuickAdapter;
import com.ying.administrator.footballmessagedemo01.adapter.CollectionShoesQuickAdapter;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MycolloectShoesFragment extends BaseFragment implements DefineView {

    private View mView;
    private String username;
    private RecyclerView recyclerView;
    private List<ShoesBean> dataList;
    CollectionShoesQuickAdapter collectionShoesQuickAdapter;
    Context context;
    private FrameLayout collection_shoes_framelayout;
    private LinearLayout mycollection_shoes_empty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null){
            mView=inflater.inflate(R.layout.fragment_mycollect_shoes,container,false);
            initView();
            initValidata();
            setDate();
            bindData();
        }
        return mView;
    }

    private void setDate() {

        if (dataList==null){
            dataList = new ArrayList<>();

        }else {
            dataList.clear();

        }
        String url = Config.LOCALHOST_URL+"/Myservers/MyCollectionShoesServlet?username="+username;

        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                CustomToast.showToast(getContext(),"获取失败,请检查是否开启Tomcat或者IP地址是否设置正确","login_error",3000);
            }

            @Override
            public void requestSuccess(String result) {

                if (!result.isEmpty()){

                    collection_shoes_framelayout.setVisibility(View.GONE);
                    mycollection_shoes_empty.setVisibility(View.GONE);

                    String json=result;
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<ShoesBean>>() {}.getType();

                    dataList=gson.fromJson(json,type);



                    collectionShoesQuickAdapter = new CollectionShoesQuickAdapter(dataList);
                    recyclerView.setAdapter(collectionShoesQuickAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));


                    // Log.d("dataList",dataList.toString());
                    // collectionNewsQuickAdapter = new CollectionNewsQuickAdapter(dataList);
                    initListener();


                }else {//用户还未收藏
                    collection_shoes_framelayout.setVisibility(View.VISIBLE);
                    mycollection_shoes_empty.setVisibility(View.VISIBLE);

                }


            }
        });

    }

    @Override
    public void initView() {
    recyclerView=mView.findViewById(R.id.recycler_collection_shoes_view);
        collection_shoes_framelayout=mView.findViewById(R.id.collection_shoes_framelayout);
        mycollection_shoes_empty=mView.findViewById(R.id.mycollection_shoes_empty);

    }

    @Override
    public void initValidata() {
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        Log.d("shoes",username);



    }

    @Override
    public void initListener() {
        //CollectionShoesQuickAdapter collectionShoesQuickAdapter = new CollectionShoesQuickAdapter(dataList);


        collectionShoesQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_collection_shoes_name:
                    case R.id.item_collection_shoes_time:
                    case R.id.item_collection_shoes_img:


                        List<ShoesBean> list=new ArrayList<>(); //用于存放发送到详情页的数据
                        list.add((ShoesBean) adapter.getData().get(position)); //将点击位置的数据传给list
                        Intent intent =new Intent(getActivity(),ShoesDetailsActivity.class);
                        intent.putExtra("shoes_list", (Serializable) list);
                        intent.putExtra("username",username);
                        //Log.d("qwe","点击了");
                        startActivity(intent);
                        break;

                }

            }
        });

        collectionShoesQuickAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_collection_shoes_name:
                    case R.id.item_collection_shoes_time:
                    case R.id.item_collection_shoes_img:

                        //将数据库中的信息删除
                        String url = Config.LOCALHOST_URL+"/Myservers/CancelCollectionShoesServlet?username="+username+"&shoes_id="+((ShoesBean) adapter.getData().get(position)).getShoes_id();

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
                        collectionShoesQuickAdapter.remove(position);


                        //数据为空时显示提示
                        if (dataList.isEmpty()){
                            collection_shoes_framelayout.setVisibility(View.VISIBLE);
                            mycollection_shoes_empty.setVisibility(View.VISIBLE);

                        }


                }

                return false;
            }
        });

    }

    @Override
    public void bindData() {

    }
}
