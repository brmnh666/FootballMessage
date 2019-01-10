package com.ying.administrator.footballmessagedemo01.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.TacitcsBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.entity.UserBean;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.util.ArrayList;
import java.util.List;

public class PractiseTacticsDetailActivity extends BaseActivity implements DefineView {
    private Button btn_back;
    private TextView top_title;
    List<TacitcsBean> list;
    private TextView tactics_name_detail;
    private ImageView tactics_img_detail;
    TacitcsBean tacitcsBean = new TacitcsBean();

    private WebView practise_tactics_webview;
    private  Button practise_tactics_btn;
    private String username;

    private String read_time;
    private boolean istime=false;
    private Handler handler;
    private Runnable runnable;


    private String practise_tactics;//这个训练提供的能力值
    private String practise_worth;//这个训练提供的身价值

    private String before_tactics; //获得原始数值
    private String before_worth; //获得原始数值

    int tactics=0;
    int worth=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise_tactics_detail);
        initView();
        initValidata();
        initListener();
        bindData();

    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        tactics_name_detail=findViewById(R.id.tactics_name_detail);
        tactics_img_detail=findViewById(R.id.tactics_img_detail);
        practise_tactics_webview=findViewById(R.id.practise_tactics_webview);
        practise_tactics_btn=findViewById(R.id.practise_tactics_btn);

    }

    @Override
    public void initValidata() {

        top_title.setText("战术");

        username = getIntent().getStringExtra("username");
        Log.d("detail",username);


        /*获取战术详情*/

        list=new ArrayList<>();
        list = (ArrayList<TacitcsBean>) getIntent().getSerializableExtra("tacitcs_list");
        tacitcsBean=list.get(0);


        tactics_name_detail.setText(tacitcsBean.getTactics_name());

        /*获得该训练获得的能力值*/
        practise_tactics=tacitcsBean.getTactics();
        practise_worth=tacitcsBean.getWorth();

        /*设置观看时间*/

        read_time=tacitcsBean.getMin_readtime();
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

                before_tactics=userBean.getTactics();
                before_worth=userBean.getWorth();

                //Log.d("sw",skill+" "+worth);
                Log.d("sw",practise_tactics+" "+practise_worth);
            }

        });

        Glide.with(getBaseContext()).load(tacitcsBean.getTactics_img()).into(tactics_img_detail);

        practise_tactics_webview.setWebChromeClient(new MyWebChromeClient());
        practise_tactics_webview.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings= practise_tactics_webview.getSettings();

        webSettings.setJavaScriptEnabled(true);//开启js
        webSettings.setDomStorageEnabled(true);//开启dom
        webSettings.setDefaultTextEncodingName("utf-8");//默认编码
        //web页面处理
        webSettings.setAllowFileAccess(true);//支持文件流
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        webSettings.setBlockNetworkImage(false);
        //开启缓存机制
        webSettings.setAppCacheEnabled(true);



    }

    @Override
    public void initListener() {

        btn_back.setOnClickListener(new CustomOnClickListener());
        practise_tactics_btn.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {
        //String html="http://10.152.22.205:8080"; //连接tomcat
        practise_tactics_webview.loadUrl(Config.LOCALHOST_URL+tacitcsBean.getTactics_url());


    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PractiseTacticsDetailActivity.this);
                    dialog.setMessage("您是否要退出？");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            PractiseTacticsDetailActivity.this.finish();
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
                case R.id.practise_tactics_btn:
                         if (istime){
                             CustomToast.showToast(getApplicationContext(),"阅读完成","register_success",2000);
                             tactics = Integer.parseInt(before_tactics)+Integer.parseInt(practise_tactics);
                             worth = Integer.parseInt(before_worth)+Integer.parseInt(practise_worth);

                             /* 将数值添加到数据库*/

                             String url = Config.LOCALHOST_URL+"/Myservers/PractiseTacticsServlet?username="+username+"&tactics="+tactics+"&worth="+worth;

                             OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                                 @Override
                                 public void requestFailure(Request request, Exception e) {
                                     Log.d("error","请检查是否开启Tomcat");
                                 }

                                 @Override
                                 public void requestSuccess(String result) {

                                 }

                             });
                         }else {

                             CustomToast.showToast(getApplicationContext(),"请认真阅读","register_error",2000);
                         }

                    break;
                    default:
                        break;

            }
        }

    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.d("aa","加载进度发生变化:"+newProgress);
        }
    }
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("aa","网页开始加载:"+url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {   //在页面加载完成后执行js隐藏掉不需要的标签
            super.onPageFinished(view, url);
            Log.d("aa","网页加载完成..."+url);

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d("aa","加载的资源:"+url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("aa","拦截到URL信息为:"+url);
            return super.shouldOverrideUrlLoading(view, url);

        }
    }
}
