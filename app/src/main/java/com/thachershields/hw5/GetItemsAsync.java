package com.thachershields.hw5;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
                JSONObject root =  new JSONObject(json); //Represents object which categories is in
                JSONArray podcasts = root.getJSONArray("articles");
                for (int i = 0; i < podcasts.length(); i++)
                {
                    long tempId = 0;
                    JSONObject currentSourceObject = podcasts.getJSONObject(i);
                    Podcast currentPodcast = new Podcast();

                    try{
                        currentPodcast.setTitle(currentSourceObject.getString("author"));
                        currentPodcast.setDate(currentSourceObject.getString("publishedAt"));
                        currentPodcast.setUrlToImage(currentSourceObject.getString("urlToImage"));

                    } catch(Exception e){
                        System.out.println("Error in building source");
                    }


                    result.add(currentPodcast);

                    Log.d("demo", String.valueOf(currentPodcast.getTitle()));
                }
                return result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> result) {
        if (result.size() > 0) {
            Log.d("demo", result.toString());
            super.onPostExecute(result);
            main.handleArticle(result);
        } else {
            Log.d("demo", "null result");
        }
    }
}
