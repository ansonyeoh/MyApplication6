package com.software.anson.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.software.anson.myapplication.R;

/**
 * Created by Anson on 2016/8/11.
 */
public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);//跳转到MainActivity
                SplashActivity.this.finish();//结束SplashActivity
            }
        }, 1500);//给postDelayed()方法传递延迟参数
    }
}
