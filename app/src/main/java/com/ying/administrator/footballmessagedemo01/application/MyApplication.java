package com.ying.administrator.footballmessagedemo01.application;

import android.app.Activity;
import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 当前类注释:全局Application类,作为全局数据的配置以及相关参数数据初始化工作
 *
 *
 */
public class MyApplication extends Application{


    private static Map<String,Activity> destoryMap = new HashMap<>();


    private static MyApplication instance=null;
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance=this;
        initImageLoader();
    }
    public static MyApplication getInstance(){
        return instance;
    }



    private void initImageLoader(){
        ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }


    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName,activity);
    }

    /**
     *销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet=destoryMap.keySet();
        for (String key:keySet){
            destoryMap.get(key).finish();
        }
    }



}
