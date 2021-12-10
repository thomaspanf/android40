package com.example.android40;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android40.model.Album;
import com.example.android40.model.Photo;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    ArrayList<Album> albumList;
    Album album;
    ArrayList<Photo> photoList;
    Context context;


    public PhotoAdapter(Context ct, Album a){
        context = ct;
        album = a;
        photoList = a.getPhotoList();

    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.caption.setText(photoList.get(position).getCaption());
        holder.date.setText(photoList.get(position).getDateString());
        holder.tags.setText("");

        holder.myImage.setImageBitmap(photoList.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        TextView caption, tags, date;
        ImageView myImage;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            caption = itemView.findViewById(R.id.caption_text);
            tags = itemView.findViewById(R.id.tag_text);
            myImage = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.date_text);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SlideShowActivity.class);
                    intent.putExtra(PhotoViewActivity.POSITION, getAdapterPosition() );
                    context.startActivity(intent);
                    return;
                }
            });
        }


    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
