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
    // ����Fragmentҳ��
    private FragmentMain fragmentMain;
    private FragmentDevice fragmentDevice;
    private FragmentMap fragmentMap;
    private FragmentUser fragmentUser;
    private FragmentUser_LoginDown fragmentUser_LoginDOWN;
    // ���岼�ֶ���
    private FrameLayout mainFl, deviceFl, mapFl, userFl;

    // ����ͼƬ�������
    private ImageView mainIv, deviceIv, mapIv, userIv;

    // ���尴ťͼƬ���
    private ImageView toggleImageView, plusImageView;

    // ����PopupWindow
    private PopupWindow popWindow;
    // ��ȡ�ֻ���Ļ�ֱ��ʵ���
    private DisplayMetrics dm;

    public String result;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        // ��ʼ��Ĭ��Ϊѡ�е���ˡ���̬����ť
        clickMainBtn();
//        toolbar = (Toolbar) findViewById(R.id.tl_custom);
//        toolbar.setTitle("");//����������


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
     * ��ʼ�����
     */
    private void initView() {
        // ʵ�������ֶ���
        mainFl = (FrameLayout) findViewById(R.id.layout_main);
        deviceFl = (FrameLayout) findViewById(R.id.layout_device);
        mapFl = (FrameLayout) findViewById(R.id.layout_map);
        userFl = (FrameLayout) findViewById(R.id.layout_user);

        // ʵ����ͼƬ�������
        mainIv = (ImageView) findViewById(R.id.image_main);
        deviceIv = (ImageView) findViewById(R.id.image_device);
        mapIv = (ImageView) findViewById(R.id.image_map);
        userIv = (ImageView) findViewById(R.id.image_user);

        // ʵ������ťͼƬ���
        toggleImageView = (ImageView) findViewById(R.id.toggle_btn);
        plusImageView = (ImageView) findViewById(R.id.plus_btn);

    }

    /**
     * ��ʼ������
     */
    private void initData() {
        // �����ֶ������ü���
        mainFl.setOnClickListener(this);
        deviceFl.setOnClickListener(this);
        mapFl.setOnClickListener(this);
        userFl.setOnClickListener(this);

        // ����ťͼƬ���ü���
        toggleImageView.setOnClickListener(this);
    }

    /**
     * ����¼�
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // �����ҳ��ť
            case R.id.layout_main:
                clickMainBtn();
                break;
            // ����豸��ť
            case R.id.layout_device:
                clickDeviceBtn();
                break;
            // �����ͼ��ť
            case R.id.layout_map:
                clickMapBtn();
                break;
            // ����û���ť
            case R.id.layout_user:
                clickUserBtn();
                break;
            // ������Ͱ�ť
            case R.id.toggle_btn:
                clickToggleBtn();
                break;
        }
    }

    /**
     * ����ˡ���ҳ����ť
     */
    private void clickMainBtn() {
        // ʵ����Fragmentҳ��
        fragmentMain = new FragmentMain();
        // �õ�Fragment���������
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // �滻��ǰ��ҳ��
        fragmentTransaction.replace(R.id.frame_content, fragmentMain, "main");
        // ��������ύ
        fragmentTransaction.commit();
        // �ı�ѡ��״̬
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
     * ����ˡ��豸����ť
     */
    private void clickDeviceBtn() {
        // ʵ����Fragmentҳ��
        fragmentDevice = new FragmentDevice();
        // �õ�Fragment���������
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // �滻��ǰ��ҳ��
        fragmentTransaction.replace(R.id.frame_content, fragmentDevice, "device");
        // ��������ύ
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
     * ����ˡ���ͼ����ť
     */
    private void clickMapBtn() {
         //ʵ����Fragmentҳ��
        fragmentMap = new FragmentMap();

        // �õ�Fragment���������
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // �滻��ǰ��ҳ��
        fragmentTransaction.replace(R.id.frame_content, fragmentMap, "map");
        // ��������ύ
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
     * ������û�����ť
     */
    private void clickUserBtn() {

        SharedPreferences mShared = getSharedPreferences("user", Context.MODE_PRIVATE);
        /**�õ�SharedPreferences�б������ֵ �ڶ�������Ϊ���SharedPreferences��û�б���͸�һ��Ĭ��ֵ**/
        result = mShared.getString("result", null);

        if (result == null){
            // ʵ����Fragmentҳ��
            fragmentUser = new FragmentUser();
            // �õ�Fragment���������
            FragmentTransaction fragmentTransaction = this
                    .getSupportFragmentManager().beginTransaction();
            // �滻��ǰ��ҳ��
            fragmentTransaction.replace(R.id.frame_content, fragmentUser, "user");
            // ��������ύ
            fragmentTransaction.commit();
            Toast.makeText(this, "δ��" , Toast.LENGTH_LONG).show();
        }else{
            fragmentUser_LoginDOWN = new FragmentUser_LoginDown();
                // �õ�Fragment���������
            FragmentTransaction fragmentTransaction = this
                    .getSupportFragmentManager().beginTransaction();
                // �滻��ǰ��ҳ��
            fragmentTransaction.replace(R.id.frame_content, fragmentUser_LoginDOWN, "userlogin");
                // ��������ύ
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
     * ������м䰴ť
     */
    private void clickToggleBtn() {
        showPopupWindow(toggleImageView);
        // �ı䰴ť��ʾ��ͼƬΪ����ʱ��״̬
        plusImageView.setSelected(true);
    }

    /**
     * �ı���ʾ�İ�ťͼƬΪ����״̬
     */
    private void changeButtonImage() {
        plusImageView.setSelected(false);
    }

    /**
     * ��ʾPopupWindow�����˵�
     */
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.popwindow_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // ����һ��PopuWidow����
            popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // ʹ��ۼ� ��Ҫ������˵���ؼ����¼��ͱ���Ҫ���ô˷���
        popWindow.setFocusable(true);
        // ����������������ʧ
        popWindow.setOutsideTouchable(true);
        // ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        // PopupWindow����ʾ��λ������
        // popWindow.showAtLocation(parent, Gravity.FILL, 0, 0);
        popWindow.showAsDropDown(parent, 0,0);

        popWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // �ı���ʾ�İ�ťͼƬΪ����״̬
                changeButtonImage();
            }
        });

        // ���������¼�
        popWindow.setTouchInterceptor(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // �ı���ʾ�İ�ťͼƬΪ����״̬
                changeButtonImage();
                popWindow.dismiss();
                return false;
            }
        });
    }


}