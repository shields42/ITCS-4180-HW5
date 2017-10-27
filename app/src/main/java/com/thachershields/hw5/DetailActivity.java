package com.thachershields.hw5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private TextView lblTitle;
    private TextView lblDate;
    private ImageView imgImage;
    private TextView lblSummary;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lblTitle = (TextView) findViewById(R.id.lblDetailTitle);
        lblDate = (TextView) findViewById(R.id.lblDetailDate);
        imgImage = (ImageView) findViewById(R.id.imgDetailImage);
        lblSummary = (TextView) findViewById(R.id.lblDetailSummary);

        progress = new ProgressDialog(DetailActivity.this);
        progress.setMessage("Loading image...");

        Intent i = getIntent();

        Podcast podcast = (Podcast)i.getSerializableExtra("Podcast");

        String xmlDate = podcast.getDate();
        try {
            Date date = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss").parse(xmlDate);
            String realDate = new SimpleDateFormat("MM/dd/yyy h:MM aa").format(date);

            lblDate.setText(realDate);

        }
        catch (ParseException p){

        }

        lblTitle.setText(podcast.getTitle());
        progress.show();
        Picasso.with(DetailActivity.this).load(podcast.getUrlToImage()).into(imgImage,new com.squareup.picasso.Callback() {;
            @Override
            public void onSuccess() {
                progress.dismiss();
            }

            @Override
            public void onError() {
                progress.dismiss();
            }
        });
        lblSummary.setText(podcast.getSummary());

    }
}
