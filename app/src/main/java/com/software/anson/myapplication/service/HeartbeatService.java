package com.software.anson.myapplication.service;


import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.software.anson.myapplication.http.HttpDevice;

import java.util.List;

public class HeartbeatService extends Service implements Runnable
{
    private Thread mThread;
    public int count = 0;
    private boolean isTip = true;
    private static String mRestMsg, deviceId;
    private static String KEY_REST_MSG = "KEY_REST_MSG";

    @Override
    public void run()
    {
        Log.i("---","心跳包");
        while (true)
        {
            try
            {
                if (count > 1)
                {
                    Log.i("@qi", "offline");
                    count = 1;
                    if (isTip)
                    {
                        //判断应用是否在运行
                        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(3);
                        for (ActivityManager.RunningTaskInfo info : list)
                        {
                            if (info.topActivity.getPackageName().equals("com.software.anson.myapplication"))
                            {
                                //通知应用，显示提示“连接不到服务器”
                                Intent intent = new Intent("com.software.anson.myapplication");
                                intent.putExtra("msg", true);
                                sendBroadcast(intent);
                                break;
                            }
                        }

                        isTip = false;
                    }
                }
                if (mRestMsg != "" && mRestMsg != null)
                {
                    //向服务器发送心跳包
                    sendHeartbeatPackage(mRestMsg);
                    count += 1;
                }

                Thread.sleep(1000 * 10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void sendHeartbeatPackage(String msg)
    {
        String url = "http://115.159.38.62:3001/bd/device/appOrder?command=~ZJNU1001031H";
        try {
            new HttpDevice(msg, deviceId).start();
//            URL httpUrl = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setReadTimeout(5000);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String str;
//            StringBuffer sb = new StringBuffer();
//            while ((str = reader.readLine()) != null) {
//                sb.append(str);
//                Log.i("======", sb.toString());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void onStart(Intent intent, int startId)
    {
        Log.i("@qi", "service onStart");
        //从本地读取服务器的URL，如果没有就用传进来的URL
        mRestMsg = getRestMsg();
        if (mRestMsg == null || mRestMsg == "")
        {
            mRestMsg = intent.getExtras().getString("url");
            deviceId = intent.getExtras().getString("deviceId");
            Log.i("==",deviceId);
        }
        setRestMsg(mRestMsg);

        mThread = new Thread(this);
        mThread.start();
        count = 0;

        super.onStart(intent, startId);
    }

    public String getRestMsg()
    {
        SharedPreferences prefer = getSharedPreferences("settings.data", Context.MODE_PRIVATE);
        Log.i("@qi", "getRestMsg() " + prefer.getString(KEY_REST_MSG, ""));
        return prefer.getString(KEY_REST_MSG, "");
    }

    public void setRestMsg(String restMsg)
    {
        SharedPreferences prefer = getSharedPreferences("settings.data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putString(KEY_REST_MSG, restMsg);
        editor.commit();
    }

}
