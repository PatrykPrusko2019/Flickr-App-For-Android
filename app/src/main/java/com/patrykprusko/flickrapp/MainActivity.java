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
 *
 *An application that uses the flickr.com portal to download photos and data from it, and the ability to change the search word.
 *
 * The exact operation of the application:
 * 1. The main activity begins, searches for the last entered word, and displays the results on the screen. It uses an object of the SharedPreferences type, which saves
 * the searched word, and after turning off and restarting the application, restores the previous search word.
 * 2.The data displayed on the main activity are:
 * - thumbnail (urlImage)
 * - title
 * which are downloaded as follows:
 * Then, when the resultSearch value has a word, it uses the GetFlickrJsonData class object, in which a new URI + parameters are created. Then, in the GetRawData class object,
 * we retrieve new data from the URL, which is then retrieved in the GetFlickrJsonData class using JSONArray, creating records of the Photo class and
 * saving it in the photoList list.
 *
 * 3. Then, when the list with data is created, load the RecyclerView object, set up the manager layout, create the adapter and link with RecyclerView.
 * RecyclerView is responsible for displaying the list of items from photoList.
 *
 * 4. In the FlickrRecyclerViewAdapter, you create a new list item view, then create a ViewHolder object that finds and stores view references -> thumbnail and title.
 * Loads data (photoList) from the specified position into views whose references are stored in the given view holder (the FlickrImageViewHolder object).
 *
 * 5. The RecyclerItemClickListener class creates a listener that uses the GestureDetector object, thanks to which it supports gesture detection:
 * - single click
 * - longer click
 *
 * 6. Summary of operation:
 *  - If the user makes the gesture of a long click on a record from the photoList, then he goes to the new PhotoDetailActivity, thanks to which detailed information about
 *  the given photo record is displayed:
 * - author
 * - title
 * - photoLarge
 * - tag
 *
 * - You can enter the button search button in the toolbar, which goes to the SearchActivity search activity, where you can enter a new word that will be searched.
 * If there is too much words, e.g. android, green, cars, Poland, boys, women, white, nothing will be found, it will only show an empty photo. If it finds new objects,
 * it will display them in the main activity.
 * 7. An additional option, if the user has not selected anything, will be to show the previous user selection.
 *
 * 8. Then I used www.materialpalette.com and material.io to change the graphics of the application.
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
            intent.putExtra(FLICKR_QUERY, resultSearch); // saves the last choice searched by user
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