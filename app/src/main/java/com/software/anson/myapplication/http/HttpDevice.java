package com.software.anson.myapplication.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.software.anson.myapplication.adapter.DeviceAdapter;
import com.software.anson.myapplication.model.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anson on 2016/8/10.
 */
public class HttpDevice extends Thread {
    private String url;
    private String deviceId, deviceAddress, music, sound, deviceName ;
    private int battery, alarm,lock,online,heart;
    private ListView listView;
    private DeviceAdapter adapter;
    private Context context;
    private Handler handler;

    public HttpDevice(String url, String deviceId, ListView listView,Context context,DeviceAdapter adapter, Handler handler) {
        this.url = url;
        this.deviceId = deviceId;
        this.listView = listView;
        this.adapter = adapter;
        this.handler = handler;
        this.context = context;
    }
    public HttpDevice(String url, String deviceId){
        this.url = url;
        this.deviceId = deviceId;
    }

    private void doGet() {
        url = url + "?deviceId=" + deviceId;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                sb.append(str);
                Log.i("======", sb.toString());
            }
            final List<Device> data = parseJson(sb.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setData(data);
                    listView.setAdapter(adapter);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doGet();
    }
    private List<Device> parseJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        List<Device> devices = new ArrayList<Device>();
        if (object.getBoolean("success") == true) {

            JSONObject msg = object.getJSONObject("msg");
            deviceAddress = msg.getString("deviceAddress");
            deviceName = msg.getString("deviceName");
            music = msg.getString("music");
            battery = msg.getInt("battery");
            alarm = msg.getInt("alarm");
            sound = msg.getString("sound");
            lock = msg.getInt("lock");
            online = msg.getInt("onLine");
            heart = msg.getInt("heart");

            //JSONArray device = rows.getJSONArray("Device");

            Device device = new Device();
            device.setDeviceCode(deviceAddress);
            device.setDeviceName(deviceName);
            device.setMusic(music);
            device.setBattery(battery);
            device.setAlarm(alarm);
            device.setSound(sound);
            device.setLock(lock);
            device.setOnline(online);
            device.setHeart(heart);
            devices.add(device);
            Log.i("===========", device.getDeviceName(deviceName));
            return devices;
        }
        return null;
    }

}
