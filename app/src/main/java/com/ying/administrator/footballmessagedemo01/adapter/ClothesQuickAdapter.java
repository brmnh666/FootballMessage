package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.ClothesBean;

import java.util.List;

public class ClothesQuickAdapter extends BaseQuickAdapter<ClothesBean,BaseViewHolder> {


    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;

    public ClothesQuickAdapter(List<ClothesBean> data) {
        super(R.layout.item_clothes,data);
        List<ClothesBean> list = data;
        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //.showImageOnLoading(R.drawable.pictureloding)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }


    protected void convert(BaseViewHolder baseViewHolder, ClothesBean clothesBean) {


              //图片缓存
       mImageLoader.displayImage(clothesBean.getClothes_cover_picture(), (ImageView) baseViewHolder.getView(R.id.clothes_img),options);
        baseViewHolder.setText(R.id.clothes_name,clothesBean.getClothes_name());   //球衣名;\
                     /*显示对应的logo*/
           if (clothesBean.getClothes_brand().equals("adidas"))
           {
               baseViewHolder.setVisible(R.id.clothes_brand_adidas, true);

               baseViewHolder.setVisible(R.id.clothes_brand_Nike,false);
               baseViewHolder.setVisible(R.id.clothes_brand_Puma,false);
               baseViewHolder.setVisible(R.id.clothes_brand_New_balance,false);
           }else if (clothesBean.getClothes_brand().equals("Nike")){
               baseViewHolder.setVisible(R.id.clothes_brand_Nike, true);

               baseViewHolder.setVisible(R.id.clothes_brand_adidas,false);
               baseViewHolder.setVisible(R.id.clothes_brand_Puma,false);
               baseViewHolder.setVisible(R.id.clothes_brand_New_balance,false);
           }else if (clothesBean.getClothes_brand().equals("Puma")){
               baseViewHolder.setVisible(R.id.clothes_brand_Puma, true);

               baseViewHolder.setVisible(R.id.clothes_brand_adidas,false);
               baseViewHolder.setVisible(R.id.clothes_brand_Nike, false);
               baseViewHolder.setVisible(R.id.clothes_brand_New_balance,false);
           }else if (clothesBean.getClothes_brand().equals("New balance")){
               baseViewHolder.setVisible(R.id.clothes_brand_New_balance, true);


               baseViewHolder.setVisible(R.id.clothes_brand_adidas,false);
               baseViewHolder.setVisible(R.id.clothes_brand_Puma,false);
               baseViewHolder.setVisible(R.id.clothes_brand_Nike,false);
           }

        baseViewHolder.addOnClickListener(R.id.cv_clothes).addOnClickListener(R.id.clothes_img);


    }
}
