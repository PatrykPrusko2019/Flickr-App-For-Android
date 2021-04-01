package com.patrykprusko.flickrapp;

import android.content.res.Resources;
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

        if(recordPhoto != null) {

            TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
            photoAuthor.setText( recordPhoto.getAuthor() );

            TextView photoTitle = (TextView) findViewById(R.id.photo_title);
//            photoTitle.setText("Title: " + recordPhoto.getTitle());
            Resources resources = getResources();
            String textResourceTitle = resources.getString(R.string.photo_title_text, recordPhoto.getTitle()); // strings.xml ->  <string name="photo_title_text">Title: %s</string>
            photoTitle.setText(textResourceTitle);

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
//            photoTags.setText("Tag: " + recordPhoto.getTags());
            String textResourceTags = resources.getString(R.string.photo_tags_text, recordPhoto.getTags()); // strings.xml ->  <string name="photo_tags_text">Tags: %s</string>
            photoTags.setText(textResourceTags);

            ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
            Picasso.with(this).load(recordPhoto.getLinkLargeImage()).placeholder(R.drawable.placeholder_image) // get large image
                    .error(R.drawable.placeholder_broken_image).into(photoImage);
        }


    }
}