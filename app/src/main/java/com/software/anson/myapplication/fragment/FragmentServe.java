package com.software.anson.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.software.anson.myapplication.R;

/**
 * Created by Anson on 2016/7/25.
 */

public class FragmentServe extends Fragment {

    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_serve, container, false);

        webview = (WebView) v.findViewById(R.id.webView);

        WebSettings setting = webview.getSettings();
        setting.setPluginState(WebSettings.PluginState.ON);
        setting.setJavaScriptEnabled(true);
        setting.setAppCacheEnabled(true);
        setting.setDomStorageEnabled(true);
        //设置缓存模式
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);

//复写WebViewClient的shouldOverrideUrlLoading()的方法

//如果需要事件处理返回false,否则返回true.这样就可以解决问题了

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                lodurl(view, url);
                return false;
            }
        });

        this.webview.loadUrl("http://115.159.38.62:8100/");


        return v;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    public void lodurl(final WebView webView, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }
}
