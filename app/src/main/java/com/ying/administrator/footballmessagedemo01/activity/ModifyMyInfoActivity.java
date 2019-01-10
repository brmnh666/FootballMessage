package com.ying.administrator.footballmessagedemo01.activity;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.application.MyApplication;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.UserBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.utils.HttpUtil;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import addresspick.AddressPickerView;

public class ModifyMyInfoActivity extends BaseActivity implements DefineView {
    private Button btn_back;
    private TextView top_title;
     private Button btn_modfiy;
     private Button btn_logout; //注销
    private String username;



    private LinearLayout ll_modify_info_name;
    private LinearLayout ll_modify_info_phone;
    private LinearLayout ll_modify_info_birthday;
    private LinearLayout ll_modify_info_adress;
    private LinearLayout ll_modify_info_sex;

    private TextView tv_modify_info_name;
    private TextView tv_modify_info_phone;
    private TextView tv_modify_info_birthday;
    private TextView tv_modify_info_adress;
    private TextView tv_modify_info_sex;


    private String[] sexArry = new String[]{"保密", "女", "男"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_modify_myinfo);
        initView();
        initValidata();
        initListener();
        bindData();

    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        btn_modfiy=findViewById(R.id.btn_modfiy);
        btn_logout=findViewById(R.id.btn_logout);

        ll_modify_info_name=findViewById(R.id.ll_modify_info_name);
        ll_modify_info_phone=findViewById(R.id.ll_modify_info_phone);
        ll_modify_info_birthday=findViewById(R.id.ll_modify_info_birthday);
        ll_modify_info_adress=findViewById(R.id.ll_modify_info_adress);
        ll_modify_info_sex=findViewById(R.id.ll_modify_info_sex);

        tv_modify_info_name=findViewById(R.id.tv_modify_info_name);
        tv_modify_info_phone=findViewById(R.id.tv_modify_info_phone);
        tv_modify_info_birthday=findViewById(R.id.tv_modify_info_birthday);
        tv_modify_info_adress=findViewById(R.id.tv_modify_info_adress);
        tv_modify_info_sex=findViewById(R.id.tv_modify_info_sex);
    }

    @Override
    public void initValidata() {
         top_title.setText("修改个人信息");
         username = getIntent().getStringExtra("username");


        if (!username.equals("root")){
         //先返回这个用户的所有信息
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

                   Log.d("aaaaa",userBean.toString());
                tv_modify_info_name.setText(userBean.getName());
                tv_modify_info_phone.setText(userBean.getPhone());
                tv_modify_info_birthday.setText(userBean.getBirthday());
                tv_modify_info_adress.setText(userBean.getAdress());
                tv_modify_info_sex.setText(userBean.getSex());




            }

        });

        }else {
            CustomToast.showToast(getApplicationContext(),"root用户登录"," ",2000);

        }

    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        ll_modify_info_name.setOnClickListener(new CustomOnClickListener());
        ll_modify_info_phone.setOnClickListener(new CustomOnClickListener());
        ll_modify_info_birthday.setOnClickListener(new CustomOnClickListener());
        ll_modify_info_adress.setOnClickListener(new CustomOnClickListener());
        ll_modify_info_sex.setOnClickListener(new CustomOnClickListener());
        btn_modfiy.setOnClickListener(new CustomOnClickListener());
        btn_logout.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {

    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    ModifyMyInfoActivity.this.finish();
                    break;
                case R.id.ll_modify_info_name:  //用户名称
                    onCreateNameDialog();
                    break;
                case R.id.ll_modify_info_phone:
                    onCreatePhoneDialog();
                    break;
                case R.id.ll_modify_info_birthday: //设置生日

                    Calendar nowdate = Calendar.getInstance();
                    int mYear = nowdate.get(Calendar.YEAR);
                    int mMonth = nowdate.get(Calendar.MONTH);
                    int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(ModifyMyInfoActivity.this, onDateSetListener, mYear, mMonth, mDay).show();


                    break;

                case R.id.ll_modify_info_adress:
                    showAddressPickerPop();
                    break;
                case R.id.ll_modify_info_sex:
                    showSexChooseDialog();
                    break;

                case R.id.btn_modfiy:
                    /*使用json传递到服务端*/
                    UserBean user =new UserBean();

                    user.setUsername(username);
                    user.setName(String.valueOf(tv_modify_info_name.getText()));
                    user.setPhone(String.valueOf(tv_modify_info_phone.getText()));
                    user.setBirthday(String.valueOf(tv_modify_info_birthday.getText()));
                    user.setAdress(String.valueOf(tv_modify_info_adress.getText()));
                    user.setSex(String.valueOf(tv_modify_info_sex.getText()));


                    Gson gson =new Gson();
                    Type type = new TypeToken<UserBean>(){}.getType();
                    String jsonstr = gson.toJson(user,type);

                    String url = Config.LOCALHOST_URL+"/Myservers/ModifyServlet";
                    new ModifyTask().execute(url,jsonstr);

                    break;

                    case R.id.btn_logout://注销


                    AlertDialog.Builder dialog = new AlertDialog.Builder(ModifyMyInfoActivity.this);
                    dialog.setMessage("您是否要注销用户？");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(ModifyMyInfoActivity.this,LoginActivity.class);
                            startActivity(intent);

                            ModifyMyInfoActivity.this.finish();
                            MyApplication.destoryActivity("MainActivity"); //销毁mianactivity
                        }
                    });

                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.show();
                        break;
                    default:
                        break;

            }
        }

    }


    /*修改名称*/
    private void onCreateNameDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final EditText userInput =  nameView.findViewById(R.id.changename_edit);

        userInput.setText(tv_modify_info_name.getText());
        userInput.setSelection(tv_modify_info_name.getText().toString().length()); //将光标设置在最后

        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                tv_modify_info_name.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("修改用户名");

        // show it
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(20);
    }

    /*修改名称*/






    /*修改电话号*/
    private void onCreatePhoneDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View phoneView = layoutInflater.inflate(R.layout.dialog_setphone, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(phoneView);

        final EditText userInput =  phoneView.findViewById(R.id.changephone_edit);

        userInput.setText(tv_modify_info_phone.getText());
        userInput.setSelection(tv_modify_info_phone.getText().toString().length()); //将光标设置在最后

        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                tv_modify_info_phone.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("修改手机号码");

        // show it
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(20);
    }

    /*修改电话号*/







    /*选择生日*/
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            String days;
            days = new StringBuffer().append(mYear).append("年").append(mMonth+1).append("月").append(mDay).append("日").toString();
            tv_modify_info_birthday.setText(days);
        }
    };
    /*       选择生日     */


    /*选泽性别*/
    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                tv_modify_info_sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }

    /*选泽性别*/



    /*地址选择*/


    private void showAddressPickerPop() {
        final PopupWindow popupWindow = new PopupWindow(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.address_picker, null, false);
        AddressPickerView addressView =rootView.findViewById(R.id.apvAddress);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                tv_modify_info_adress.setText(address);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(rootView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAsDropDown(tv_modify_info_adress);

    }
    /*地址选择*/



/*  修改信息*/
    class ModifyTask extends AsyncTask<String,Integer,String> {

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
                CustomToast.showToast(getApplicationContext(),"修改成功","register_success",500);
                return;
            }else {

                if (i==-1){
                    // Toast.makeText(getApplicationContext(),"对不起,该用户名已经被注册",Toast.LENGTH_SHORT).show();
                    CustomToast.showToast(getApplicationContext(),"修改失败","register_error",500);
                    return;
                }
            }
        }
    }
    /*  修改信息*/

}
