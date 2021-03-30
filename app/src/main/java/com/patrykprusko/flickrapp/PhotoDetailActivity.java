package com.patrykprusko.flickrapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/*
displays a single photo image
 */
public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        activateToolbar(true);

        // adds new data for the current record (photo in detail)

        Photo recordPhoto = (Photo) getIntent().getSerializableExtra(PHOTO_TRANSFER); // get object Photo

        TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
        TextView photoTitle = (TextView) findViewById(R.id.photo_title);
        TextView photoTags = (TextView) findViewById(R.id.photo_tags);
        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);

        if(recordPhoto != null) {
            photoAuthor.setText("Author: " + recordPhoto.getAuthor());
            photoTitle.setText("Title: " + recordPhoto.getTitle());
            photoTags.setText("Tag: " + recordPhoto.getTags());

            Picasso.with(this).load(recordPhoto.getLinkLargeImage()).placeholder(R.drawable.placeholder_image) // get large image
                    .error(R.drawable.placeholder_broken_image).into(photoImage);
        }


    }
}