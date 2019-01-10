package com.ying.administrator.footballmessagedemo01.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.ying.administrator.footballmessagedemo01.R;
import com.ying.administrator.footballmessagedemo01.common.Config;
import com.ying.administrator.footballmessagedemo01.common.DefineView;
import com.ying.administrator.footballmessagedemo01.entity.UserBean;
import com.ying.administrator.footballmessagedemo01.activity.base.BaseActivity;
import com.ying.administrator.footballmessagedemo01.utils.HttpUtil;
import com.ying.administrator.footballmessagedemo01.utils.Info;
import com.ying.administrator.footballmessagedemo01.utils.OkHttpManger;
import com.ying.administrator.footballmessagedemo01.widget.CustomToast;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends BaseActivity implements DefineView {
    private Button btn_back;
    private TextView top_title;
    private EditText username,password; //登录用户名密码
    private Button login;
    private Button register;

    String login_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initValidata();
        initListener();
        bindData();


    }

    @Override
    public void initView() {
        btn_back=findViewById(R.id.btn_back);
        top_title=findViewById(R.id.top_title);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
    }

    @Override
    public void initValidata() {
        top_title.setText("登录/注册界面");
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        login.setOnClickListener(new CustomOnClickListener());
        register.setOnClickListener(new CustomOnClickListener());

    }

    @Override
    public void bindData() {

    }



    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                 case R.id.btn_back:
                    LoginActivity.this.finish();
                    break;
                 case R.id.login:
                     login_username = username.getText().toString();
                    String login_password =password.getText().toString();
                    if (login_username.isEmpty()||login_password.isEmpty()){
                        //Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                        CustomToast.showToast(getApplicationContext(),"用户名或密码不能为空,请重新输入","login_error",2000);
                        username.setText("");
                        password.setText("");
                    }
                    else if (Info.isContainChinese(login_username)||Info.isContainChinese(login_password)){
                        //Toast.makeText(LoginActivity.this,"用户名或密码不能中文",Toast.LENGTH_SHORT).show();
                        CustomToast.showToast(getApplicationContext(),"用户名或密码不能为中文,请重新输入","login_error",2000);
                        username.setText("");
                        password.setText("");
                    }
                    else if (login_username.indexOf(" ")!=-1||login_password.indexOf(" ")!=-1){
                        CustomToast.showToast(getApplicationContext(),"用户名密码不能含有空格,请重新输入","login_error",2000);
                        username.setText("");
                        password.setText("");
                    }
                    else {
                        //Toast.makeText(MainActivity.this, "用户名：" + Username + "密码：" + Password, Toast.LENGTH_SHORT).show();
                       //new LoginTask().execute(url);
                        String url = Config.LOCALHOST_URL+"/Myservers/LoginServlet?username="+login_username+"&password="+login_password;



                         OkHttpManger.getAsync(url, new OkHttpManger.DataCallBack() {
                             @Override
                             public void requestFailure(Request request, Exception e) {
                                // Log.d("error","请检查是否开启Tomcat");
                                 CustomToast.showToast(getApplicationContext(),"登录失败,请检查是否开启Tomcat或者IP地址是否设置正确","login_error",3000);
                             }

                             @Override
                             public void requestSuccess(String result) {

                                  //Log.d("aaaaaaaaa",result);

                                    //保存数据
                                 if(result.equals("1")){

                                  //   Log.d("b",result);
                                    // Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                                   // CustomToast.showToast(getApplicationContext(),"登录成功","login_success",300);

                                     /*登录成功到主界面*/
                                     Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                                     intent.putExtra("username",login_username); //将用户名传递给主界面
                                     startActivity(intent);
                                     finish();
                                      return;

                                 }else if (result.equals("0")){ //为新用户 进入完善页面
                                     /*登录成功到完善界面*/
                                     Intent intent =new Intent(LoginActivity.this,FirstCompleteMyInfoActivity.class);
                                     intent.putExtra("username",login_username); //将用户名传递给主界面
                                     startActivity(intent);
                                     finish();
                                     return;
                                 }


                                 else if(result.equals("-1")){
                                        // Toast.makeText(getApplicationContext(),"登陆失败",Toast.LENGTH_SHORT).show();
                                         CustomToast.showToast(getApplicationContext(),"账号或者密码错误","login_error",2000);
                                     return;

                                     }

                             }
                         });

                    }
                             /*快捷通道*/
                    if (login_username.equals("root")&&login_password.equals("root")){
                        /*登录成功到主界面*/
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("username",login_username);
                        startActivity(intent);
                        finish();

                    }

                    break;


                case R.id.register:
                    /*注册*/
                    String register_username = username.getText().toString();
                    String register_password = password.getText().toString();

                    //判断字符串中是否含有中文
                    if (Info.isContainChinese(register_username)||Info.isContainChinese(register_password)){

                     //   Toast.makeText(getApplicationContext(),"用户名或密码不能含有中文",Toast.LENGTH_SHORT).show();
                        CustomToast.showToast(getApplicationContext(),"用户名或密码不能含有中文,请重新输入","register_error",2000);
                        username.setText("");
                        password.setText("");
                    }

                    else if (register_username.isEmpty()||register_password.isEmpty()){

                        //Toast.makeText(getApplicationContext(),"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();
                        CustomToast.showToast(getApplicationContext(),"用户名或者密码不能为空,请重新输入","register_error",2000);
                        username.setText("");
                        password.setText("");
                        //判断账号名或者密码是否含有空格
                    }  else if (register_username.indexOf(" ")!=-1||register_password.indexOf(" ")!=-1){

                        //Toast.makeText(getApplicationContext(),"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();
                        CustomToast.showToast(getApplicationContext(),"用户名密码不能含有空格,请重新输入","register_error",2000);
                        username.setText("");
                        password.setText("");
                    }


                    else {
                        /*使用json传递到服务端*/
                        UserBean user =new UserBean();
                        user.setUsername(register_username);
                        user.setPassword(register_password);
                        user.setName("匿名用户"+Info.getStringRandom(8)); //随机生成8个字符串
                        user.setRegister_time(Info.getDate()); //获取当前时间
                        user.setIs_new_user("1"); //注册完成后赋值是新用户
                        user.setSkill("0"); //技能点默认为0
                        user.setTactics("0");//战术点默认为0
                        user.setWorth("0");//身价默认为0

                        Gson gson =new Gson();
                        Type type = new TypeToken<UserBean>(){}.getType();
                        String jsonstr = gson.toJson(user,type);

                        String url = Config.LOCALHOST_URL+"/Myservers/RegisterServlet";
                        new RegisterTask().execute(url,jsonstr);

                    }

                    break;

                    default:
                     break;
            }
        }


    }




    /*--------------------------------登录-------------------------------*/
   /* class LoginTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            String par  = params[0];
            URL url = null;
            try {
                url = new URL(par);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String result = HttpUtil.doPost(url);
            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //保存数据
            if(result.equals("1")){
                Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();

                *//*登录成功到主界面*//*
                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("username",login_username); //将用户名传递给主界面
                startActivity(intent);
                finish();

                return;
            }else{
                if(result.equals("-1")){
                    Toast.makeText(getApplicationContext(),"登陆失败",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        }
    }*/
    /*--------------------------------登录-------------------------------*/



    /*--------------------------------注册-------------------------------*/
    class RegisterTask extends AsyncTask<String,Integer,String> {

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
                CustomToast.showToast(getApplicationContext(),"注册成功","register_success",2000);
                return;
            }else {

                if (i==-1){
                   // Toast.makeText(getApplicationContext(),"对不起,该用户名已经被注册",Toast.LENGTH_SHORT).show();
                    CustomToast.showToast(getApplicationContext(),"对不起,该用户名已经被注册","register_error",2000);
                    return;
                }
            }
        }
    }
    /*--------------------------------注册-------------------------------*/

}
