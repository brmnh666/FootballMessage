package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.TacitcsBean;

import java.util.List;

public class TacticsQuickAdapter extends BaseQuickAdapter<TacitcsBean,BaseViewHolder> {

    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;


    public TacticsQuickAdapter( List<TacitcsBean> data) {
        super(R.layout.item_practise_tactics,data);
        List<TacitcsBean> list =data;

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TacitcsBean tacitcsBean) {

        mImageLoader.displayImage(tacitcsBean.getTactics_img(), (ImageView) baseViewHolder.getView(R.id.tactics_img),options);
    baseViewHolder.setText(R.id.tactics_name,tacitcsBean.getTactics_name())
                 .setText(R.id.tactics_content,tacitcsBean.getTactics_content());
baseViewHolder.addOnClickListener(R.id.item_tactics_cardview);
    }
}
