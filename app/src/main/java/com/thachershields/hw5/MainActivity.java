package com.thachershields.hw5;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progress;
    ListView lvResults;
    EditText txtSearch;
    Button btnGo;
    Button btnClear;
    ArrayList<Podcast> podcastList = null;
    ArrayList<Podcast> searchList = null;
    ArrayList<Podcast> backupList = null;
    PodcastAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSearch = (EditText)findViewById(R.id.txtSearch);
        btnGo = (Button)findViewById(R.id.btnGo);
        btnClear = (Button)findViewById(R.id.btnClear);

        progress = new ProgressDialog(MainActivity.this);
        progress.setCancelable(false);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        if(isConnected()){
            Toast.makeText(MainActivity.this, "Internet Connected", Toast.LENGTH_SHORT).show();
            progress.setTitle("Loading Data...");
            progress.show();
            new GetItemsAsync(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/xml"); //Insert URL

        } else {
            Toast.makeText(MainActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
        }

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                searchList = new ArrayList<Podcast>();
                podcastList.clear();
                for(int i = 0; i < backupList.size(); i++){
                    if(backupList.get(i).getTitle().contains(txtSearch.getText())){
                        podcastList.add(backupList.get(i));
                    }
                }
                Collections.sort(podcastList);
                ((PodcastAdapter)lvResults.getAdapter()).notifyDataSetChanged();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                podcastList.clear();
                txtSearch.setText("");
                for(int i = 0; i < backupList.size(); i++){
                    if(backupList.get(i).getTitle().contains("")){
                        podcastList.add(backupList.get(i));
                    }
                }
                Collections.sort(podcastList);
                ((PodcastAdapter)lvResults.getAdapter()).notifyDataSetChanged();
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


    protected void handlePodcasts(final ArrayList<Podcast> result){
        podcastList = (ArrayList<Podcast>)result.clone();
        backupList = (ArrayList<Podcast>)result.clone();

        Collections.sort(podcastList);

        /*
        for(int i = 0; i<podcastList.size(); i++){
            System.out.println(podcastList.get(i).toString());
        }
        */

        lvResults = (ListView) findViewById(R.id.lvResults);
        adapter = new PodcastAdapter(this, R.layout.list_item, podcastList);
        lvResults.setAdapter(adapter);
        progress.dismiss();

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("Podcast", podcastList.get(position));
                startActivity(intent);
            }
        });
    }
}