package com.example.group33_hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView lv_sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");

        progressBar = findViewById(R.id.progressBar);
        lv_sources = findViewById(R.id.lv_sources);

        progressBar.setVisibility(View.INVISIBLE);

        if (isConnectedOnline()) {
            new GetSources().execute("https://newsapi.org/v2/sources?apiKey=5c63098c345e438c99411cf1b5a6bec9");
        }
        else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null) {
            networkInfo = cm.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    private class GetSources extends AsyncTask<String, Void, ArrayList<Source>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Source> doInBackground(String... strings) {
            ArrayList<Source> sources = new ArrayList<>();
            HttpURLConnection connection = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                    JSONObject jsonRoot = new JSONObject(json);
                    JSONArray jsonSources = jsonRoot.getJSONArray("sources");

                    for (int i = 0; i < jsonSources.length(); i++) {
                        JSONObject jsonSource = jsonSources.getJSONObject(i);
                        Source source = new Source(jsonSource.getString("id"), jsonSource.getString("name"));
                        sources.add(source);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return sources;
        }

        @Override
        protected void onPostExecute(ArrayList<Source> sources) {
            progressBar.setVisibility(View.INVISIBLE);
            List<String> list_sources = new ArrayList<>();
            String text;
            for (int i = 0; i < sources.size(); i++) {
                text = sources.get(i).getName();
                list_sources.add(text);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list_sources);
            lv_sources.setAdapter(adapter);
        }
    }
}
