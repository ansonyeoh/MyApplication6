package com.software.anson.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
    // 定义Fragment页面
    private FragmentMain fragmentMain;
    private FragmentDevice fragmentDevice;
    private FragmentMap fragmentMap;
    private FragmentUser fragmentUser;
    private FragmentUser_LoginDown fragmentUser_LoginDOWN;
    // 定义布局对象
    private FrameLayout mainFl, deviceFl, mapFl, userFl;

    // 定义图片组件对象
    private ImageView mainIv, deviceIv, mapIv, userIv;

    // 定义按钮图片组件
    private ImageView toggleImageView, plusImageView;

    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;

    public String result;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        // 初始化默认为选中点击了“动态”按钮
        clickMainBtn();
//        toolbar = (Toolbar) findViewById(R.id.tl_custom);
//        toolbar.setTitle("");//设置主标题


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
        super.onSaveInstanceState(outState);
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
        // 实例化布局对象
        mainFl = (FrameLayout) findViewById(R.id.layout_main);
        deviceFl = (FrameLayout) findViewById(R.id.layout_device);
        mapFl = (FrameLayout) findViewById(R.id.layout_map);
        userFl = (FrameLayout) findViewById(R.id.layout_user);

        // 实例化图片组件对象
        mainIv = (ImageView) findViewById(R.id.image_main);
        deviceIv = (ImageView) findViewById(R.id.image_device);
        mapIv = (ImageView) findViewById(R.id.image_map);
        userIv = (ImageView) findViewById(R.id.image_user);

        // 实例化按钮图片组件
        toggleImageView = (ImageView) findViewById(R.id.toggle_btn);
        plusImageView = (ImageView) findViewById(R.id.plus_btn);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 给布局对象设置监听
        mainFl.setOnClickListener(this);
        deviceFl.setOnClickListener(this);
        mapFl.setOnClickListener(this);
        userFl.setOnClickListener(this);

        // 给按钮图片设置监听
        toggleImageView.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击主页按钮
            case R.id.layout_main:
                clickMainBtn();
                break;
            // 点击设备按钮
            case R.id.layout_device:
                clickDeviceBtn();
                break;
            // 点击地图按钮
            case R.id.layout_map:
                clickMapBtn();
                break;
            // 点击用户按钮
            case R.id.layout_user:
                clickUserBtn();
                break;
            // 点击订餐按钮
            case R.id.toggle_btn:
                clickToggleBtn();
                break;
        }
    }

    /**
     * 点击了“主页”按钮
     */
    private void clickMainBtn() {
        // 实例化Fragment页面
        fragmentMain = new FragmentMain();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentMain, "main");
        // 事务管理提交
        fragmentTransaction.commit();
        // 改变选中状态
        mainFl.setSelected(true);
        mainIv.setSelected(true);

        deviceFl.setSelected(false);
        deviceIv.setSelected(false);

        mapFl.setSelected(false);
        mapIv.setSelected(false);

        userFl.setSelected(false);
        userIv.setSelected(false);
    }

    /**
     * 点击了“设备”按钮
     */
    private void clickDeviceBtn() {
        // 实例化Fragment页面
        fragmentDevice = new FragmentDevice();
        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentDevice, "device");
        // 事务管理提交
        fragmentTransaction.commit();

        mainFl.setSelected(false);
        mainIv.setSelected(false);

        deviceFl.setSelected(true);
        deviceIv.setSelected(true);

        mapFl.setSelected(false);
        mapIv.setSelected(false);

        userFl.setSelected(false);
        userIv.setSelected(false);
    }

    /**
     * 点击了“地图”按钮
     */
    private void clickMapBtn() {
         //实例化Fragment页面
        fragmentMap = new FragmentMap();

        // 得到Fragment事务管理器
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragmentMap, "map");
        // 事务管理提交
        fragmentTransaction.commit();


        mainFl.setSelected(false);
        mainIv.setSelected(false);

        deviceFl.setSelected(false);
        deviceIv.setSelected(false);

        mapFl.setSelected(true);
        mapIv.setSelected(true);

        userFl.setSelected(false);
        userIv.setSelected(false);
    }

    /**
     * 点击“用户”按钮
     */
    private void clickUserBtn() {

        SharedPreferences mShared = getSharedPreferences("user", Context.MODE_PRIVATE);
        /**拿到SharedPreferences中保存的数值 第二个参数为如果SharedPreferences中没有保存就赋一个默认值**/
        result = mShared.getString("result", null);

        if (result == null){
            // 实例化Fragment页面
            fragmentUser = new FragmentUser();
            // 得到Fragment事务管理器
            FragmentTransaction fragmentTransaction = this
                    .getSupportFragmentManager().beginTransaction();
            // 替换当前的页面
            fragmentTransaction.replace(R.id.frame_content, fragmentUser, "user");
            // 事务管理提交
            fragmentTransaction.commit();
            Toast.makeText(this, "未绑定" , Toast.LENGTH_LONG).show();
        }else{
            fragmentUser_LoginDOWN = new FragmentUser_LoginDown();
                // 得到Fragment事务管理器
            FragmentTransaction fragmentTransaction = this
                    .getSupportFragmentManager().beginTransaction();
                // 替换当前的页面
            fragmentTransaction.replace(R.id.frame_content, fragmentUser_LoginDOWN, "userlogin");
                // 事务管理提交
            fragmentTransaction.commit();
            }

        mainFl.setSelected(false);
        mainIv.setSelected(false);

        deviceFl.setSelected(false);
        deviceIv.setSelected(false);

        mapFl.setSelected(false);
        mapIv.setSelected(false);

        userFl.setSelected(true);
        userIv.setSelected(true);
    }

    /**
     * 点击了中间按钮
     */
    private void clickToggleBtn() {
        showPopupWindow(toggleImageView);
        // 改变按钮显示的图片为按下时的状态
        plusImageView.setSelected(true);
    }

    /**
     * 改变显示的按钮图片为正常状态
     */
    private void changeButtonImage() {
        plusImageView.setSelected(false);
    }

    /**
     * 显示PopupWindow弹出菜单
     */
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.popwindow_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        // PopupWindow的显示及位置设置
        // popWindow.showAtLocation(parent, Gravity.FILL, 0, 0);
        popWindow.showAsDropDown(parent, 0,0);

        popWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // 改变显示的按钮图片为正常状态
                changeButtonImage();
            }
        });

        // 监听触屏事件
        popWindow.setTouchInterceptor(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // 改变显示的按钮图片为正常状态
                changeButtonImage();
                popWindow.dismiss();
                return false;
            }
        });
    }


}