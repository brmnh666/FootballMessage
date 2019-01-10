package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.widget.CustomVideoView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 当前类注释:启动欢迎界面

 */
public class WelcomeActivity extends BaseActivity implements DefineView,View.OnClickListener {
    private int recLen=6; //倒计时5秒
    private TextView tv_skip;
    Timer timer =new Timer();
    private Handler handler;
    private Runnable runnable;
    private CustomVideoView welcome_videoview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        initView();
        initValidata();
        initListener();

        welcome_videoview.setVideoURI(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.foot));
        welcome_videoview.start();




        timer.schedule(task,0,1000);//等待时间0秒 停顿时间1秒

        /**
         * 正常情况下不点击跳过
         */
        handler =new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {

                if(welcome_videoview.isPlaying()){
                    welcome_videoview.stopPlayback();
                    welcome_videoview=null;
                }

                /*Intent intent =new Intent(WelcomeActivity.this,MainActivity.class);
               startActivity(intent);
               finish();*/

                /*调转到登录界面*/
                Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },5000); //延时5秒后发送信息


    }


    @Override
    public void initView() {
    tv_skip=findViewById(R.id.tv_skin);
   welcome_videoview = findViewById(R.id.welcome_videoview);
    }

    @Override
    public void initValidata() {
        welcome_videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f,0f);  //设置静音
            }
        });
        //设置循环
        welcome_videoview.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {


                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        welcome_videoview.start();

                    }
                });
    }

    @Override
    public void initListener() {
        tv_skip.setOnClickListener(this);
    }

    @Override
    public void bindData() {

    }

    TimerTask task= new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { //主线程
                @Override
                public void run() {
                    recLen--;
                    tv_skip.setText("跳过 "+recLen);
                    if (recLen<0){
                        timer.cancel();
                        tv_skip.setVisibility(View.GONE); //倒计时到0隐藏字体
                    }
                }
            });

        }
    };

    /*
     * 点击跳过
     *
      *  */
          @Override
        public void onClick(View view) {
       switch (view.getId()){

           case R.id.tv_skin:

               if(welcome_videoview.isPlaying()){
                   welcome_videoview.stopPlayback();
                   welcome_videoview=null;
               }


             /*  //从闪屏也跳到 主页面
               Intent intent =new Intent(WelcomeActivity.this,MainActivity.class);
               startActivity(intent);
               finish();*/

               /*调转到登录界面*/
               Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
               startActivity(intent);
               finish();



               if (runnable!=null){

                   handler.removeCallbacks(runnable);
               }

               break;
               default:
               break;
       }
    }
}
