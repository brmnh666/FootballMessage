package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.dl7.player.media.IjkPlayerView;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.SkillBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.entity.UserBean;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.util.ArrayList;
import java.util.List;

public class PractiseSkillDetailActivity extends BaseActivity implements DefineView {
    private Button btn_back;
    private TextView top_title;
    List<SkillBean> list;
    private TextView skill_name_detail;
    SkillBean skillBean=new SkillBean();
    private WebView practise_skill_webview;
    /*视频播放框架*/
    private IjkPlayerView ijkPlayerView;
    private String username;
    private Uri uri;
    private Button practise_skill_btn;


    private String read_time;
    private boolean istime=false;
    private Handler handler;
    private Runnable runnable;

    private String practise_skill;//这个训练提供的能力值
    private String practise_worth;//这个训练提供的身价值

    private String before_skill; //获得原始数值
    private String before_worth; //获得原始数值

    int skill=0;
    int worth=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_practise_skill_detail);
       initView();
       initValidata();
        bindData();
       initListener();


    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        skill_name_detail=findViewById(R.id.skill_name_detail);
        ijkPlayerView=findViewById(R.id.player_view);
        practise_skill_webview=findViewById(R.id.practise_skill_webview);
        practise_skill_btn=findViewById(R.id.practise_skill_btn);
    }

    @Override
    public void initValidata() {
        top_title.setText("技术");

        username = getIntent().getStringExtra("username");
    //    Log.d("detail",username);


        /*获取技术详情*/
        list=new ArrayList<>();
        list = (ArrayList<SkillBean>) getIntent().getSerializableExtra("skill_list");
        skillBean=list.get(0);
        /*标题*/
        skill_name_detail.setText(skillBean.getSkill_name());

        /*获得该训练获得的能力值*/
        practise_skill=skillBean.getSkill();
        practise_worth=skillBean.getWorth();
        /*视频*/

        uri=Uri.parse(Config.LOCALHOST_URL+skillBean.getSkill_video_url());
        ijkPlayerView.init()
                .setVideoPath(uri)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)
                .enableDanmaku()
                .pause();
        /*下面web*/
        practise_skill_webview.loadUrl(Config.LOCALHOST_URL+skillBean.getSkill_content_url());


        /*设置观看时间*/

        read_time=skillBean.getMin_readtime();
        int time= Integer.parseInt(read_time);
        handler=new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                istime=true;
            }
        },time*1000);


        /*获取原本是技能值*/

        String url = Config.LOCALHOST_URL+"/Myservers/SelectServlet?username="+username;

        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Log.d("error","请检查是否开启Tomcat");
            }

            @Override
            public void requestSuccess(String result) {

                /*解析传来的来*/
                Gson gson =new Gson();
                UserBean userBean= gson.fromJson(result,UserBean.class);

                before_skill=userBean.getSkill();
                before_worth=userBean.getWorth();

             //Log.d("sw",skill+" "+worth);
                Log.d("sw",practise_skill+" "+practise_worth);
            }

        });
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        practise_skill_btn.setOnClickListener(new CustomOnClickListener());


    }

    @Override
    public void bindData() {



    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PractiseSkillDetailActivity.this);
                    dialog.setMessage("您是否要退出？");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            PractiseSkillDetailActivity.this.finish();
                            // MyApplication.destoryActivity("MainActivity"); //销毁mianactivity
                        }
                    });

                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.show();

                    break;

                case R.id.practise_skill_btn:

                    if (istime){

                        CustomToast.showToast(getApplicationContext(),"阅读完成","register_success",2000);

                        skill = Integer.parseInt(before_skill)+Integer.parseInt(practise_skill);
                        worth = Integer.parseInt(before_worth)+Integer.parseInt(practise_worth);

                     Log.d("final_skill", String.valueOf(skill));
                     Log.d("final_worth",String.valueOf(worth));


                        /* 将数值添加到数据库*/

                        String url = Config.LOCALHOST_URL+"/Myservers/PractiseSkillServlet?username="+username+"&skill="+skill+"&worth="+worth;

                        OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, Exception e) {
                                Log.d("error","请检查是否开启Tomcat");
                            }

                            @Override
                            public void requestSuccess(String result) {

                            }

                        });
                        /* 将数值添加到数据库*/

                    }else {

                        CustomToast.showToast(getApplicationContext(),"请认真阅读","register_error",2000);
                    }

                    break;
                    default:
                        break;
            }
        }

    }


    /*将视频与activity生命周期进行绑定*/

    @Override
    protected void onResume() {
        super.onResume();
        ijkPlayerView.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();
        ijkPlayerView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkPlayerView.onDestroy();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ijkPlayerView.configurationChanged(newConfig);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ijkPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*监听后退键*/
    @Override
    public void onBackPressed() {


        if (ijkPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        Log.d("guanbi","已关闭");



    }


}
