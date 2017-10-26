package com.thachershields.hw5;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by thach on 10/26/2017.
 */

public class PodcastAdapter extends ArrayAdapter<Podcast> {

    public PodcastAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Podcast> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Podcast podcast = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
        ImageView imgImage = (ImageView) convertView.findViewById(R.id.imgImage);

        lblTitle.setText(podcast.getTitle());

        Picasso.with(getContext()).load(podcast.getUrlToImage()).into(imgImage);

        return convertView;
    }
}
