package com.example.group33_hw05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.List;

/*
Homework 05
Group33_HW05.zip
Nick DeBakey & Lis Rizvanolli
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.model, parent, false);
        }

        ImageView iv_newsImage = (ImageView) convertView.findViewById(R.id.iv_newsImage);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_author = (TextView) convertView.findViewById(R.id.tv_author);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);

        Picasso.get().load(news.urlToImage).into(iv_newsImage);
        tv_title.setText(news.title);
        tv_author.setText(news.author);
        tv_date.setText(news.publishedAt);

        return convertView;
    }
}
