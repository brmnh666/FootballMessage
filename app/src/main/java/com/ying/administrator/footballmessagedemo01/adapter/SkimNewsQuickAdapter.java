package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.SkimNewsBean;
import com.ying.administrator.footballmessagedemo01.utils.Info;

import java.util.List;

public class SkimNewsQuickAdapter extends BaseItemDraggableAdapter<SkimNewsBean,BaseViewHolder> {




    public SkimNewsQuickAdapter(List<SkimNewsBean> data) {
        super(R.layout.item_skim_news_layout,data);
        List<SkimNewsBean> list = data;


    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, SkimNewsBean skimNewsBean) {

        baseViewHolder.setText(R.id.item_skim_news_title,skimNewsBean.getTitle())   //标题
                      .setText(R.id.item_skim_news_time, Info.getTimebefore(skimNewsBean.getSkim_time())); //浏览时间

        baseViewHolder.addOnClickListener(R.id.item_skim_news_time).addOnClickListener(R.id.item_skim_news_title);
    }

}
