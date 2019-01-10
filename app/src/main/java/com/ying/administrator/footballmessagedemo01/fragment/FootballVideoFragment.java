package com.ying.administrator.footballmessagedemo01.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.VideoRecyclerAdapter;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.VideoBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.widget.MyVideoView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FootballVideoFragment extends BaseFragment implements DefineView {
    private RecyclerView recyclerView;
    private VideoRecyclerAdapter mAdapter;
    private MyVideoView videoView;
    private View lastView;
    private List<VideoBean> videoBeanList = new ArrayList<>();
    private View mview;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mview==null){
            mview = inflater.inflate(R.layout.fragment_footballvideo,container,false);
            bindData();
            initView();
            initValidata();
            initListener();

        }
        return mview;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause

        }
    }

    @Override
    public void initView() {
        recyclerView = mview.findViewById(R.id.recyclerView);
        mAdapter = new VideoRecyclerAdapter(videoBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        mAdapter.setListener(new VideoRecyclerAdapter.OnClickPlayListener() {
            @Override
            public void onPlayClick(View view, String videoPath) {
                showVideo(view, videoPath);
            }
        });


        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (videoView == null)
                    return;
                View video_FrameLayout = view.findViewById(R.id.item_video_root_fl);
                if (video_FrameLayout != null) {

                    FrameLayout fl = (FrameLayout) video_FrameLayout;
                    if (fl.getChildCount() > 0) {
                        fl.removeAllViews();
                        int position = 0;
                        if (videoView.isPlaying()) {
                            position = videoView.getPosition();
                            videoView.stop();
                        }

                        videoView.start();
                        videoView.seekTo(position);

                    }
                    fl.setVisibility(View.GONE);
                }
                video_FrameLayout = view.findViewById(R.id.item_imageview);
                if (video_FrameLayout != null) {
                    if (video_FrameLayout.getVisibility() != View.VISIBLE) {
                        video_FrameLayout.setVisibility(View.VISIBLE);
                    }
                }
                video_FrameLayout = view.findViewById(R.id.item_image_play);
                if (video_FrameLayout != null) {
                    if (video_FrameLayout.getVisibility() != View.VISIBLE) {
                        video_FrameLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void bindData() {
        //初始化数据源
        if (videoBeanList==null){
            videoBeanList = new ArrayList<>();
        }else {
            videoBeanList.clear();
        }

        String json = getJson("video/Video.json", getContext());

        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("video");
        Gson gson =new Gson();

        //遍历解析json数据
        for (JsonElement videos : jsonArray) {
            //通过反射 得到UserBean.class
            VideoBean bean = gson.fromJson(videos, new TypeToken<VideoBean>() {}.getType());
            videoBeanList.add(bean);

        }



    }


    private void showVideo(View view, final String videoPath) {
        View v;
        removeVideoView();

        if (videoView == null) {
            videoView = new MyVideoView(getContext());

        }
        videoView.stop();
        v = view.findViewById(R.id.item_imageview);

        if (v != null)
        {
            v.setVisibility(View.INVISIBLE); //隐藏
        }

        v = view.findViewById(R.id.item_image_play);

        if (v != null){
            v.setVisibility(View.INVISIBLE);
        }
        v = view.findViewById(R.id.item_video_root_fl);
        if (v != null) {
            v.setVisibility(View.VISIBLE);
            FrameLayout fl = (FrameLayout) v;
            fl.removeAllViews();
            fl.addView(videoView, new ViewGroup.LayoutParams(-1, -1));

            videoView.setVideoPath(videoPath);
            videoView.start();
        }
        lastView = view;
    }

    private void removeVideoView() {
        View v;
        if (lastView != null) {
            v = lastView.findViewById(R.id.item_imageview);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_image_play);
            if (v != null) v.setVisibility(View.VISIBLE);
            v = lastView.findViewById(R.id.item_video_root_fl);
            if (v != null) {
                FrameLayout ll = (FrameLayout) v;
                ll.removeAllViews();
                v.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (videoView != null) {
            videoView.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {  //换到别的fragment 时暂停视频播放
        if (videoView != null) {
            videoView.pause();
        }
        super.onHiddenChanged(hidden);
    }

    /*获取资源文件里的json*/
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
