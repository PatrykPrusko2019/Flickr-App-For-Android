package com.patrykprusko.flickrapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {

    private List<Photo> mPhotoList;
    private Context mContext;


    /**
     *
     * @param photoList containing the data to populate views to be used by RecyclerView
     */
    public FlickrRecyclerViewAdapter(List<Photo> photoList) {
        mPhotoList = photoList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FlickrImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        //create new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);

        return new FlickrImageViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( FlickrImageViewHolder holder, int position) {
        // Get element from your mPhotoList at this position and replace the contents of the view with that element
        Photo photo = mPhotoList.get(position);
        holder.getTitle().setText(photo.getTitle());
        holder.getThumbnail().setImageURI(Uri.parse(photo.getUrlImage()));
    }


    // Return the size of your mPhotoList (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }


    /**
     * Provide a reference to the type of views that you are using
     */
    public static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        private final ImageView thumbnail;
        private final TextView title;


        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            this.thumbnail = itemView.findViewById(R.id.thumbnail_browse);
            this.title = itemView.findViewById(R.id.title_browse);
        }

        public ImageView getThumbnail() {
            return thumbnail;
        }

        public TextView getTitle() {
            return title;
        }
    }


}
