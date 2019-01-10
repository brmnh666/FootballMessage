package com.ying.administrator.footballmessagedemo01.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.HomeNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.VideoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaoqi on 2017/9/27.
 */

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.ViewHolder> {

    private List<VideoBean> mList;

    private OnClickPlayListener listener;

    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;

    public void setListener(OnClickPlayListener listener) {
        this.listener = listener;
    }

    public VideoRecyclerAdapter(List<VideoBean> list) {
        this.mList = list;
        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //  .showImageOnLoading(R.drawable.pictureloding)
                .build();
        mImageLoader= ImageLoader.getInstance();

    }

    public void addVideoBean(VideoBean videoBean) {
        if (videoBean == null)
            return;
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(videoBean);
        notifyDataSetChanged();
    }

    public void addAllVideoBean(List<VideoBean> list) {
        if (list == null) return;
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final VideoBean videoBean = mList.get(position);



        holder.mVideoTitle.setText(videoBean.getTitle());
        holder.mImageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPlayClick(holder.mCardView, videoBean.getVideo_url());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        FrameLayout mVideoRootFl;
        ImageView item_imageview;
        ImageView mImageViewPlay;
        TextView mVideoTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.item_cardview);
            mVideoRootFl =  itemView.findViewById(R.id.item_video_root_fl);
            item_imageview = itemView.findViewById(R.id.item_imageview);
            mImageViewPlay = itemView.findViewById(R.id.item_image_play);
            mVideoTitle=itemView.findViewById(R.id.item_video_title);
        }
    }

    public interface OnClickPlayListener {
        void onPlayClick(View view, String videoPath);
    }
}
