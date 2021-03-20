package com.patrykprusko.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//todo next

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK;
}

public class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetRawData";

    private final OnDownloadComplete mCallback;
    private DownloadStatus mDownloadStatus;

    interface OnDownloadComplete {
        void onDownloadComplete(String s, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callBacks) {
        mCallback = callBacks;
        mDownloadStatus = DownloadStatus.IDLE;
    }

    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread: starts");
        if(mCallback != null) {
            doInBackground(s);
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        Log.d(TAG, "doInBackground: starts");
        BufferedReader reader = null;
        URL url = null;

        if(strings[0] == null) {
            this.mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        } else {

            this.mDownloadStatus = DownloadStatus.PROCESSING;

            try {
                url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.d(TAG, "doInBackground: connected -> " + connection.getResponseMessage());

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder builder = new StringBuilder();

                for (String line = reader.readLine() ; null != line; line = reader.readLine()  ) {
                    builder.append(line + "\n");
                }
                this.mDownloadStatus = DownloadStatus.OK;
                return builder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
