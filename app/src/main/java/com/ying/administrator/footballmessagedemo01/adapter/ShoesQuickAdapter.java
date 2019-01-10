package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.ShoesBean;

import java.util.List;

public class ShoesQuickAdapter extends BaseQuickAdapter<ShoesBean,BaseViewHolder> {


    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;

    public ShoesQuickAdapter(List<ShoesBean> data) {
        super(R.layout.item_shoes,data);
        List<ShoesBean> list = data;

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //.showImageOnLoading(R.drawable.pictureloding)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }


    protected void convert(BaseViewHolder baseViewHolder, ShoesBean shoesBean) {


              //图片缓存
        mImageLoader.displayImage(shoesBean.getShoes_cover_picture(), (ImageView) baseViewHolder.getView(R.id.shoes_img),options);
        baseViewHolder.setText(R.id.shoes_name,shoesBean.getShoes_name());   //球鞋名;

        baseViewHolder.addOnClickListener(R.id.cv_shoes).addOnClickListener(R.id.shoes_img);
    }
}
