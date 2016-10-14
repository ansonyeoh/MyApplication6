package com.software.anson.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.anson.myapplication.R;
import com.software.anson.myapplication.fragment.FragmentUser;
import com.software.anson.myapplication.http.HttpThreadForRegister;
import com.xys.libzxing.zxing.activity.CaptureActivity;

/**
 * Created by Anson on 2016/8/9.
 */

//用户注册功能
public class RegisterActivity extends Activity {

    private EditText ed_username, ed_ID_register, ed_password_register, ed_password_confirm;
    private Button bt_submit, bt_scan_id, bt_back;
    public String customerPwd;
    String identityCard, customerName;
    String pw1;
    String pw2;
    private FragmentUser fragmentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //this.getActionBar().setTitle(R.string.Register);
        ed_ID_register = (EditText) findViewById(R.id.ed_ID_register);
        ed_password_register = (EditText) findViewById(R.id.ed_password_register);
        ed_password_confirm = (EditText) findViewById(R.id.ed_password_confirm);
        ed_username = (EditText) findViewById(R.id.ed_customerName);




        bt_scan_id = (Button) findViewById(R.id.bt_scan_id);
        bt_scan_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RegisterActivity.this, CaptureActivity.class), 0);
            }
        });

        bt_back = (Button) findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerName = ed_username.getText().toString();
                identityCard = ed_ID_register.getText().toString();
                pw1 = ed_password_register.getText().toString();
                pw2 = ed_password_confirm.getText().toString();
                //判断用户输入信息
                if (TextUtils.isEmpty(ed_username.getText())) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ed_password_register.getText()) || TextUtils.isEmpty(ed_password_confirm.getText()) ){
                    Toast.makeText(RegisterActivity.this, "请输入密码！", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(ed_ID_register.getText())){
                    Toast.makeText(RegisterActivity.this,"请输入身份证号码！",Toast.LENGTH_SHORT).show();
                }else if(ed_password_register.getText().toString().equals(ed_password_confirm.getText().toString())){
                    customerPwd = pw1;
                    //注册的接口
                    String url = "http://115.159.38.62:3001/bd/customer/register";
                    new HttpThreadForRegister(url,customerName, identityCard, customerPwd).start();
                    Toast.makeText(RegisterActivity.this,"已提交",Toast.LENGTH_SHORT).show();
                   finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //扫描二维码返回结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            ed_ID_register.setText(result);
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
