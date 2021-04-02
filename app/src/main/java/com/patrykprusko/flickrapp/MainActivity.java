package com.patrykprusko.flickrapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * start project FlickrApp
 */
public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "MainActivity";

    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;
    private String resultSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);

       RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
       recyclerView.setLayoutManager(new LinearLayoutManager(this)); // sets the manager layout

       recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));


       mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
       recyclerView.setAdapter(mFlickrRecyclerViewAdapter); //sets adapter


        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String criteriaByUser = sharedPreferences.getString(FLICKR_QUERY, "");

        if(criteriaByUser != null) resultSearch = criteriaByUser;

        Log.d(TAG, "onResume: starts -> new search value : " + this.resultSearch);

        if(resultSearch.length() > 0) {
            Log.d(TAG, "onResume: user chose correctly !");
            GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
            getFlickrJsonData.execute(resultSearch); // creates URL + search parameters to be able to retrieve using JSON objects
        }
        Log.d(TAG, "onResume: ends");
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
        if (id == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(FLICKR_QUERY, resultSearch);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");

        if(status == DownloadStatus.OK) {
            Log.d(TAG, "onDataAvailable: status -> " + status + ", starts method loadNewData with class 's FlickrRecyclerViewAdapter");
            mFlickrRecyclerViewAdapter.loadNewData(data);

        } else {
            // download or processing failed
            Toast.makeText(this, "onDataAvailable failed with status ", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onDataAvailable failed with status " + status + ", and data size() is -> " + data.size() + ". Problem with status or data" );
        }

        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Toast.makeText(MainActivity.this, "Normal tap at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: starts");
        Toast.makeText(MainActivity.this, "Normal tap at position " + position, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, PhotoDetailActivity.class); // jump next activity -> PhotoDetailActivity
            intent.putExtra(PHOTO_TRANSFER, mFlickrRecyclerViewAdapter.getPhoto(position));
            startActivity(intent);

    }
}