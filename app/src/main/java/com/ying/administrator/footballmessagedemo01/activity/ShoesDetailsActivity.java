package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.utils.HttpUtil;
import com.ying.administrator.footballmessagedemo01.utils.Info;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import banner.CustomBanner;

public class ShoesDetailsActivity extends BaseActivity implements DefineView {
    private Button btn_back;
    private TextView top_title;
    private CustomBanner<String> banner;
    List<ShoesBean> list;
    ShoesBean getshoesBean =new ShoesBean();
    private TextView shoes_detail_name;
    private TextView shoes_detail_brand;
    private TextView shoes_detail_children;
    private TextView shoes_detail_texture;
    private TextView shoes_detail_place;
    private TextView shoes_detail_color;
    private TextView shoes_detail_yieldly;
    private TextView shoes_detail_level;
    private TextView shoes_detail_type;
    private TextView shoes_detail_weight;
    private TextView shoes_detail_NO;
    private TextView shoes_detail_price;

    private TextView tv_shoes_collection;
    private String shoes_id;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoesdetails);

        initView();
        initValidata();
        initListener();
        bindData();

    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        banner=findViewById(R.id.shoes_detail_custombanner);
        shoes_detail_name=findViewById(R.id.shoes_detail_name);
        shoes_detail_brand=findViewById(R.id.shoes_detail_brand);
        shoes_detail_children=findViewById(R.id.shoes_detail_children);
        shoes_detail_texture=findViewById(R.id.shoes_detail_texture);
        shoes_detail_place=findViewById(R.id.shoes_detail_place);
        shoes_detail_color=findViewById(R.id.shoes_detail_color);
        shoes_detail_yieldly=findViewById(R.id.shoes_detail_yieldly);
        shoes_detail_level=findViewById(R.id.shoes_detail_level);
        shoes_detail_type=findViewById(R.id.shoes_detail_type);
        shoes_detail_weight=findViewById(R.id.shoes_detail_weight);
        shoes_detail_NO=findViewById(R.id.shoes_detail_NO);
        shoes_detail_price=findViewById(R.id.shoes_detail_price);
        tv_shoes_collection=findViewById(R.id.tv_shoes_collection);
    }

    @Override
    public void initValidata() {
        top_title.setText("球鞋详情");
        /*   获取到的数据*/
        list=new ArrayList<>();
        list = (ArrayList<ShoesBean>) getIntent().getSerializableExtra("shoes_list");
        getshoesBean=list.get(0);

        shoes_detail_name.setText(getshoesBean.getShoes_name());
        shoes_detail_brand.setText(getshoesBean.getShoes_brand());
        shoes_detail_children.setText(getshoesBean.getShoes_children());
        shoes_detail_texture.setText(getshoesBean.getShoes_texture());
        shoes_detail_place.setText(getshoesBean.getShoes_place());
        shoes_detail_color.setText(getshoesBean.getShoes_color());
        shoes_detail_yieldly.setText(getshoesBean.getShoes_yieldly());
        shoes_detail_level.setText(getshoesBean.getShoes_level());
        shoes_detail_type.setText(getshoesBean.getShoes_type());
        shoes_detail_weight.setText(getshoesBean.getShoes_weight());
        shoes_detail_NO.setText(getshoesBean.getShoes_NO());
        shoes_detail_price.setText(getshoesBean.getShoes_price());

        shoes_id=getshoesBean.getShoes_id();
        username=getIntent().getStringExtra("username");

                /*设置轮播图*/
        ArrayList<String> images = new ArrayList<>();
        images.add(getshoesBean.getShoes_content_picture_1());
        images.add(getshoesBean.getShoes_content_picture_2());
        images.add(getshoesBean.getShoes_content_picture_3());
        setBean(images);
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        tv_shoes_collection.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {
        Log.d("usernma_shoes_id",username+" "+shoes_id);


    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    ShoesDetailsActivity.this.finish();
                    break;
                case R.id.tv_shoes_collection:
                    /*使用json传递到服务端*/
                    ShoesBean shoesBean=new ShoesBean();
                    shoesBean.setUsername(username);
                    shoesBean.setShoes_id(getshoesBean.getShoes_id());
                    shoesBean.setCollection_time(Info.getDateAndTime());
                    shoesBean.setShoes_name(getshoesBean.getShoes_name());
                    shoesBean.setShoes_brand(getshoesBean.getShoes_brand());
                    shoesBean.setShoes_children(getshoesBean.getShoes_children());
                    shoesBean.setShoes_texture(getshoesBean.getShoes_texture());
                    shoesBean.setShoes_place(getshoesBean.getShoes_place());
                    shoesBean.setShoes_color(getshoesBean.getShoes_color());
                    shoesBean.setShoes_yieldly(getshoesBean.getShoes_yieldly());
                    shoesBean.setShoes_level(getshoesBean.getShoes_level());
                    shoesBean.setShoes_type(getshoesBean.getShoes_type());
                    shoesBean.setShoes_weight(getshoesBean.getShoes_weight());
                    shoesBean.setShoes_NO(getshoesBean.getShoes_NO());
                    shoesBean.setShoes_price(getshoesBean.getShoes_price());
                    shoesBean.setShoes_cover_picture(getshoesBean.getShoes_cover_picture());
                    shoesBean.setShoes_content_picture_1(getshoesBean.getShoes_content_picture_1());
                    shoesBean.setShoes_content_picture_2(getshoesBean.getShoes_content_picture_2());
                    shoesBean.setShoes_content_picture_3(getshoesBean.getShoes_content_picture_3());

                    Log.d("shoesBean",shoesBean.toString());

                    Gson gson =new Gson();
                    Type type = new TypeToken<ShoesBean>(){}.getType();
                    String jsonstr = gson.toJson(shoesBean,type);


                    String url = Config.LOCALHOST_URL+"/Myservers/CollectionShoesServlet";
                    new CollectionShoesTask().execute(url,jsonstr);

                     break;
                    default:
                        break;

            }
        }

    }


    //设置普通指示器
    private void setBean(final ArrayList<String> beans) {
        banner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                Glide.with(context).load(entity).into((ImageView) view);
            }
        }, beans)
//                //设置指示器为普通指示器
//                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)
//                //设置指示器的方向
//                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
//                //设置指示器的指示点间隔
                .setIndicatorInterval(30)
                //设置自动翻页
                .startTurning(5000);
    }


    /*收藏*/
    class CollectionShoesTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            String par  = params[0];
            String jsonstr = params[1];
            URL url = null;
            try {
                url = new URL(par);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String result = HttpUtil.doJsonPost(url,jsonstr);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            int i =Integer.parseInt(result);
            if (i==1){

                // Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                CustomToast.showToast(getApplicationContext(),"收藏成功","collection_success",1000);
                return;
            }else {

                if (i==-1){
                    // Toast.makeText(getApplicationContext(),"对不起,该用户名已经被注册",Toast.LENGTH_SHORT).show();
                    CustomToast.showToast(getApplicationContext(),"该球鞋已经收藏了","collection_error",1000);
                    return;
                }
            }
        }
    }

    /*收藏*/

}
