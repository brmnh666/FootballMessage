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
import com.ying.administrator.footballmessagedemo01.activity.ClothesDetailACtivity;
import com.ying.administrator.footballmessagedemo01.activity.DetailsActivity;
import com.ying.administrator.footballmessagedemo01.activity.ShoesDetailsActivity;
import com.ying.administrator.footballmessagedemo01.adapter.CollectionClothesQuickAdapter;
import com.ying.administrator.footballmessagedemo01.adapter.CollectionShoesQuickAdapter;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.ClothesBean;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MycolloectClothesFragment extends BaseFragment implements DefineView {

    private View mView;
    private String username;
    private RecyclerView recyclerView;
    private List<ClothesBean> dataList;
    CollectionClothesQuickAdapter collectionClothesQuickAdapter;

    private FrameLayout collection_clothes_framelayout;
    private LinearLayout mycollection_clothes_empty;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null){
            mView=inflater.inflate(R.layout.fragment_mycollect_clothes,container,false);
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
        String url = Config.LOCALHOST_URL+"/Myservers/MyCollectionClothesServlet?username="+username;

        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                CustomToast.showToast(getContext(),"获取失败,请检查是否开启Tomcat或者IP地址是否设置正确","login_error",3000);
            }

            @Override
            public void requestSuccess(String result) {

                if (!result.isEmpty()){

                    collection_clothes_framelayout.setVisibility(View.GONE);
                    mycollection_clothes_empty.setVisibility(View.GONE);

                    String json=result;
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<ClothesBean>>() {}.getType();

                    dataList=gson.fromJson(json,type);



                    collectionClothesQuickAdapter = new CollectionClothesQuickAdapter(dataList);
                    recyclerView.setAdapter(collectionClothesQuickAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));


                     Log.d("dataList",dataList.toString());
                    // collectionNewsQuickAdapter = new CollectionNewsQuickAdapter(dataList);
                    initListener();


                }else {//用户还未收藏
                    collection_clothes_framelayout.setVisibility(View.VISIBLE);
                    mycollection_clothes_empty.setVisibility(View.VISIBLE);

                }


            }
        });


    }

    @Override
    public void initView() {
        recyclerView=mView.findViewById(R.id.recycler_collection_clothes_view);
        collection_clothes_framelayout=mView.findViewById(R.id.collection_clothes_framelayout);
        mycollection_clothes_empty=mView.findViewById(R.id.mycollection_clothes_empty);



    }

    @Override
    public void initValidata() {
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        //Log.d("clothes",username);
    }

    @Override
    public void initListener() {
        collectionClothesQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_clothes_collection_img:
                    case R.id.item_clothes_collection_name:
                    case R.id.item_clothes_collection_time:
                    case R.id.cv_collection_clothes:

                        List<ClothesBean> list=new ArrayList<>(); //用于存放发送到详情页的数据
                        list.add((ClothesBean) adapter.getData().get(position)); //将点击位置的数据传给list
                        Intent intent =new Intent(getActivity(),ClothesDetailACtivity.class);

                        intent.putExtra("clothes_list", (Serializable) list);
                        intent.putExtra("username",username);
                        //Log.d("qwe","点击了");
                        startActivity(intent);
                        break;

                }

            }
        });

        collectionClothesQuickAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){

                    case R.id.item_clothes_collection_img:
                    case R.id.item_clothes_collection_name:
                    case R.id.item_clothes_collection_time:
                    case R.id.cv_collection_clothes:

                        //将数据库中的信息删除
                        String url = Config.LOCALHOST_URL+"/Myservers/CancelCollectionClothesServlet?username="+username+"&clothes_id="+((ClothesBean) adapter.getData().get(position)).getClothes_id();


                       // Log.d("2222222222",((ClothesBean) adapter.getData().get(position)).getClothes_id());
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
                        collectionClothesQuickAdapter.remove(position);


                        //数据为空时显示提示
                        if (dataList.isEmpty()){
                            collection_clothes_framelayout.setVisibility(View.VISIBLE);
                            mycollection_clothes_empty.setVisibility(View.VISIBLE);

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
