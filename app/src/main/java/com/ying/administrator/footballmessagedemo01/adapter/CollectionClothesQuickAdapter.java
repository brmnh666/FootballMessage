package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.ClothesBean;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;

import java.util.List;

public class CollectionClothesQuickAdapter extends BaseQuickAdapter<ClothesBean,BaseViewHolder> {


    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;

    public CollectionClothesQuickAdapter(List<ClothesBean> data) {
        super(R.layout.item_collection_clothes_layout,data);
        List<ClothesBean> list = data;

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, ClothesBean clothesBean) {


              //图片缓存
        mImageLoader.displayImage(clothesBean.getClothes_cover_picture(), (ImageView) baseViewHolder.getView(R.id.item_clothes_collection_img),options);
        baseViewHolder.setText(R.id.item_clothes_collection_name,clothesBean.getClothes_name())   //标题
                      .setText(R.id.item_clothes_collection_time,"收藏时间: "+clothesBean.getCollection_time()); //收藏时间

baseViewHolder.addOnClickListener(R.id.item_clothes_collection_img).addOnClickListener(R.id.item_clothes_collection_name).addOnClickListener(R.id.item_clothes_collection_time).addOnClickListener(R.id.cv_collection_clothes);
baseViewHolder.addOnLongClickListener(R.id.item_clothes_collection_img).addOnLongClickListener(R.id.item_clothes_collection_name).addOnLongClickListener(R.id.item_clothes_collection_time).addOnLongClickListener(R.id.cv_collection_clothes);


    }
}
