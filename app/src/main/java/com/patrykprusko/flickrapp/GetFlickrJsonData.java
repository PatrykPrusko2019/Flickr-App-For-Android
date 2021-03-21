package com.patrykprusko.flickrapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData extends AsyncTask< String, Void, List<Photo> > implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";
    private List<Photo> photoList;
    private OnDataAvailable callback;
    private String baseURL;
    private String language;
    private boolean matchAll;

    public GetFlickrJsonData(OnDataAvailable callback, String baseURL, String language, boolean matchAll) {
        this.callback = callback;
        this.baseURL = baseURL;
        this.language = language;
        this.matchAll = matchAll;
    }


    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }


    @Override
    protected List<Photo> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts");

        //created uri + parameters
        String newUri = createNewUri(strings[0], this.language, this.matchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(newUri);

        Log.d(TAG, "doInBackground: ends");
        return null;
    }

    // created new Uri
    private String createNewUri(String searchCriteria, String language, boolean matchAll) {

        // ?tags=android,nougat,sdk& tagmode=any& lang=us-en& format=json& nojsoncallback=1
        Uri newUri = Uri.parse(this.baseURL).buildUpon()
                                        .appendQueryParameter("tags", searchCriteria)
                                        .appendQueryParameter("tagmode", matchAll ? "all" : "any")
                                        .appendQueryParameter("lang", language)
                                        .appendQueryParameter("format", "json")
                                        .appendQueryParameter("nojsoncallback" , "1")
                                        .build();
        return newUri.toString();
    }

    @Override
    protected void onPostExecute(List<Photo> s) {
        super.onPostExecute(s);
    }

    // uses JSONArray class
    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts. Status = " + status);

        if(status == DownloadStatus.OK) {

            photoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray jsonArray = jsonData.getJSONArray("items");

                for(int i = 0 ; i < jsonArray.length(); i++) {

                    JSONObject jsonPhoto = jsonArray.getJSONObject(i); // get record object
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String author_id = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    // get photo URL
                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String imageURL = jsonMedia.getString("m");

                    String linkLargeImage = imageURL.replaceFirst("_m.", "_b."); // turns the photo into a big one

                    Photo photoObject = new Photo(title, author, author_id, linkLargeImage, imageURL, tags);
                    photoList.add( photoObject ); // adds a new record to display
                    Log.d(TAG, "onDownloadComplete: -> " + photoObject);
                }
                

            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage() );
                status = DownloadStatus.FAILED_OR_EMPTY;
            }

        } else {
            Log.d(TAG, "onDownloadComplete: data problem ");
            status = DownloadStatus.FAILED_OR_EMPTY;
        }

        Log.d(TAG, "onDownloadComplete: ends");

        callback.onDataAvailable(photoList, status);

    }


}
