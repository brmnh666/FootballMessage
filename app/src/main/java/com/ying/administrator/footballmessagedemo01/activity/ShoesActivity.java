package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.CollectionNewsQuickAdapter;
import com.ying.administrator.footballmessagedemo01.adapter.ShoesQuickAdapter;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShoesActivity extends BaseActivity implements DefineView {
    private Button btn_back;
    private TextView top_title;
    private RecyclerView recyclerView;
    Context context;
    private List<ShoesBean> dataList; //原始数据源
    private String username;
    ShoesQuickAdapter shoesQuickAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes);
        initView();
        initValidata();
        setData();
        bindData();




    }

    private void setData() {
           //初始化数据源
        if (dataList==null){
            dataList = new ArrayList<>();
        }else {
            dataList.clear();
        }


        String url = Config.LOCALHOST_URL+"/Myservers/ShoesServlet";



        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                // Log.d("error","请检查是否开启Tomcat");
                CustomToast.showToast(getApplicationContext(),"获取失败,请检查是否开启Tomcat或者IP地址是否设置正确","login_error",3000);
            }

            @Override
            public void requestSuccess(String result) {
                if(!result.isEmpty()){ //返回有数据


                    //保存数据


                    String json=result;
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<ShoesBean>>() {}.getType();
                    dataList=gson.fromJson(json,type);

                    shoesQuickAdapter =new ShoesQuickAdapter(dataList);
                    recyclerView.setAdapter(shoesQuickAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                     Log.d("result",result.toString());

                    initListener();



                }else if (result.isEmpty()){ //数据库中还没球鞋


                }

            }
        });





    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        recyclerView=findViewById(R.id.recycler_shoes_view);
    }

    @Override
    public void initValidata() {
       top_title.setText("球鞋专栏");
         username = getIntent().getStringExtra("username");
         Log.d("shoesactivity",username);
    }

    @Override
    public void initListener() {
      btn_back.setOnClickListener(new CustomOnClickListener());

        shoesQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

              switch (view.getId()){
                  case R.id.cv_shoes :
                  case R.id.shoes_img:
                      List<ShoesBean> list=new ArrayList<>(); //用于存放发送到详情页的数据
                      list.add((ShoesBean) adapter.getData().get(position)); //将点击位置的数据传给list
                      Intent intent =new Intent(ShoesActivity.this,ShoesDetailsActivity.class);
                      intent.putExtra("shoes_list", (Serializable) list);
                      intent.putExtra("username",username);
                      startActivity(intent);
                      break;
                      default:
                      break;

              }
            }
        });
    }

    @Override
    public void bindData() {

    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    ShoesActivity.this.finish();
                    break;

            }
        }

    }




}
