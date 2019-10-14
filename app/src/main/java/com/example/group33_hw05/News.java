package com.example.group33_hw05;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

public class News implements Serializable {
    String author, title, url, urlToImage, publishedAt;

    public News(String author, String title, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "News{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
