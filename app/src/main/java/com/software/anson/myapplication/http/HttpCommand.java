package com.software.anson.myapplication.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anson on 2016/8/12.
 */
public class HttpCommand extends Thread {

    private String url;
    private String command;

    public HttpCommand (String url, String command){
        this.url = url;
        this.command = command;
    }

    private void doGet() {
        url = url + "?command=" + command;
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        doGet();
    }
}
