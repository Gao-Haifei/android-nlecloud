package com.example.myappone;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NCallBack;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;

public class MainActivity extends AppCompatActivity {




    String NAME, PASS;
    EditText one, two;
    Button dl;
    NetWorkBusiness business;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar statusBar = new StatusBar(MainActivity.this);
        statusBar.setColor(R.color.transparent);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity);


        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        dl = findViewById(R.id.dl);
        //初始化

        //点击登录
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NAME = one.getText().toString();
                //获取输入的用户名
                PASS = two.getText().toString();
                //获取输入的密码
                NetWorkBusiness business = new NetWorkBusiness("", start.URL);
                //初始化
                business.signIn(new SignIn(NAME, PASS),
                        //进入云平台
                        new NCallBack<BaseResponseEntity<User>>(getApplicationContext()) {
                            @Override
                            protected void onResponse(BaseResponseEntity<User> response) {
                                User user = response.getResultObj();

                                //判断是否登录成功
                                if (user == null) {
                                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                } else {
                                    start.TOKEN = user.getAccessToken();
                                    //获取TOKEN
                                    MainActivity.this.business = new NetWorkBusiness(start.TOKEN, start.URL);
                                    Log.i("口令", start.TOKEN);
                                    //打印进入云台口令
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    //弹窗 登录成功
                                    Intent intent = new Intent();
                                    //创建一个事件
                                    intent.setClass(MainActivity.this, project_Activity.class);
                                    //事件  跳转界面至  project_Activity
                                    startActivity(intent);   //执行跳转
                                    finish();  //结束这个界面


                                }
                            }
                        });
            }
        });
    }
}