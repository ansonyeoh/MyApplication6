package com.software.anson.myapplication.activity;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.software.anson.myapplication.R;
import com.software.anson.myapplication.http.HttpCommand;

/**
 * Created by Anson on 2016/8/12.
 */

//个人偏好设置功能
public class SettingActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    private Vibrator vibrator=null;
    private MediaPlayer music = null;// 播放器引用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SharedPreferences settings, phone_setting;
        final SharedPreferences.Editor editorsettings,editor;
        settings = getSharedPreferences("music", Context.MODE_PRIVATE);
        phone_setting = getSharedPreferences("phone_music", Context.MODE_PRIVATE);
        editor = phone_setting.edit();
        editorsettings = settings.edit();


        //手机音乐选择
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.music, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    PlayMusic(R.raw.m0);
                    editor.putInt("warning", 0);
                    editor.commit();
                } else if (pos == 1) {
                    PlayMusic(R.raw.m1);
                    editor.putInt("warning", 1);
                    editor.commit();
                } else if (pos == 2) {
                    PlayMusic(R.raw.m2);
                    editor.putInt("warning", 2);
                    editor.commit();
                } else if (pos == 3) {
                    PlayMusic(R.raw.m3);
                    editor.putInt("warning", 3);
                    editor.commit();
                } else if (pos == 4) {
                    PlayMusic(R.raw.m4);
                    editor.putInt("warning", 4);
                    editor.commit();
                } else if (pos == 5) {
                    PlayMusic(R.raw.m5);
                    editor.putInt("warning", 5);
                    editor.commit();
                } else if (pos == 6) {
                    PlayMusic(R.raw.m6);
                    editor.putInt("warning", 6);
                    editor.commit();
                } else if (pos == 7) {
                    PlayMusic(R.raw.m7);
                    editor.putInt("warning", 7);
                    editor.commit();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        Switch switch_lock = (Switch) findViewById(R.id.switch_lock);
        switch_lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            String url = "http://115.159.38.62:3001/bd/device/appOrder";
            String command_open_lock = "~ZJNU100103l0";
            String command_close_lock = "~ZJNU100103l1";

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    new HttpCommand(url, command_open_lock).start();
                } else {
                    new HttpCommand(url, command_close_lock).start();
                }
            }
        });

        //设置最后一次选择的手机提示音
        SharedPreferences settings3= getSharedPreferences("phone_music", 0); //与上面的保持一致
        int position2 = settings3.getInt("warning", 0 );
        spinner2.setSelection(position2);

        //手机震动
        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        Switch switch_vibrate = (Switch) findViewById(R.id.switch_vibrator);
        switch_vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //turn on
                    vibrator.vibrate(new long[]{100, 400, 100, 400}, 2);
                } else {
                    //turn off
                    vibrator.cancel();
                }
            }
        });

        //设备音量
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int num = seekBar.getProgress();
                String a = String.valueOf(seekBar.getProgress());
                String command = "~ZJNU100104jDF" ;
                String url = "http://115.159.38.62:3001/bd/device/appOrder";
                Toast.makeText(SettingActivity.this, "Max:" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                new HttpCommand(url, command).start();

                if (num < 15.92) {
                    seekBar.setProgress(0);
                } else if (num >= 15.92 && num < 47.78) {
                    seekBar.setProgress(31);
                } else if (num >= 47.78 && num < 79.64) {
                    seekBar.setProgress(64);
                } else if (num >= 79.64 && num < 111.5) {
                    seekBar.setProgress(96);
                } else if (num >= 111.5 && num < 143.35) {
                    seekBar.setProgress(127);
                } else if (num >= 143.35 && num < 175.21) {
                    seekBar.setProgress(159);
                } else if (num >= 175.21 && num < 207.07) {
                    seekBar.setProgress(191);
                } else if (num >= 207.07) {
                    seekBar.setProgress(223);
                }

            }
        });

        //设备音乐选择
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.music, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String command = "~ZJNU100104xE" + pos;
                String url = "http://115.159.38.62:3001/bd/device/appOrder";
                new HttpCommand(url, command).start();

                editorsettings.putInt("SelectedPosition", pos);
                editorsettings.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        //获取设备音乐最后一次选择的选项
        SharedPreferences settings2 = getSharedPreferences("music", 0); //与上面的保持一致
        int position = settings2.getInt("SelectedPosition", 0 );
        spinner.setSelection(position);
        //确定按钮
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


//播放音乐
    private void PlayMusic(int MusicId) {

        music = MediaPlayer.create(this, MusicId);
        music.start();

    }
}
