package com.ying.administrator.footballmessagedemo01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.UserBean;
import com.ying.administrator.footballmessagedemo01.fragment.base.BaseFragment;
import com.ying.administrator.footballmessagedemo01.activity.PractiseActivity;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;

/*
* 练球界面
* */
public class FootballFragment extends BaseFragment implements DefineView {
  private View mview;
  private  ImageView image_skill;
  private  ImageView image_tactics;
  private String username;

private TextView personal_skill;
private TextView personal_worth;
private TextView personal_tactics;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_football, container, false);
      if (mview==null){
          mview = inflater.inflate(R.layout.fragment_football,container,false);
          initView();
          initValidata();
          initListener();
      }
      return mview;
    }

    @Override
    public void initView() {
        image_skill=mview.findViewById(R.id.image_skill);
        image_tactics=mview.findViewById(R.id.image_tactics);
        personal_skill=mview.findViewById(R.id.personal_skill);
        personal_worth=mview.findViewById(R.id.personal_worth);
        personal_tactics=mview.findViewById(R.id.personal_tactics);
    }

    @Override
    public void initValidata() {
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        Log.d("第四个",username);


    }

    @Override
    public void initListener() {
        image_skill.setOnClickListener(new CustomOnClickListener());
       image_tactics.setOnClickListener(new CustomOnClickListener());

    }

    @Override
    public void bindData() {

    }

    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.image_skill:
                    Intent intent_skill=new Intent(getActivity(),PractiseActivity.class);
                    intent_skill.putExtra("target","intent_skill");
                    intent_skill.putExtra("username",username);
                    startActivity(intent_skill);
                    break;
                case R.id.image_tactics:
                    Intent intent_tactics=new Intent(getActivity(),PractiseActivity.class);
                    intent_tactics.putExtra("target","intent_tactics");
                    intent_tactics.putExtra("username",username);
                    startActivity(intent_tactics);
                    break;
                default:
                    break;

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

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

                personal_skill.setText(userBean.getSkill());

                personal_worth.setText(userBean.getWorth());

                personal_tactics.setText(userBean.getTactics());



            }

        });

    }
}
