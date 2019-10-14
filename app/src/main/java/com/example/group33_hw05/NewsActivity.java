package com.example.group33_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

import static com.example.group33_hw05.MainActivity.TAG_IMAGE;

/*
Homework 05
Group33_HW05.zip
Nick DeBakey & Lis Rizvanolli
 */

public class NewsActivity extends AppCompatActivity {

    ProgressBar progressBar_news;
    ListView lv_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        progressBar_news = findViewById(R.id.progressBar_news);
        progressBar_news.setVisibility(View.INVISIBLE);
        lv_news = findViewById(R.id.lv_news);

        final Bundle extrasFromMain = getIntent().getExtras().getBundle(TAG_IMAGE);
        final Source source = (Source) extrasFromMain.getSerializable("source");
        setTitle(source.getName());

        new GetNews().execute("https://newsapi.org/v2/top-headlines?sources=" + source.id + "&apiKey=5c63098c345e438c99411cf1b5a6bec9");
    }

    private class GetNews extends AsyncTask<String, Void, ArrayList<News>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar_news.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            ArrayList<News> newsArticles = new ArrayList<>();
            HttpURLConnection connection = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                    JSONObject jsonRoot = new JSONObject(json);
                    JSONArray jsonSources = jsonRoot.getJSONArray("articles");
                    for (int i = 0; i < jsonSources.length(); i++) {
                        JSONObject jsonSource = jsonSources.getJSONObject(i);
                        News news = new News(jsonSource.getString("author"),
                                jsonSource.getString("title"),
                                jsonSource.getString("url"),
                                jsonSource.getString("urlToImage"),
                                jsonSource.getString("publishedAt"));
                        newsArticles.add(news);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return newsArticles;
        }

        @Override
        protected void onPostExecute(final ArrayList<News> news) {
            super.onPostExecute(news);
            progressBar_news.setVisibility(View.INVISIBLE);
            NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, R.layout.model, news);
            lv_news.setAdapter(newsAdapter);
            lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
                    Bundle sendData = new Bundle();
                    sendData.putSerializable("article", (Serializable) news.get(i));
                    intent.putExtra("article", sendData);
                    startActivity(intent);
                }
            });
        }
    }
}
