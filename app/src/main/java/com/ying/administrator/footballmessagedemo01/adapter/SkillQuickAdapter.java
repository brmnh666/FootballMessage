package com.ying.administrator.footballmessagedemo01.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.SkillBean;
import com.ying.administrator.footballmessagedemo01.entity.TacitcsBean;

import java.util.List;

public class SkillQuickAdapter extends BaseQuickAdapter<SkillBean,BaseViewHolder> {

    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;


    public SkillQuickAdapter(List<SkillBean> data) {
        super(R.layout.item_practise_skill,data);
        List<SkillBean> list =data;

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        mImageLoader=ImageLoader.getInstance();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, SkillBean skillBean) {

        mImageLoader.displayImage(skillBean.getSkill_img(), (ImageView) baseViewHolder.getView(R.id.skill_img),options);
    baseViewHolder.setText(R.id.skill_name,skillBean.getSkill_name());

     baseViewHolder.addOnClickListener(R.id.item_skill_cardview);
    }
}
