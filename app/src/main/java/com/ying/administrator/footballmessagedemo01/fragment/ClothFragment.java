package com.ying.administrator.footballmessagedemo01.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.activity.ClothesActivity;
import com.ying.administrator.footballmessagedemo01.activity.ShoesActivity;

import java.util.ArrayList;

import banner.CustomBanner;

public class ClothFragment extends BaseFragment implements DefineView {
    private View mView;
   private CustomBanner<String> banner;
     private  String username;

   private CardView clothfragment_shoes,clothfragment_clothes;
   private ImageView clothfragment_shoes_img,clothfragment_clothes_img;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
       // return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cloth, container, false);
      if (mView==null){

          mView = inflater.inflate(R.layout.fragment_cloth,container,false);
          initView();
          initValidata();
          initListener();
          bindData();
          initValidataBanner();
      }
      return  mView;



    }



    @Override
    public void initView() {
     banner=mView.findViewById(R.id.custombanner_clothing);
        clothfragment_shoes=mView.findViewById(R.id.clothfragment_shoes);
        clothfragment_clothes=mView.findViewById(R.id.clothfragment_clothes);
        clothfragment_shoes_img=mView.findViewById(R.id.clothfragment_shoes_img);
        clothfragment_clothes_img=mView.findViewById(R.id.clothfragment_clothes_img);

    }

    @Override
    public void initValidata() {
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        Log.d("fragmentusername",username);
    }

    @Override
    public void initListener() {

        initValidataBanner();
        clothfragment_shoes.setOnClickListener(new CustomOnClickListener());
        clothfragment_clothes.setOnClickListener(new CustomOnClickListener());
        clothfragment_shoes_img.setOnClickListener(new CustomOnClickListener());
        clothfragment_clothes_img.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {

    }



    public void initValidataBanner(){
        ArrayList<String> images = new ArrayList<>();
        images.add("http://www.ouou.cn/uploadfile/2018/1007/20181007095717131.jpg");
        images.add("http://bbsimg.ouou.cn/Mon_1808/2_84933_5d9c2eeaff0431d.jpg");
        images.add("http://www.ouou.cn/uploadfile/2018/1002/20181002014058772.jpg");
        setBean(images);
    }

    //轮播图配置

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


    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.clothfragment_shoes:
                case R.id.clothfragment_shoes_img:
                    Intent intent_shoes=new Intent(getActivity(),ShoesActivity.class);
                    intent_shoes.putExtra("username",username);
                     startActivity(intent_shoes);
                    break;


                case R.id.clothfragment_clothes:
                case R.id.clothfragment_clothes_img:
                    Intent intent_clothes =new Intent(getActivity(), ClothesActivity.class);
                    intent_clothes.putExtra("username",username);
                    startActivity(intent_clothes);
                    break;
                default:
                    break;

            }
        }

    }
}
