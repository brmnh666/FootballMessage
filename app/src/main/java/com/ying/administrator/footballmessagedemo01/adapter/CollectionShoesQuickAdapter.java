package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;

import java.util.List;

public class CollectionShoesQuickAdapter extends BaseQuickAdapter<ShoesBean,BaseViewHolder> {


    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;

    public CollectionShoesQuickAdapter(List<ShoesBean> data) {
        super(R.layout.item_collection_shoes_layout,data);
        List<ShoesBean> list = data;

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShoesBean shoesBean) {


              //图片缓存
        mImageLoader.displayImage(shoesBean.getShoes_cover_picture(), (ImageView) baseViewHolder.getView(R.id.item_collection_shoes_img),options);
        baseViewHolder.setText(R.id.item_collection_shoes_name,shoesBean.getShoes_name())   //标题
                      .setText(R.id.item_collection_shoes_time,"收藏时间: "+shoesBean.getCollection_time()); //收藏时间

baseViewHolder.addOnClickListener(R.id.item_collection_shoes_img).addOnClickListener(R.id.item_collection_shoes_name).addOnClickListener(R.id.item_collection_shoes_time);
baseViewHolder.addOnLongClickListener(R.id.item_collection_shoes_img).addOnLongClickListener(R.id.item_collection_shoes_name).addOnLongClickListener(R.id.item_collection_shoes_time);
    }
}
