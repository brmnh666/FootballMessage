package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.adapter.HistroTodayQuickAdapter;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.HistroytoDayBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.utils.Info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
* 历史上的今天
*
* */
public class HistoryToDayActvity extends BaseActivity implements DefineView {

    private Button btn_back;
    private TextView top_title,tv_today;
    private RecyclerView recyclerView;
    private FrameLayout histroytoday_framelayout;
    private LinearLayout histroytoday_empty;
    Context context;
    private List<HistroytoDayBean> dataList; //原始数据源
    private List<HistroytoDayBean> newdataList; //判断时间后的数据源
    private String currentMonth; //当前月份  以选取具体json文件
    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historytoday);
       // setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setData();
        HistroTodayQuickAdapter histroTodayQuickAdapter=new HistroTodayQuickAdapter(newdataList);//适配器
        recyclerView.setAdapter(histroTodayQuickAdapter);
    }

    private void setData() {
      //初始化数据源
        if (dataList==null){
            dataList = new ArrayList<>();
            newdataList=new ArrayList<>();
        }else {
            dataList.clear();
            newdataList.clear();
        }

                 currentMonth=Info.getMonth();  //选择当前月份
             switch (currentMonth){
                 case "01月":
                     fileName="January";
                     break;
                 case "02月":
                     fileName="February";
                     break;
                 case "03月":
                     fileName="March";
                     break;
                 case "04月":
                     fileName="April";
                     break;
                 case "05月":
                     fileName="May";
                     break;
                 case "06月":
                     fileName="June";
                     break;
                 case "07月":
                     fileName="July";
                     break;
                 case "08月":
                     fileName="August";
                     break;
                 case "09月":
                     fileName="September";
                     break;
                 case "10月":
                     fileName="October";
                     break;
                 case "11月":
                     fileName="November";
                     break;
                 case "12月":
                     fileName="December";
                     break;
                     default:
                         break;


             }
        String json = getJson("incident/"+fileName+".json", getApplicationContext());
        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("incident");
        Gson gson =new Gson();

            //遍历解析json数据
        for (JsonElement histroytoDayBean : jsonArray) {
            //通过反射 得到UserBean.class
            HistroytoDayBean bean = gson.fromJson(histroytoDayBean, new TypeToken<HistroytoDayBean>() {}.getType());
            dataList.add(bean);


        }

        String monthAndDay = Info.getMonthAndDay();  //获取当前 几月几号

        for (int i=0;i<dataList.size();i++){
            // Log.d("aa",histroytoDayBeans.get(i).getDate());
            //判断当前日期是否和数据的日期一样
            if (dataList.get(i).getDate().equals(monthAndDay)){

              //  Log.d("aa",dataList.get(i).getDetails());
                   //产生新的数据源
                newdataList.add(dataList.get(i));




            }

        }

        //判断当前数据是否为空
        if (newdataList.isEmpty()){
            histroytoday_framelayout.setVisibility(View.VISIBLE);
            histroytoday_empty.setVisibility(View.VISIBLE);
        }else {//不为null过

            histroytoday_framelayout.setVisibility(View.GONE);
            histroytoday_empty.setVisibility(View.GONE);

        }



    }

    @Override
    public void initView() {
        recyclerView =findViewById(R.id.recycler_historytoday_view);
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        tv_today=findViewById(R.id.tv_today);
        histroytoday_framelayout=findViewById(R.id.histroytoday_framelayout);
        histroytoday_empty=findViewById(R.id.histroytoday_empty);
    }

    @Override
    public void initValidata() {
       top_title.setText("历史上的今天");
       tv_today.setText(Info.getDateAndWeek());  //获取今天时间和星期
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
                    HistoryToDayActvity.this.finish();
                    break;

            }
        }

    }


    /*获取资源文件里的json*/
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
