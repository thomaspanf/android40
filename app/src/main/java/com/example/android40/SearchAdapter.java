package com.example.android40;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android40.model.Album;
import com.example.android40.model.Photo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<Photo> searchedPhotos;
    private Context context;
    private String searchedAlbumName;

    public SearchAdapter(Context context, ArrayList<Photo> sp, String searchedAlbum){
        this.context = context;
        this.searchedPhotos = sp;
        this.searchedAlbumName = searchedAlbum;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photo_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        holder.caption.setText(searchedPhotos.get(position).getCaption());
        holder.date.setText(searchedPhotos.get(position).getDateString());
        holder.tags.setText("");
//        if(photoList.get(position).getSortedTags().size()>0){
//            holder.tags.setText(photoList.get(position).getSortedTags().get(0).getDesc());
//        }
//        else{
//
//        }
        //holder.myImage.setImageURI(Uri.fromFile(new File(searchedPhotos.get(position).getName())));
        Bitmap image = BitmapFactory.decodeByteArray(searchedPhotos.get(position).imageByteArray, 0, searchedPhotos.get(position).imageByteArray.length);
        holder.myImage.setImageBitmap(image);


    }

    @Override
    public int getItemCount() {
        return searchedPhotos.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView caption, tags, date;
        ImageView myImage;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            caption = itemView.findViewById(R.id.caption_text);
            tags = itemView.findViewById(R.id.tag_text);
            myImage = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.date_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SlideShowActivity.class);
                    intent.putExtra(PhotoViewActivity.POSITION, getAdapterPosition());
                    intent.putExtra("Searched Album", searchedAlbumName);
                    intent.putExtra(SearchActivity.SEARCHED_ALBUM_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }


    }

    public void clear() {
        int size = searchedPhotos.size();
        searchedPhotos.clear();
        notifyItemRangeRemoved(0, size);
    }

}
