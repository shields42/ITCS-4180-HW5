package com.thachershields.hw5;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by thach on 10/25/2017.
 */

public class GetItemsAsync extends AsyncTask<String, Void, ArrayList<Podcast>> {
    MainActivity main;

    public GetItemsAsync(MainActivity activity){this.main = activity;}

    @Override
    protected ArrayList<Podcast> doInBackground(String... params){
        HttpURLConnection connection = null;
        ArrayList<Podcast> result = new ArrayList<>();
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();


            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = PodcastParser.PodcastPullParser.parsePodcasts(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Podcast> result) {
        if (result.size() > 0) {
            //Log.d("demo", result.toString());
            super.onPostExecute(result);
            main.handlePodcasts(result);
        } else {
            Log.d("Alert", "null result");
        }
    }
}
