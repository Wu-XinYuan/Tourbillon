package com.example.Tourbillon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LibraryQueryActivity extends AppCompatActivity {
    String library_url = "https://zgrs.lib.sjtu.edu.cn/index-m.html";
    //String studyroom_url = "http://studyroom.lib.sjtu.edu.cn/index.asp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_query);

        WebView webview = (WebView) findViewById(R.id.query_library);

        // 跳转到 图书馆入馆人数 + 入馆预约 界面
        WebSettings settings = webview.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);

        webview.loadUrl(library_url);

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        }
        );

        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
                        //后退
                        webview.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        Button return_app = (Button) findViewById(R.id.return_app_lib);
        return_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                String sHead=   "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
                        "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />"+
                        "<style>img{max-width:100% !important;height:auto !important;}</style>"
                        +"<style>body{max-width:100% !important;}</style>"+"</head><body>";

                //设定加载结束的操作
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String response_data = response.body().string();
                    webview.loadDataWithBaseURL(null, getNewContent(sHead + response_data + "</body></html>"), "text/html", "utf-8", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        */

        /*
        Button room_appoint = (Button) findViewById(R.id.appoint_room);
        room_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.loadUrl(studyroom_url);

            }
        });

         */
    }

    /*
    public static String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("max-width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

    public void sendRequestRequest(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

     */
}