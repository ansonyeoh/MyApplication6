package com.software.anson.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.software.anson.myapplication.R;
import com.software.anson.myapplication.activity.SettingActivity;
import com.software.anson.myapplication.adapter.DeviceAdapter;
import com.software.anson.myapplication.http.HttpCommand;
import com.software.anson.myapplication.http.HttpDevice;
import com.software.anson.myapplication.model.Device;
import com.software.anson.myapplication.swipemenu.SwipeMenu;
import com.software.anson.myapplication.swipemenu.SwipeMenuCreator;
import com.software.anson.myapplication.swipemenu.SwipeMenuItem;
import com.software.anson.myapplication.swipemenu.SwipeMenuListView;

import java.util.Timer;


/**
 * Created by Anson on 2016/7/3.
 */
public class FragmentDevice extends Fragment {

    private Button bt1, bt2;
    private SwipeMenuListView listView;
    private DeviceAdapter adapter;
    private Handler handler = new Handler();
    private Context context;
    private Toolbar mToolbar2;
    private Timer timer;
    public int count = 0;
    private boolean isTip = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        final String deviceId = sharedPreferences.getString("DeviceId", null);
        //final String deviceId = 1001+"";
        final String name = sharedPreferences.getString("Name", null);
        View v = inflater.inflate(R.layout.fragment_device, container, false);

        mToolbar2 = (Toolbar) v.findViewById(R.id.toolbar_device);
        // toolbar_device.setLogo(R.drawable.ic_launcher);
        mToolbar2.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar_device.setSubtitle("");
        mToolbar2.inflateMenu(R.menu.menu_device);


        bt2 = (Button) v.findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                        intent.setClass(getActivity(), SettingActivity.class);
                        startActivity(intent);
            }
        });

        bt1 = (Button) v.findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name == null){
                    Toast.makeText(getActivity(),"用户未登录！请登录",Toast.LENGTH_LONG).show();
                }else if(deviceId == null){
                    Toast.makeText(getActivity(),"您未租赁任何设备！", Toast.LENGTH_LONG).show();
                }else {
                String url1 = "http://115.159.38.62:3001/bd/device/findOne";
                new HttpDevice(url1, deviceId, listView, context,adapter,handler).start();

//                Intent serviceIntent = new Intent("HeartbeatService");
//                serviceIntent.putExtra("url",url1);
//                serviceIntent.putExtra("deviceId",deviceId);
//                getActivity().startService(serviceIntent);
                Log.i("===", "FragmentDevice" + deviceId);}
            }
        });

//        bt2 = (Button) v.findViewById(R.id.bt2);
//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), SimpleActivity.class);
//                startActivity(intent);
//            }
//        });

        listView = (SwipeMenuListView) v.findViewById(R.id.listView);
        adapter = new DeviceAdapter(getActivity());


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override

            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(context);
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
//                if (device.getDeviceCode()=="0"){
//                    openItem.setIcon(R.drawable.open);
//                }else {
//
//                }
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setIcon(getResources().getDrawable(R.drawable.open));
                // add to menu
                menu.addMenuItem(openItem);

            }
        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                Device device = (Device) adapter.getItem(position);
                switch (index) {
                    case 0:
                        if (device.getOnline() == 0) {
                            // turn on
                            String url = "http://115.159.38.62:3001/bd/device/appOrder";
                            new HttpCommand(url, "~ZJNU100103o0").start();
                        } else if (device.getOnline() == 1){
                            // turn off
                            String url = "http://115.159.38.62:3001/bd/device/appOrder";
                            new HttpCommand(url, "~ZJNU100103c1").start();
                        }
                        break;
                    case 1:
                        break;
                }
            }
        });

        // set SwipeListener
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });



        return v;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
////        Log.e(TAG, "onCreateOptionsMenu()");
//        menu.clear();
//        inflater.inflate(R.menu.menu_device, menu);
//    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
