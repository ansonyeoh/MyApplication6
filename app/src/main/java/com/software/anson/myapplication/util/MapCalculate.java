package com.software.anson.myapplication.util;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Anson on 2016/8/5.
 */
public class MapCalculate {
    public float BaiduX, BaiduY;

    protected void runTest() throws Throwable{
        try {
            BaiduLocation bl = new BaiduLocation();
            bl.gpsx = (float) 116.39715589925937;//经度
            bl.gpsy = (float) 39.916260363776765;//纬度
            GetBaiduLocation(bl);
            if(bl.ok) {
                BaiduX = (float)(bl.baidux*1E6);
                BaiduY = (float)(bl.baiduy*1E6);
                // 转换成功，这个坐标是百度专用的
            }
            else {
                /// 转换失败
            }
        }
        catch(Exception ex) {
        }
    }

    class BaiduLocation {
        public float gpsx, gpsy;
        public float baidux, baiduy;
        public boolean ok = false;
    }



    public static String GetBaiduLocation(float x, float y) throws MalformedURLException, IOException {
        String url = String.format("http://api.map_icon.baidu.com/ag/coord/convert?from=0&to=4&x=%f&y=%f", x, y);
        HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url).openConnection());
        urlConnection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String lines = reader.readLine();
        reader.close();
        urlConnection.disconnect();
        return lines;
    }

    public static boolean GetBaiduLocation(BaiduLocation bl) {
        try {
            bl.ok = false;
            String res = GetBaiduLocation(bl.gpsx, bl.gpsy);
            if(res.startsWith("{") && res.endsWith("}")) {
                res = res.substring(1, res.length() - 2).replace("\"", "");
                String[] lines = res.split(",");
                for(String line : lines) {
                    String[] items = line.split(":");
                    if(items.length == 2) {
                        if("error".equals(items[0])) {
                            bl.ok = "0".equals(items[1]);
                        }
                        if("x".equals(items[0])) {
                            bl.baidux = ConvertBase64(items[1]);
                        }
                        if("y".equals(items[0])) {
                            bl.baiduy = ConvertBase64(items[1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            bl.ok = false;
        }
        return bl.ok;
    }
    private static float ConvertBase64(String str) {
        byte[] bs = Base64.decode(str,Base64.DEFAULT);
        return Float.valueOf(new String(bs));
    }

}
