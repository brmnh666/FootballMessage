package com.ying.administrator.footballmessagedemo01.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.SuggestFeedbackBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.utils.HttpUtil;
import com.ying.administrator.footballmessagedemo01.utils.Info;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class SuggestFeedbackActivity extends BaseActivity implements DefineView {
    private Button btn_back;
     private TextView top_title;
    private static final int MAX_COUNT = 200;  //显示的最大字数
     private EditText et_Content;
     private Button  btn_submit;
     private TextView tv_count;
    private  String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestfeedback);
        // setStatusBar();
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {
     btn_back=findViewById(R.id.btn_back);
     top_title = findViewById(R.id.top_title);
     et_Content=findViewById(R.id.et_content);
     tv_count=findViewById(R.id.text_count);
     btn_submit=findViewById(R.id.btn_submit);
    }

    @Override
    public void initValidata() {
      top_title.setText("意见反馈");

        username = getIntent().getStringExtra("username");

       // Log.d("aaa",username);
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        btn_submit.setOnClickListener(new CustomOnClickListener());
        //对输入框进行监听
        et_Content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_count.setText("剩余字数:"+(MAX_COUNT - editable.length()));
            }
        });
    }

    @Override
    public void bindData() {

    }

    class CustomOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    SuggestFeedbackActivity.this.finish();
                    break;
                case R.id.btn_submit: //按下提交后

               String suggest =et_Content.getText().toString();
               if (suggest.isEmpty()){
                   AlertDialog.Builder dialog = new AlertDialog.Builder(SuggestFeedbackActivity.this);
                   dialog.setMessage("请认真反馈哦");
                   dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   });
                   dialog.show();



               }else {
                   /*使用json传递到服务端*/
                   SuggestFeedbackBean suggestFeedbackBean=new SuggestFeedbackBean();
                   Log.d("aaaa",username);
                   suggestFeedbackBean.setUsername(username);
                   suggestFeedbackBean.setSuggest(suggest);
                   suggestFeedbackBean.setSuggest_time(Info.getDateAndTime());
                   Gson gson =new Gson();
                   Type type = new TypeToken<SuggestFeedbackBean>(){}.getType();
                   String jsonstr = gson.toJson(suggestFeedbackBean,type);

                   String url = Config.LOCALHOST_URL+"/Myservers/SuggestServlet";

                   new SuggestFeedbackTask().execute(url,jsonstr);
               }




                    break;
            }
        }
    }

    class SuggestFeedbackTask extends AsyncTask<String,Integer,String> {

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
                Toast.makeText(getApplicationContext(),"反馈成功",Toast.LENGTH_SHORT).show();


/*
                AlertDialog.Builder dialog = new AlertDialog.Builder(SuggestFeedbackActivity.this);
                dialog.setMessage("感谢您的反馈!是否退出？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SuggestFeedbackActivity.this.finish();
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();*/

                return;
            }else {

                if (i==-1){
                    Toast.makeText(getApplicationContext(),"反馈失败",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

}
