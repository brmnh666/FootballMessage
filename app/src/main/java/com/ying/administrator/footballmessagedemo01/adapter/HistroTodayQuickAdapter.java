package com.ying.administrator.footballmessagedemo01.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.HistroytoDayBean;
import com.ying.administrator.footballmessagedemo01.utils.Info;

import java.util.List;

public class HistroTodayQuickAdapter extends BaseQuickAdapter<HistroytoDayBean,BaseViewHolder>{


    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;
    public HistroTodayQuickAdapter(List<HistroytoDayBean> data) {
        super(R.layout.item_histroytoday,data);
        List<HistroytoDayBean> list = data;

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //.showImageOnLoading(R.drawable.pictureloding)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, HistroytoDayBean historyToDayBean) {

             /*判断是否有图片没有图片？如果没有图片把imageview隐藏掉*/
        if (historyToDayBean.getImg().isEmpty()){

            baseViewHolder.getView(R.id.construction_detail_iv_img).setVisibility(View.GONE);

        }

              //图片缓存
        mImageLoader.displayImage(historyToDayBean.getImg(), (ImageView) baseViewHolder.getView(R.id.construction_detail_iv_img),options);
        baseViewHolder.setText(R.id.construction_detail_tv_time,historyToDayBean.getYear())   //年份
                      .setText(R.id.construction_detail_tv_data,historyToDayBean.getDetails())//事件
                      .setText(R.id.construction_detail_tv_title,historyToDayBean.getTitle()) //标题
                      .setVisible(R.id.construction_detail_other_line, (Info.isEmptyOrNullString(historyToDayBean.getPosition())
                        || !historyToDayBean.getPosition().equals("1")));// 判断是不是尾，如果是，隐藏;

        // 判断是不是开头，如果是，隐藏开始的线
        View view_TopLine = baseViewHolder.getView(R.id.construction_detail_top_line);
        if ((!Info.isEmptyOrNullString(historyToDayBean.getPosition())&& historyToDayBean.getPosition().equals("0")))
        {
            view_TopLine.setVisibility(View.INVISIBLE);
        }
    }
}
