package com.example.group33_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
Homework 05
Group33_HW05.zip
Nick DeBakey & Lis Rizvanolli
 */

public class WebViewActivity extends AppCompatActivity {

    WebView wv_article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final Bundle extrasFromNews = getIntent().getExtras().getBundle("article");
        final News news = (News) extrasFromNews.getSerializable("article");
        setTitle(news.author);

        wv_article = findViewById(R.id.wv_article);
        wv_article.setWebViewClient(new WebViewClient());
        wv_article.loadUrl(news.url);
    }
}
