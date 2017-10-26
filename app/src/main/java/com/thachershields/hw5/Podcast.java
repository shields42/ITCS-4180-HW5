package com.thachershields.hw5;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thach on 10/25/2017.
 */

public class Podcast implements Serializable, Comparable<Podcast>{
    protected String title;
    protected String urlToImage;
    protected String date;
    protected String summary;
    protected String realDate;
    protected Date   compareDate;

    public Podcast(){
        this.title = "Title not set";
        this.urlToImage = "Url not set";
        this.date = "Date not set";
        this.summary = "Summary not set";
    }
    public Podcast(String title, String urlToImage, String date, String summary, String realDate) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.date = date;
        this.summary = summary;
        this.realDate = realDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary(){
        return summary;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getRealDate(){
        return realDate;
    }

    public void setRealDate(String realDate){
        this.realDate = realDate;
    }

    @Override
    public String toString(){
        String output;
        output = "Title: "+this.getTitle() + "\n" + "Date: "+this.getDate() + "\n" + "Image: "+this.getUrlToImage() + "\n" + "Summary: "+ this.getSummary() + "\n";
        return output;
    }

    @Override
    public int compareTo(@NonNull Podcast o) {
        try{
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-M-dd'T'HH:mm:ss");
            this.compareDate = parser.parse(this.getDate());
            o.compareDate = new SimpleDateFormat("yyyy-M-dd'T'HH:mm:ss").parse(o.getDate());
            return this.compareDate.compareTo(o.compareDate);
        } catch (ParseException p){
            System.out.println("Error converting date format");
            return 0;
        }
    }
}
