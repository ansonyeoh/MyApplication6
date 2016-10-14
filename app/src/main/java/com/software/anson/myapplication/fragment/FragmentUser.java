package com.software.anson.myapplication.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.software.anson.myapplication.R;
import com.software.anson.myapplication.activity.LoginActivity;
import com.software.anson.myapplication.activity.MainActivity;

/**
 * Created by Anson on 2016/7/12.
 */
public class FragmentUser extends Fragment {

    private TextView tv_info;
    private Button bt_jiebang;
    private Toolbar mToolbar5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user, container, false);
        bt_jiebang = (Button) v.findViewById(R.id.bt_jiebang);

        mToolbar5 = (Toolbar) v.findViewById(R.id.toolbar_user);
        // toolbar_device.setLogo(R.drawable.ic_launcher);
        mToolbar5.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar_device.setSubtitle("");
        mToolbar5.setTitleTextColor(R.color.white);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar5);



        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("Name", null);
        final int fee = sharedPreferences.getInt("Fee", 0);
        double cost = fee / 10;
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        Log.i("=====", "name = " + name);
        if (name == null) {
            tv_info.setText("尚未登录");
            bt_jiebang.setText("点击登录");
        } else {
            tv_info.setText(name + "    产生费用: " + cost + " 元");
            bt_jiebang.setText("注销登录");
        }

        bt_jiebang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name == null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);

                } else {
                    sharedPreferences.edit().clear().commit();
                    ShowDialog("解绑成功");
                }
            }

        });
        return v;
    }

    public void ShowDialog(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(string);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.e(TAG, "onCreateOptionsMenu()");
        menu.clear();
        inflater.inflate(R.menu.menu_device, menu);
    }
}
