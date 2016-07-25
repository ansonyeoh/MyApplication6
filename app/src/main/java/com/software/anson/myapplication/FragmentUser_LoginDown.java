package com.software.anson.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Anson on 2016/7/12.
 */
public class FragmentUser_LoginDown extends Fragment {

    private Button bt_info;
    private Button bt_jiebang;
    private FragmentUser fragmentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  v = inflater.inflate(R.layout.fragmentlogin, container, false);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", null);

        bt_info = (Button) v.findViewById(R.id.bt_info);
        bt_info.setText(name);
        bt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bt_jiebang = (Button) v.findViewById(R.id.bt_jiebang);
        bt_jiebang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = new File(DATA_URL + getActivity().getPackageName().toString()
//                        + "/shared_prefs/", SHARED_MAIN_XML);
//                if (file.exists()) {
//                    file.delete();
//                }else{
//                    Toast.makeText(getActivity(),"无文件1", Toast.LENGTH_SHORT).show();
//                }

                sharedPreferences.edit().clear().commit();
                ShowDialog("解绑成功");
            }

        });
        return v;
    }
    public void ShowDialog(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(string);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                fragmentUser = new FragmentUser();
                // 得到Fragment事务管理器
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                // 替换当前的页面
                fragmentTransaction.replace(R.id.frame_content, fragmentUser, "user");
                // 事务管理提交
                fragmentTransaction.commit();
            }
        });
        builder.show();
    }
}
