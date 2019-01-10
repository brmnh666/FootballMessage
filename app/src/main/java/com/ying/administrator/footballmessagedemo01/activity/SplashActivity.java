package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.common.DefineView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity implements DefineView,View.OnClickListener {

    private int recLen=6; //倒计时5秒
    private TextView tv_splash_skin;
    Timer timer =new Timer();
    private Handler handler;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initValidata();
        initListener();
        timer.schedule(task,0,1000);//等待时间0秒 停顿时间1秒
        /**
         * 正常情况下不点击跳过
         */
        handler =new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {


                /*调转到登录界面*/
                Intent intent =new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();

            }
        },5000); //延时5秒后发送信息


    }

    @Override
    public void initView() {
        tv_splash_skin=findViewById(R.id.tv_splash_skin);
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        tv_splash_skin.setOnClickListener(this);
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
                    tv_splash_skin.setText("跳过 "+recLen);
                    if (recLen<0){
                        timer.cancel();
                        tv_splash_skin.setVisibility(View.GONE); //倒计时到0隐藏字体
                    }
                }
            });

        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_splash_skin:




                /*调转到登录界面*/
                Intent intent =new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();



                if (runnable!=null){

                    handler.removeCallbacks(runnable);
                }

                break;
            default:
                break;
        }
    }
}
