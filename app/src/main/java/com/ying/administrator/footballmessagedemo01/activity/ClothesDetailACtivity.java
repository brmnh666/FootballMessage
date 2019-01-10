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
import com.ying.administrator.footballmessagedemo01.entity.ClothesBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;
import com.ying.administrator.footballmessagedemo01.utils.HttpUtil;
import com.ying.administrator.footballmessagedemo01.utils.Info;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import banner.CustomBanner;

public class ClothesDetailACtivity extends BaseActivity implements DefineView {

    private Button btn_back;
    private TextView top_title;

    private CustomBanner<String> banner;
    List<ClothesBean> list;
    ClothesBean getclothesBean =new ClothesBean();

    private TextView tv_clothes_collection;
    private String clothes_id;
    private String username;

    private TextView clothes_detail_name;
    private TextView clothes_detail_brand;
    private TextView clothes_detail_yieldly;
    private TextView clothes_detail_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothesdetails);
        initView();
        initValidata();
        initListener();
        bindData();

    }


    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        banner=findViewById(R.id.clothes_detail_custombanner);
        clothes_detail_name=findViewById(R.id.clothes_detail_name);
        clothes_detail_brand=findViewById(R.id.clothes_detail_brand);
        clothes_detail_yieldly=findViewById(R.id.clothes_detail_yieldly);
        clothes_detail_price=findViewById(R.id.clothes_detail_price);
        tv_clothes_collection=findViewById(R.id.tv_clothes_collection);
    }

    @Override
    public void initValidata() {
        top_title.setText("球衣详情");
        /*   获取球衣到的数据*/
        list=new ArrayList<>();
        list = (ArrayList<ClothesBean>) getIntent().getSerializableExtra("clothes_list");
        getclothesBean=list.get(0);
        clothes_detail_name.setText(getclothesBean.getClothes_name());
        clothes_detail_brand.setText(getclothesBean.getClothes_brand());
        clothes_detail_yieldly.setText(getclothesBean.getClothes_yieldly());
        clothes_detail_price.setText(getclothesBean.getClothes_price());

        username=getIntent().getStringExtra("username");
        clothes_id=getclothesBean.getClothes_id();

        /*设置轮播图*/
        ArrayList<String> images = new ArrayList<>();
        images.add(getclothesBean.getClothes_detail_picture1());
        images.add(getclothesBean.getClothes_detail_picture2());
        images.add(getclothesBean.getClothes_detail_picture3());
        setBean(images);
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        tv_clothes_collection.setOnClickListener(new CustomOnClickListener());

    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    ClothesDetailACtivity.this.finish();
                    break;
                case R.id.tv_clothes_collection:
                  ClothesBean clothesBean=new ClothesBean();
                  clothesBean.setUsername(username);
                  clothesBean.setClothes_id(getclothesBean.getClothes_id());
                  clothesBean.setCollection_time(Info.getDateAndTime());
                  clothesBean.setClothes_name(getclothesBean.getClothes_name());
                  clothesBean.setClothes_brand(getclothesBean.getClothes_brand());
                  clothesBean.setClothes_yieldly(getclothesBean.getClothes_yieldly());
                  clothesBean.setClothes_price(getclothesBean.getClothes_price());
                  clothesBean.setClothes_cover_picture(getclothesBean.getClothes_cover_picture());
                  clothesBean.setClothes_detail_picture1(getclothesBean.getClothes_detail_picture1());
                    clothesBean.setClothes_detail_picture2(getclothesBean.getClothes_detail_picture2());
                    clothesBean.setClothes_detail_picture3(getclothesBean.getClothes_detail_picture3());

                    Gson gson =new Gson();
                    Type type = new TypeToken<ClothesBean>(){}.getType();
                    String jsonstr = gson.toJson(clothesBean,type);

                    Log.d("jsonstr",jsonstr);

                    String url = Config.LOCALHOST_URL+"/Myservers/CollectionClothesServlet";

                    new CollectionClothesTask().execute(url,jsonstr);

                    break;

                    default:

                 break;
            }
        }

    }
    @Override
    public void bindData() {

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
    class CollectionClothesTask extends AsyncTask<String,Integer,String> {

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
                    CustomToast.showToast(getApplicationContext(),"该球衣已经收藏了","collection_error",1000);
                    return;
                }
            }
        }
    }

    /*收藏*/

}
