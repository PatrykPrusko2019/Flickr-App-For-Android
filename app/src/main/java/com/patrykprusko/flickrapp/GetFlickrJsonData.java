package com.patrykprusko.flickrapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class GetFlickrJsonData extends AsyncTask< String, Void, List<Photo>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";

    private List<Photo> photoList = null;
    private String baseURL;
    private String language;
    private boolean matchAll;

    private final OnDataAvailable callback;

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    public GetFlickrJsonData(OnDataAvailable callback, String baseURL, String language, boolean matchAll) {
        this.callback = callback;
        this.baseURL = baseURL;
        this.language = language;
        this.matchAll = matchAll;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: starts");

        if(callback != null) {
            callback.onDataAvailable(photoList, DownloadStatus.OK);
        }

        Log.d(TAG, "onPostExecute: ends");
    }


    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");

        //created uri + parameters
        String newUri = createNewUri(params[0], language, matchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(newUri);

        Log.d(TAG, "doInBackground: ends");
        return photoList;
    }

    // created new Uri
    private String createNewUri(String searchCriteria, String language, boolean matchAll) {
        Log.d(TAG, "createUri starts -> " + searchCriteria);
        // ?tags=android,nougat,sdk& tagmode=any& lang=us-en& format=json& nojsoncallback=1
//        Uri newUri = Uri.parse(baseURL).buildUpon()
//                                        .appendQueryParameter("tags", searchCriteria)
//                                        .appendQueryParameter("tagmode", matchAll ? "all" : "any")
//                                        .appendQueryParameter("lang", language)
//                                        .appendQueryParameter("format", "json")
//                                        .appendQueryParameter("nojsoncallback" , "1")
//                                        .build();

        //second way
        return Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", language)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
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
            }

        } else {
            Log.d(TAG, "onDownloadComplete: data problem ");
        }


        Log.d(TAG, "onDownloadComplete: ends");
    }


}
