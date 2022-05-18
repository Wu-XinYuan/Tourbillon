package com.example.Tourbillon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class ClassroomQueryActivity extends AppCompatActivity {
    String classroom_url = "https://ids.sjtu.edu.cn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_query);

        WebView webview = (WebView) findViewById(R.id.query_class);

        Button return_app = (Button) findViewById(R.id.return_app_class);
        return_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 跳转到 空闲教室查询页面
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(classroom_url);

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

    }
}