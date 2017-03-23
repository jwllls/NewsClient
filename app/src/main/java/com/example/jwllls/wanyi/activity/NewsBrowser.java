package com.example.jwllls.wanyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jwllls.wanyi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jwllls on 2016/12/19.
 */

public class NewsBrowser extends ToolBarActivity {

    WebView webNews;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsbrowser);
        webNews = (WebView) findViewById(R.id.web_news);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        webNews.loadUrl(url);
        //设置可自由缩放网页
        webNews.getSettings().setSupportZoom(true);
        webNews.getSettings().setBuiltInZoomControls(true);
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        webNews.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        webNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webNews.loadUrl(url);
            }
        });


    }


    //go back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webNews.canGoBack()) {
            webNews.goBack();
            return true;
        }
        //  return true;
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}
