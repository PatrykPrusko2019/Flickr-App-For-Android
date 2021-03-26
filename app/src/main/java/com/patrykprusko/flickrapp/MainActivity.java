package com.patrykprusko.flickrapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//klasa nie moja z kursu
/**
 * start project FlickrApp
 */
public class MainActivity extends AppCompatActivity implements GetFlickrJsonData.OnDataAvailable {

    private static final String TAG = "MainActivity";

    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
       recyclerView.setLayoutManager(new LinearLayoutManager(this)); // sets the manager layout

       mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
       recyclerView.setAdapter(mFlickrRecyclerViewAdapter); //sets adapter


        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
        getFlickrJsonData.execute("android,nougat"); // creates URL + search parameters to be able to retrieve using JSON objects




        Log.d(TAG, "onCreate: ends");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu: returned : " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        
        if(status == DownloadStatus.OK) {
            Log.d(TAG, "onDataAvailable: status -> " + status + ", starts loadNewData with class FlickrRecyclerViewAdapter");
            mFlickrRecyclerViewAdapter.loadNewData(data);

        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }
}