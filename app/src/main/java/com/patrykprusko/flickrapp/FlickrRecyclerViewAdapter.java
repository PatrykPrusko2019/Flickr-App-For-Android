package com.patrykprusko.flickrapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * using ReyclerView with an adapter takes views (eg CardView) and data and combines them using ViewHolder (class FlickrImageViewHolder).
 */
class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {

    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotoList;
    private Context mContext;


    /**
     *
     * @param photoList containing the data to populate views to be used by RecyclerView
     */
    public FlickrRecyclerViewAdapter(Context context , List<Photo> photoList) {
        mContext = context;
        mPhotoList = photoList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FlickrImageViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        //create new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);

        return new FlickrImageViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( FlickrImageViewHolder holder, int position) {
        // Get element from your mPhotoList at this position and replace the contents of the view with that element

        if( mPhotoList == null || mPhotoList.size() == 0 ) {
            Log.d(TAG, "onBindViewHolder: empty image");
            holder.getThumbnail().setImageResource(R.drawable.placeholder_broken_image);
            holder.getTitle().setText(R.string.empty_photo);
        } else {
            Photo photoItem = mPhotoList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + " ---> " + position);
            holder.getTitle().setText(photoItem.getTitle()); // get title

            Picasso.with(mContext).load(photoItem.getUrlImage()).placeholder(R.drawable.placeholder_image) // use the Picasso library to download a photo
                    .error(R.drawable.placeholder_broken_image).into(holder.getThumbnail());
        }
    }


    // Return the size of your mPhotoList (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");

        return  (mPhotoList != null) && (mPhotoList.size() != 0) ? mPhotoList.size() : 0 ;
    }

    // get actuals record photo , which to display in activity -> PhotoDetailActivity
    Photo getPhoto(int position) {
        if(position < mPhotoList.size() && position > -1) {
            return mPhotoList.get(position);
        } else {
            Log.d(TAG, "getPhoto: return null");
            return null;
        }
    }


     void loadNewData(List<Photo> data) {
        mPhotoList = data;
        notifyDataSetChanged();

    }


    /**
     * Provide a reference to the type of views that you are using
     */
     static class FlickrImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail;
        TextView title;


        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }

        public ImageView getThumbnail() {
            return thumbnail;
        }

        public TextView getTitle() {
            return title;
        }
    }






}
