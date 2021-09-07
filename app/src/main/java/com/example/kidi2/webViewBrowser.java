package com.example.kidi2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class webViewBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_browser);
        WebView browser = (WebView) findViewById(R.id.webView);
        browser.loadUrl("https://www.google.com/");
    }

}