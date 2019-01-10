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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.ShoesQuickAdapter;
import com.ying.administrator.footballmessagedemo01.adapter.SkillQuickAdapter;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;
import com.ying.administrator.footballmessagedemo01.entity.SkillBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.activity.PractiseSkillDetailActivity;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
* 足球技术训练fragment
*
* */
public class PractiseSkillFragment extends BaseFragment implements DefineView {
    private View mView;

 private RecyclerView recyclerView;
 private List<SkillBean> datalist;
 SkillQuickAdapter skillQuickAdapter;
 Context context;
 private String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null){
            mView=inflater.inflate(R.layout.fragment_practise_skill,container,false);
            initView();
            initValidata();
            setDate();
/*

            skillQuickAdapter=new SkillQuickAdapter(datalist);
            recyclerView.setAdapter(skillQuickAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
*/


         //   initListener();
            bindData();
        }
        return  mView;
    }

    private void setDate() {
        //初始化数据源
        if (datalist==null){
            datalist = new ArrayList<>();

        }else {
            datalist.clear();

        }

      /*  String html=Config.LOCALHOST_URL+"/practise/skill.json";

        OkHttpManger.getAsync(html, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Log.d("error","请打开tomcat服务器");
            }

            @Override
            public void requestSuccess(String result) {
                //先转JsonObject
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                //再转JsonArray 加上数据头
                JsonArray jsonArray = jsonObject.getAsJsonArray("skill");
                Gson gson =new Gson();

                //遍历解析json数据
                for (JsonElement skillBean : jsonArray) {
                    //通过反射 得到UserBean.class
                    SkillBean bean = gson.fromJson(skillBean, new TypeToken<SkillBean>() {}.getType());
                    datalist.add(bean);

                }


            }
        });*/

        String url = Config.LOCALHOST_URL+"/Myservers/SkillServlet";



        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                // Log.d("error","请检查是否开启Tomcat");
                CustomToast.showToast(getContext(),"获取失败,请检查是否开启Tomcat或者IP地址是否设置正确","login_error",3000);
            }

            @Override
            public void requestSuccess(String result) {
                if(!result.isEmpty()){ //返回有数据


                    //保存数据


                    String json=result;
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<SkillBean>>() {}.getType();
                    datalist=gson.fromJson(json,type);

                    skillQuickAdapter=new SkillQuickAdapter(datalist);
                    recyclerView.setAdapter(skillQuickAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    //Log.d("result",result.toString());

                    initListener();



                }else if (result.isEmpty()){ //数据库中还没球鞋


                }

            }
        });






    }

    @Override
    public void initView() {
        recyclerView=mView.findViewById(R.id.recycler_skill_view);
    }

    @Override
    public void initValidata() {

        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");

    }

    @Override
    public void initListener() {
        skillQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_skill_cardview :
                        List<SkillBean> list=new ArrayList<>(); //用于存放发送到详情页的数据
                        list.add((SkillBean) adapter.getData().get(position)); //将点击位置的数据传给list
                        Intent intent =new Intent(getActivity(), PractiseSkillDetailActivity.class);

                        intent.putExtra("skill_list", (Serializable) list);
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
}
