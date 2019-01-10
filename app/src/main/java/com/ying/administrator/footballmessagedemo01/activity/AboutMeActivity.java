package com.ying.administrator.footballmessagedemo01.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;

public class AboutMeActivity extends BaseActivity implements DefineView {
   private Button btn_back;
   private TextView top_title;
private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        //setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back); //返回按钮
        top_title=findViewById(R.id.top_title);//顶部标题
    }

    @Override
    public void initValidata() {
     top_title.setText("关于我们");
    }

    @Override
    public void initListener() {
       btn_back.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {

    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    AboutMeActivity.this.finish();
                    break;

            }
        }

    }
}
