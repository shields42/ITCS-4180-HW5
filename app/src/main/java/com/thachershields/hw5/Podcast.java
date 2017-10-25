package com.thachershields.hw5;

/**
 * Created by thach on 10/25/2017.
 */

public class Podcast {
    protected String title;
    protected String UrlToImage;
    protected String date;

    public Podcast(){
        this.title = "Title not set";
        this.UrlToImage = "Url not set";
        this.date = "Date not set";
    }
    public Podcast(String title, String urlToImage, String date) {
        this.title = title;
        UrlToImage = urlToImage;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return UrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        UrlToImage = urlToImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
