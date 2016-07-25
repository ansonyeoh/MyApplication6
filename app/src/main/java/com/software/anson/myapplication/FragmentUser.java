package com.software.anson.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anson on 2016/7/3.
 */
public class FragmentUser extends Fragment {

    private Button  bt_id_code;
    private FragmentUser_LoginDown fragmentLogin;
    public String Start_From, Destination, Shift, Timetable, Seat, Name, ID, Waiting_Room;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);


        bt_id_code = (Button) v.findViewById(R.id.bt_id_code);
        bt_id_code.setText("绑定，扫描二维码");
        bt_id_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
            }
        });



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");

            try {
                //JSONTokener jsonParser = new JSONTokener(result);
                // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
                JSONObject person =  new JSONObject(result);
                // 接下来的就是JSON对象的操作了
                Waiting_Room = person.getString("Waiting_Room");
                Start_From =  person.getString("Start_From");
                Destination = person.getString("Destination");
                Shift = person.getString("Shift");
                Timetable = person.getString("Timetable");
                Seat = person.getString("Seat");
                Name = person.getString("Name");
                ID = person.getString("ID");

                SharedPreferences myShared= getActivity().getSharedPreferences("user",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = myShared.edit();
                editor.putString("Waiting_Room",Waiting_Room);
                editor.putString("Start_From", Start_From);
                editor.putString("Destination", Destination);
                editor.putString("Shift", Shift);
                editor.putString("Timetable", Timetable);
                editor.putString("Seat", Seat);
                editor.putString("Name", Name);
                editor.putString("ID", ID);
                editor.putString("result", result);
                editor.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(this.getActivity(), "绑定成功，已登录！", Toast.LENGTH_LONG).show();
//            SharedPreferences myShared= getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor = myShared.edit();
//            editor.putString("result", result);
//            editor.commit();


            fragmentLogin = new FragmentUser_LoginDown();
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentLogin.setArguments(bundle);
            // 替换当前的页面
            fragmentTransaction.replace(R.id.frame_content, fragmentLogin, "login_down");
            // 事务管理提交
            fragmentTransaction.commit();

        }
    }

}
