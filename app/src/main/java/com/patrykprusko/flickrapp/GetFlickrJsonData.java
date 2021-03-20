package com.patrykprusko.flickrapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URI;
import java.util.List;

public class GetFlickrJsonData extends AsyncTask< String, Void, List<Photo> > implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";
    private List<Photo> PhotoList;
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

    @Override
    public void onDownloadComplete(String s, DownloadStatus status) {
        
    }


}
