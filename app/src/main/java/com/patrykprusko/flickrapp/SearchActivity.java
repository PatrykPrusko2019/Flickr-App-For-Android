package com.patrykprusko.flickrapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.activateToolbar(true);

    }




    public void onClickedButtonResult(View view) {
        Log.d(TAG, "onClickedButtonResult: start choose new criteria");

        EditText valueSearch = (EditText) findViewById(R.id.value_search);
        result = valueSearch.getText().toString();

        if ( result.length() > 0 ) {
            Toast.makeText(this, "new value search -> " + result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "new value search -> empty choose user" + result, Toast.LENGTH_SHORT).show();
            result = getIntent().getStringExtra(FLICKR_QUERY); // get previous user search
            Log.d(TAG, "onClickedButtonResult: " + result);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString(FLICKR_QUERY, result).apply(); //save the result

        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }
}