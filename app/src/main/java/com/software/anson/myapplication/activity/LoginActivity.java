package com.software.anson.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.anson.myapplication.R;
import com.software.anson.myapplication.model.User;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Created by Anson on 2016/8/9.
 */

//登录功能
public class LoginActivity extends Activity{
    private EditText ed_ID, ed_password;
    private Button bt_id_code, bt_login, bt_register;
    public String Start_From , Destination, Shift, Timetable, Seat, Name, ID, Waiting_Room, DeviceId;
    private int Fee;
    private String identityCard, customerPwd;
    User user2 = new User();
    private static final int MSG_SUCCESS = 0;
    private static final int MSG_FAILED = 1;

    //将数据存储本地
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            switch (msg.what) {
                case MSG_SUCCESS:
                    user2 = (User) msg.obj;
                    Waiting_Room = user2.getRoom("room");
                    Start_From = user2.getStartFrom("Start_From");
                    Destination = user2.getDestination("Destination");
                    Shift = user2.getShift("Shift");
                    Timetable = user2.getTimetable("Timetable");
                    Seat = user2.getSeat("Seat");
                    Name = user2.getName("Name");
                    ID = user2.getIdentityCard("ID");
                    Fee = user2.getFee(0);
                    DeviceId = user2.getDeviceId("DeviceId");
                    Log.i("===", "座位号为 " + DeviceId);
                    SharedPreferences myShared = getSharedPreferences("user", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = myShared.edit();
                    editor.putString("Waiting_Room", Waiting_Room);
                    editor.putString("Start_From", Start_From);
                    editor.putString("Destination", Destination);
                    editor.putString("Shift", Shift);
                    editor.putString("Timetable", Timetable);
                    editor.putString("Seat", Seat);
                    editor.putString("Name", Name);
                    editor.putString("ID", ID);
                    editor.putInt("Fee", Fee);
                    editor.putString("DeviceId",DeviceId);
                    editor.commit();
                    if(Waiting_Room == null){
                        Toast.makeText(LoginActivity.this, "后台无车票数据，请联系后台管理员。", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case MSG_FAILED:
                    Toast.makeText(LoginActivity.this, "身份证号或密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        this.getActionBar().setTitle(R.string.Login);

        ed_ID = (EditText) findViewById(R.id.ed_ID);
        ed_password = (EditText) findViewById(R.id.ed_password);


        bt_id_code = (Button) findViewById(R.id.bt_id_code);
        bt_id_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, CaptureActivity.class), 0);
            }
        });

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identityCard = ed_ID.getText().toString().trim();
                customerPwd = ed_password.getText().toString().trim();
                String url = "http://115.159.38.62:3001/bd/customer/login";
                new HttpThreadForLogin(url, identityCard, customerPwd).start();
                Log.i("=====", "测试检验" + DeviceId);


//                Intent resultIntent = new Intent();
//                bundle.putInt("width", mCropRect.width());
//                bundle.putInt("height", mCropRect.height());
//                bundle.putString("result", rawResult.getText());
//                resultIntent.putExtras(bundle);
//                this.setResult(RESULT_OK, resultIntent);
//                CaptureActivity.this.finish();
            }
        });

        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);

               startActivity(intent);
            }
        });
    }


    //二维码扫描
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            ed_ID.setText(result);
        }
    }
    //http请求线程
    class HttpThreadForLogin extends Thread {
        private String url;
        private String identityCard;
        private String customerPwd;
        public String msg;

        HttpThreadForLogin(String url, String identityCard, String customerPwd) {
            this.url = url;
            this.identityCard = identityCard;
            this.customerPwd = customerPwd;
        }

        @Override
        public void run() {
            try {
                URL httpUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setReadTimeout(5000);
                OutputStream out = conn.getOutputStream();
                String content = "identityCard=" + identityCard + "&customerPwd=" + customerPwd;
                out.write(content.getBytes());
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final StringBuffer sb = new StringBuffer();
                String str;

                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                    Log.i("=============", sb.toString());
                    System.out.print(sb);
                }
                User user1 = parseJson(sb.toString());
                if (user1 != null) {
                    mHandler.obtainMessage(MSG_SUCCESS, user1).sendToTarget();
                } else {
                    mHandler.obtainMessage(MSG_FAILED).sendToTarget();
                }

            } catch (MalformedInputException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //解析返回的数据
        private User parseJson(String json) throws JSONException {
            JSONObject object = new JSONObject(json);
            if (object.getBoolean("success") == true) {
                JSONObject rows = object.getJSONObject("rows");
                ID = rows.getString("identityCard");
                Name = rows.getString("customerName");
                Start_From = rows.getString("startFrom");
                Destination = rows.getString("destination");
                Shift = rows.getString("shift");
                Seat = rows.getString("seat");
                Waiting_Room = rows.getString("room");
                Timetable = rows.getString("timetable");
                Fee = rows.getInt("fee");
                DeviceId =rows.getString("deviceId");
                //JSONArray device = rows.getJSONArray("Device");

                User user = new User();
                user.setDestination(Destination);
                user.setStartFrom(Start_From);
                user.setIdentityCard(ID);
                user.setName(Name);
                user.setRoom(Waiting_Room);
                user.setSeat(Seat);
                user.setShift(Shift);
                user.setTimetable(Timetable);
                user.setFee(Fee);
                user.setDeviceId(DeviceId);
                Log.i("===========", user.getStartFrom(Start_From));
                return user;
            }
            return null;
        }
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
}
