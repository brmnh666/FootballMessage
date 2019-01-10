package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.HomeNewsBean;
import com.ying.administrator.footballmessagedemo01.entity.MyColloectNewsBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.entity.SkimNewsBean;
import com.ying.administrator.footballmessagedemo01.utils.HttpUtil;
import com.ying.administrator.footballmessagedemo01.utils.Info;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsActivity extends BaseActivity implements DefineView {
    private Button btn_back;
    private WebView details_content;
    private HomeNewsBean homeNewsBean;
    private TextView top_title;
    private TextView tv_collection;


    private FrameLayout news_detail_framelayout;
    private LinearLayout news_detail_empty;


   private String href;
   private String username;
    private String thumb;
    private String title;
    private String display_time;


    private String skim_time;// 浏览时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        tv_collection=findViewById(R.id.tv_collection);


         details_content=findViewById(R.id.details_content);
        top_title=findViewById(R.id.top_title);

        news_detail_framelayout=findViewById(R.id.news_detail_framelayout);
        news_detail_empty=findViewById(R.id.news_detail_empty);


    }

    @Override
    public void initValidata() {
        top_title.setText("资讯详情");

        //设置webview
        details_content.setWebChromeClient(new MyWebChromeClient());
        details_content.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings= details_content.getSettings();
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
        btn_back.setOnClickListener(new CustomOnClickListener() );
        tv_collection.setOnClickListener(new CustomOnClickListener());

    }

    @Override
    public void bindData() {
        Intent intent = getIntent();
        href=intent.getStringExtra("news_href");
         details_content.loadUrl(href);

         username = getIntent().getStringExtra("username");
         thumb = getIntent().getStringExtra("thumb");
         title = getIntent().getStringExtra("title");
         //description = getIntent().getStringExtra("description");

        display_time = getIntent().getStringExtra("display_time");

       // Log.d("aaa",username+" "+thumb+" "+title+" "+description+" "+display_time);


        //浏览后保存到数据库
        skim_time=Info.getDateAndTime();  //获取点击后的系统时间
         /*使用json点击后的传递到服务端*/
         SkimNewsBean skimNewsBean=new SkimNewsBean();
         skimNewsBean.setUsername(username);
         skimNewsBean.setTitle(title);
         skimNewsBean.setWeb_url(href);
         skimNewsBean.setSkim_time(skim_time);
         skimNewsBean.setDisplay_time(display_time);
         skimNewsBean.setThumb(thumb);
         Gson gson =new Gson();
         Type type = new TypeToken<SkimNewsBean>(){}.getType();
         String jsonstr = gson.toJson(skimNewsBean,type);
         String url = Config.LOCALHOST_URL+"/Myservers/SkimNewsServlet";
         new SkimNewsTask().execute(url,jsonstr);

    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    DetailsActivity.this.finish();
                    break;

                case R.id.tv_collection:
                    /*使用json传递到服务端*/
                    MyColloectNewsBean myColloectNewsBean=new MyColloectNewsBean();
                    myColloectNewsBean.setUsername(username);
                    myColloectNewsBean.setDisplay_time(display_time);
                    myColloectNewsBean.setTitle(title);
                    //myColloectNewsBean.setDescription(description);
                    myColloectNewsBean.setThumb(thumb);
                    myColloectNewsBean.setWeb_url(href);
                    myColloectNewsBean.setCollection_time(Info.getDateAndTime());
                    Gson gson =new Gson();
                    Type type = new TypeToken<MyColloectNewsBean>(){}.getType();
                    String jsonstr = gson.toJson(myColloectNewsBean,type);


                    String url = Config.LOCALHOST_URL+"/Myservers/CollectionNewsServlet";
                    new CollectionNewsTask().execute(url,jsonstr);


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
            news_detail_framelayout.setVisibility(View.VISIBLE);
            news_detail_empty.setVisibility(View.VISIBLE);
        }
    }
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("aa","网页开始加载:"+url);
            news_detail_framelayout.setVisibility(View.VISIBLE);
            news_detail_empty.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {   //在页面加载完成后执行js隐藏掉不需要的标签
            super.onPageFinished(view, url);
            Log.d("aa","网页加载完成..."+url);

            news_detail_framelayout.setVisibility(View.GONE);
            news_detail_empty.setVisibility(View.GONE);
            //编写 javaScript方法
            String javascript = "javascript:function hideOther() {" +
                        //去掉推荐评论
                    "document.getElementsByClassName('article-foot-recommend')[0].remove();" +
                    "document.getElementsByClassName('slide-hao swiperWrap')[0].remove();" +
                    "}"

                    ;


            //创建方法
            view.loadUrl(javascript);

            //加载方法
           view.loadUrl("javascript:hideOther();");
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


/*收藏*/
    class CollectionNewsTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            String par  = params[0];
            String jsonstr = params[1];
            URL url = null;
            try {
                url = new URL(par);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String result = HttpUtil.doJsonPost(url,jsonstr);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            int i =Integer.parseInt(result);
            if (i==1){

                // Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                CustomToast.showToast(getApplicationContext(),"收藏成功","collection_success",1000);
                return;
            }else {

                if (i==-1){
                    // Toast.makeText(getApplicationContext(),"对不起,该用户名已经被注册",Toast.LENGTH_SHORT).show();
                    CustomToast.showToast(getApplicationContext(),"该资讯已经收藏了","collection_error",1000);
                    return;
                }
            }
        }
    }

    /*收藏*/




    /*浏览*/

    class SkimNewsTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            String par  = params[0];
            String jsonstr = params[1];
            URL url = null;
            try {
                url = new URL(par);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String result = HttpUtil.doJsonPost(url,jsonstr);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            int i =Integer.parseInt(result);
            if (i==1){

                // Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
             //   CustomToast.showToast(getApplicationContext(),"浏览成功","skim_success",1000);
                return;
            }else {

                if (i==0){
                    // Toast.makeText(getApplicationContext(),"对不起,该用户名已经被注册",Toast.LENGTH_SHORT).show();
                //    CustomToast.showToast(getApplicationContext(),"浏览时间已经更新","collection_error",1000);
                    return;
                }
            }
        }
    }
/*浏览*/
}
