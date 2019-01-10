package com.ying.administrator.footballmessagedemo01.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.entity.HeadBean;
import com.ying.administrator.footballmessagedemo01.entity.HomeNewsBean;

import java.util.ArrayList;
import java.util.List;

import banner.CustomBanner;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener {

    private  static final int TYPE_HEAD=2;  //头布局
    private static final int TYPE_ITEM=0; //判断是否是底部
    private static final int TYPE_FOOT=1;
    private Context mContext;
    private LayoutInflater mInflater;
    private CustomBanner<String> banner;
    private ImageLoader mImageLoader;   //图片缓存加载
    private DisplayImageOptions options;
    private List<HomeNewsBean> homeNewsBeans; //新闻列表数据
    private List<HeadBean> headBeans;//顶部轮播栏数据
    View headView;
    public void setHomeNewsBeans(List<HomeNewsBean> HomeNewsBeans) {
        this.homeNewsBeans = HomeNewsBeans;
    }

    public void setHeadBeans(List<HeadBean> headBeans) {
        this.headBeans = headBeans;
    }


    public HomeRecyclerAdapter(Context mContext){
        this.mContext=mContext;
        this.mInflater=LayoutInflater.from(mContext);

        /*图片缓存*/
        options=new  DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //.showImageOnLoading(R.drawable.pictureloding)
                .build();
        mImageLoader=ImageLoader.getInstance();


    }



    private  class HeadViewHolder extends RecyclerView.ViewHolder{


        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

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
        if (viewType==TYPE_HEAD){
           headView =mInflater.inflate(R.layout.banner_layout,parent,false);
           headView.setOnClickListener(this);

           HeadViewHolder headViewHolder =new HeadViewHolder(headView);
           return  headViewHolder;



       }else if (viewType==TYPE_ITEM){
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
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder){
                                                             //第一条是轮播图
            HomeNewsBean homeNewsBean = homeNewsBeans.get(position-1);

            holder.itemView.setTag(homeNewsBean);

            mImageLoader.displayImage(homeNewsBean.getThumb(),((ItemViewHolder)holder).item_news_img,options);

            ((ItemViewHolder)holder).item_news_title.setText(homeNewsBean.getTitle());
            ((ItemViewHolder)holder).item_news_content.setText(homeNewsBean.getTitle());
            ((ItemViewHolder)holder).item_news_time.setText(homeNewsBean.getDisplay_time());
        }else if (holder instanceof FootItemViewHolder){
            //HomeNewsBean homeNewsBean = homeNewsBeans.get(position);
            //上拉加载更多布局

        }else if (holder instanceof  HeadViewHolder){
             banner=headView.findViewById(R.id.custombanner);


            ArrayList<String> images = new ArrayList<>();
            for (int i =0;i<headBeans.size();i++){

                images.add(headBeans.get(i).getImg());        //将图片的地址存入集合中
            }


            setBean(images);

        }

    }

    @Override
    public int getItemCount() {
        return homeNewsBeans == null?0:homeNewsBeans.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }else if (position+1==getItemCount()){
            return  TYPE_FOOT;
        }else {
            return TYPE_ITEM;
        }
    }



    @Override
    public void onClick(View view) {
      if (onItemClickListener!=null){
          onItemClickListener.onItemClick(view,(HomeNewsBean)view.getTag());
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
    //轮播图配置

    //设置普通指示器
    private void setBean(final ArrayList<String> beans) {
        banner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                Glide.with(context).load(entity).into((ImageView) view);
            }
        }, beans)
//                //设置指示器为普通指示器
//                .setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect)
//                //设置指示器的方向
//                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER)
//                //设置指示器的指示点间隔
                .setIndicatorInterval(30)
                //设置自动翻页
                .startTurning(5000);
    }
}
