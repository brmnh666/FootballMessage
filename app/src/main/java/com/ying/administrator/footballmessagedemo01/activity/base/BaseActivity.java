package com.ying.administrator.footballmessagedemo01.activity.base;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 当前类注释:基类Activity 继承自FragmentActivity

 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
    protected void openActivity(Class<?> pClass){
        Intent mIntent=new Intent(this,pClass);
        this.startActivity(mIntent);
    }*/
    }
}
