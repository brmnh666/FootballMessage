package com.ying.administrator.footballmessagedemo01.utils;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/*
* OKHttpManager 工具类封装
* */
public class OkHttpManger {
    //OKhttp对象实例
    private OkHttpClient client;
    private static OkHttpManger okhttpManager;

  private Handler handler;
    private static OkHttpManger getInstance(){
        if(okhttpManager==null){
            okhttpManager=new OkHttpManger();
        }
        return  okhttpManager;
    }
    private OkHttpManger(){
        client=new OkHttpClient();
        handler=new Handler(Looper.getMainLooper());
    }




    /**
     * 进行GET异步请求
     * @param url
     * @param callBack
     */
    private void p_getAsync(String url, final DataCallBack callBack){
        final Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverFailure(request, e, callBack);
            }
            /**
             * 异步返回数据
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    deliverSuccess(response.body().string(), callBack);
                }catch (IOException e){
                    deliverFailure(request,e,callBack);
                }
            }
        });
    }



    //*************************数据请求成功或者失败分发方法**********************
    /**
     * 进行分发请求失败的数据情况
     * @param request
     * @param e
     * @param callBack
     */
    private void deliverFailure(final Request request,final IOException e, final DataCallBack callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(request, e);
                }
            }
        });
    }

    /**
     * 请求分发请求成功的数据情况
     * @param result
     * @param callBack
     */
    private void deliverSuccess(final String result, final DataCallBack callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestSuccess(result);
                }
            }
        });
    }

    //*************对外公布的方法********************



    /**
     * 进行GET异步请求数据
     * @param url
     * @param callBack
     */
    public static void getAsync(String url,DataCallBack callBack){
        getInstance().p_getAsync(url, callBack);
    }




    //*************数据回调接口************************
    public interface DataCallBack{
        /**
         * 请求失败
         * @param request
         */
        void requestFailure(Request request,Exception e);

        /**
         * 请求成功
         * @param result
         */
        void requestSuccess(String result);
    }
}
