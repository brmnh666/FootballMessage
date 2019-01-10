package com.ying.administrator.footballmessagedemo01.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.HomeNewsBean;

import java.util.List;

public class PageRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener {
    private static final int TYPE_ITEM=0; //判断是否是底部
    private static final int TYPE_FOOT=1;
   private Context mContext;
   private LayoutInflater mInflater;
    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;
   private List<HomeNewsBean> homeNewsBeans;  //新闻列表数据


    public void setHomeNewsBeans(List<HomeNewsBean> HomeNewsBeans) {
        this.homeNewsBeans = HomeNewsBeans;
    }
    public PageRecyclerAdapter(Context mContext){
        this.mContext=mContext;
        this.mInflater=LayoutInflater.from(mContext);

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
              //  .showImageOnLoading(R.drawable.pictureloding)
                .build();
        mImageLoader=ImageLoader.getInstance();


    }


    //自定义的ViewHolder  每个item的holder
     private class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView item_news_title; //标题
        private TextView item_news_content;//详情
        private TextView item_news_time; //发布时间
        private ImageView item_news_img; //图片
        public ItemViewHolder(View itemView) {
            super(itemView);

            item_news_content = itemView.findViewById(R.id.item_news_content);
            item_news_title = itemView.findViewById(R.id.item_news_title);
            item_news_time = itemView.findViewById(R.id.item_news_time);
            item_news_img = itemView.findViewById(R.id.item_news_img);
        }



    }

    /**
     * 上拉加载更多进度布局
     */
    private class FootItemViewHolder extends  ViewHolder{

        public FootItemViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       if (viewType==TYPE_ITEM){
           View itemView = mInflater.inflate(R.layout.item_home_news_layout,parent,false);
           itemView.setOnClickListener(this);
           ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
           return itemViewHolder;
       }else if (viewType==TYPE_FOOT){
           View footItemView=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
           footItemView.setOnClickListener(this);
           FootItemViewHolder footItemViewHolder=new FootItemViewHolder(footItemView);
           return footItemViewHolder;
       }
     return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder){
     HomeNewsBean homeNewsBean = homeNewsBeans.get(position);
     holder.itemView.setTag(homeNewsBean);
     mImageLoader.displayImage(homeNewsBean.getThumb(),((ItemViewHolder)holder).item_news_img,options);
      ((ItemViewHolder)holder).item_news_title.setText(homeNewsBean.getTitle());
        ((ItemViewHolder)holder).item_news_content.setText(homeNewsBean.getDescription());
        ((ItemViewHolder)holder).item_news_time.setText(homeNewsBean.getDisplay_time());
        }else if (holder instanceof FootItemViewHolder){
            //HomeNewsBean homeNewsBean = homeNewsBeans.get(position);
          //上拉加载更多布局

        }

    }

    @Override
    public int getItemCount() {
        return homeNewsBeans == null?0:homeNewsBeans.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1==getItemCount()){
            return TYPE_FOOT;
        }else {
            return  TYPE_ITEM;
        }
    }







    public void onClick(View v) {
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(v,(HomeNewsBean) v.getTag());
        }
    }

    //添加ItemClickListener接口
    public interface OnItemClickListener{
        void onItemClick(View view,HomeNewsBean bean);

    }

    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
