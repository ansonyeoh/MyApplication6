package com.software.anson.myapplication.http;

import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Created by Anson on 2016/7/28.
 */
public class HttpThreadForRegister extends Thread {

    private String url;
    private String customerName;
    private String identityCard;
    private String customerPwd;

    public HttpThreadForRegister(String url, String customerName, String identityCard, String customerPwd){
        this.url = url;
        this.customerName = customerName;
        this.identityCard = identityCard;
        this.customerPwd = customerPwd;
    }

    private void doGet(){
        url = url + "?customerName=" + customerName + "&identityCard=" + identityCard +"&customerPwd=" + customerPwd;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str=reader.readLine())!=null){
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doHttpClientPost() throws JSONException, IOException {
        try {
//        HttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost(url);
//        post.addHeader("Accept", "application/json");
//        JSONObject param = new JSONObject();//定义json对象
//        param.put("customerName", customerName);
//        param.put("customerPwd", customerPwd);
//        param.put("identityCard", identityCard);
//        Log.i("=============", String.valueOf(param));
//        StringEntity se = new StringEntity(param.toString());
//        post.setEntity(se);//发送数据
//
//        HttpResponse response = client.execute(post);//获得相应
//        String result = EntityUtils.toString(response.getEntity());
//        JSONObject result1 = new JSONObject(result);
//        Log.i("=============", result1.toString());
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            OutputStream out = conn.getOutputStream();
            String content = "identityCard=" + identityCard +"&customerName="+ customerName + "&customerPwd=" + customerPwd;
            out.write(content.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;

            while((str = reader.readLine())!=null){
                sb.append(str);
                Log.i("=============", sb.toString());
                System.out.print(sb);
            }
        } catch (MalformedInputException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        //doGet();
        try {
            doHttpClientPost();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
