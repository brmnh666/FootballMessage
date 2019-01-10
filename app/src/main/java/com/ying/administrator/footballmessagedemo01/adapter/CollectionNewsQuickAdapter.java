package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;

import java.util.List;

public class CollectionNewsQuickAdapter extends BaseQuickAdapter<MyColloectNewsBean,BaseViewHolder> {


    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;

    public CollectionNewsQuickAdapter(List<MyColloectNewsBean> data) {
        super(R.layout.item_collection_news_layout,data);
        List<MyColloectNewsBean> list = data;

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //.showImageOnLoading(R.drawable.pictureloding)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, MyColloectNewsBean myColloectNewsBean) {


              //图片缓存
        mImageLoader.displayImage(myColloectNewsBean.getThumb(), (ImageView) baseViewHolder.getView(R.id.item_collection_news_img),options);
        baseViewHolder.setText(R.id.item_collection_news_title,myColloectNewsBean.getTitle())   //标题
                      .setText(R.id.item_collection_time,"收藏时间: "+myColloectNewsBean.getCollection_time()); //收藏时间

        baseViewHolder.addOnClickListener(R.id.item_mycloection_new_cardview).addOnClickListener(R.id.item_collection_news_img).addOnClickListener(R.id.item_collection_news_title).addOnClickListener(R.id.item_collection_time);
baseViewHolder.addOnLongClickListener(R.id.item_mycloection_new_cardview).addOnLongClickListener(R.id.item_collection_news_img).addOnLongClickListener(R.id.item_collection_news_title).addOnLongClickListener(R.id.item_collection_time);
    }
}
