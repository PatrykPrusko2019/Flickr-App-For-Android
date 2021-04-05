package com.patrykprusko.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK
}

class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetRawData";

    private final OnDownloadComplete mCallback;
    private DownloadStatus mDownloadStatus;

    public GetRawData(OnDownloadComplete callBacks) {
        mCallback = callBacks;
        mDownloadStatus = DownloadStatus.IDLE;
    }

    interface OnDownloadComplete {
        void onDownloadComplete(String s, DownloadStatus status);
    }

    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread: starts");
        if(mCallback != null) {
           mCallback.onDownloadComplete( doInBackground(s) , mDownloadStatus); // goes to the GetFlickrJsonData class
            // Log.d(TAG, "runInSameThread: results: " + result); // shows results
        }
        Log.d(TAG, "runInSameThread: ends");
    }

    @Override
    protected String doInBackground(String... strings) {

        Log.d(TAG, "doInBackground: starts");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        URL url = null;

        if(strings == null) {
            this.mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        } else {


            try {
                this.mDownloadStatus = DownloadStatus.PROCESSING;

                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.d(TAG, "doInBackground: connected -> " + connection.getResponseMessage());

                StringBuilder result = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


                for (String line = reader.readLine() ; null != line; line = reader.readLine()  ) {
                    result.append(line + "\n");
                }
                this.mDownloadStatus = DownloadStatus.OK;
                return result.toString();

            } catch (MalformedURLException e) {
                Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage());
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
