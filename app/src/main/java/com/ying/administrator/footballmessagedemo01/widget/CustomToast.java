package com.ying.administrator.footballmessagedemo01.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ying.administrator.footballmessagedemo01.R;

import java.util.Timer;
import java.util.TimerTask;


public class CustomToast {
    private  static Toast toast=null;


    public static void showToast(Context context, String text,String type,final int time){
        final Timer timer =new Timer();
        View layout =View.inflate(context, R.layout.custom_toast,null);
        TextView toast_text = layout.findViewById(R.id.toast_text);

        toast_text.setText(text);
        if (type.equals("login_success")){
         toast_text.setBackgroundResource(R.color.mask_tags_11);

        }else if (type.equals("login_error")){
            toast_text.setBackgroundResource(R.color.mask_tags_19);
        }else if (type.equals("register_success")){
            toast_text.setBackgroundResource(R.color.mask_tags_13);

        }else if (type.equals("register_error")){
            toast_text.setBackgroundResource(R.color.mask_tags_19);
        }else {
            toast_text.setBackgroundResource(R.color.color_black);
        }

        if (toast==null){
            toast =new Toast(context);
            //设置toast文本 把设置好的布局传来
            toast.setView(layout);
            //设置吐司显示在屏幕的位置
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.TOP,0,130);

        }else {
            toast.setView(layout);

        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, time);

    }




}
