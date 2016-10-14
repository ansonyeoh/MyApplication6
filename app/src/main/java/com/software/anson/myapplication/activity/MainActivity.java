package com.software.anson.myapplication.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.RadioGroup;

import com.software.anson.myapplication.R;
import com.software.anson.myapplication.fragment.FragmentDevice;
import com.software.anson.myapplication.fragment.FragmentMain;
import com.software.anson.myapplication.fragment.FragmentMap;
import com.software.anson.myapplication.fragment.FragmentServe;
import com.software.anson.myapplication.fragment.FragmentUser;

//主Activity
public class MainActivity extends ActionBarActivity {
    // 定义Fragment页面
    private FragmentMain fragmentMain;
    private FragmentDevice fragmentDevice;
    private FragmentMap fragmentMap;
    private FragmentUser fragmentUser;
    private FragmentServe fragmentServe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(savedInstanceState == null) {
//            clickMainBtn();
//        } else {
//            // Don't add the fragment!
//            // (and use savedInstanceState to restore Activity state)
//        }
        initView();
        clickMainBtn();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    /**
     * 初始化组件
     */
    private void initView() {
        RadioGroup myTabRg;
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.layout_main:
                        clickMainBtn();
                        break;
                    case R.id.layout_device:
                        clickDeviceBtn();
                        break;
                    case R.id.layout_map:
                        clickMapBtn();
                        break;
                    case R.id.layout_user:
                        clickUserBtn();
                        break;
                    case R.id.layout_serve:
                        clickServeBtn();
                        break;
                    default:
                        break;
                }

            }
        });

    }


    /**
     * 点击了“主页”按钮
     */
    private void clickMainBtn() {
        //this.getActionBar().setTitle(R.string.Main);
        FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();
        // 实例化Fragment页面
        if (fragmentMain == null) {
            fragmentMain = new FragmentMain();
            transaction.add(R.id.frame_content, fragmentMain, "main");
        }
        hideFragment(transaction);
        transaction.show(fragmentMain);
        transaction.commit();
    }

    /**
     * 点击了“设备”按钮
     */
    private void clickDeviceBtn() {
        //this.getActionBar().setTitle(R.string.Device);
        FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();
        // 实例化Fragment页面
        if (fragmentDevice == null) {
            fragmentDevice = new FragmentDevice();
            transaction.add(R.id.frame_content, fragmentDevice, "device");
        }

        hideFragment(transaction);
        transaction.show(fragmentDevice);
        transaction.commit();
    }

    /**
     * 点击了“地图”按钮
     */
    private void clickMapBtn() {
        //this.getActionBar().setTitle(R.string.Map);
        FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();
        // 实例化Fragment页面
        if (fragmentMap == null) {
            fragmentMap = new FragmentMap();
            transaction.add(R.id.frame_content, fragmentMap, "map_icon");
        }

        hideFragment(transaction);
        transaction.show(fragmentMap);
        transaction.commit();
    }

    /**
     * 点击“用户”按钮
     */
    private void clickUserBtn() {
        //this.getActionBar().setTitle(R.string.User);
        // 实例化Fragment页面
        FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();
        // 实例化Fragment页面
        if (fragmentUser == null) {
            fragmentUser = new FragmentUser();
            transaction.add(R.id.frame_content, fragmentUser, "user");
        }
        hideFragment(transaction);
        transaction.show(fragmentUser);
        transaction.commit();
    }

    /**
     * 点击“点餐”按钮
     */
    private void clickServeBtn() {
        //this.getActionBar().setTitle(R.string.serve);
        //实例化Fragment页面
        FragmentTransaction transaction = this
                .getSupportFragmentManager().beginTransaction();
        // 实例化Fragment页面
        if (fragmentServe == null) {
            fragmentServe = new FragmentServe();
            transaction.add(R.id.frame_content, fragmentServe, "serve");
        }
        hideFragment(transaction);
        transaction.show(fragmentServe);
        transaction.commit();
    }


    private void hideFragment(FragmentTransaction transaction) {

        if (fragmentMain != null) {
            transaction.hide(fragmentMain);
        }
        if (fragmentDevice != null) {
            transaction.hide(fragmentDevice);
        }
        if (fragmentMap != null) {
            transaction.hide(fragmentMap);
        }
        if (fragmentUser != null) {
            transaction.hide(fragmentUser);
        }
        if (fragmentServe != null) {
            transaction.hide(fragmentServe);
        }

    }

}