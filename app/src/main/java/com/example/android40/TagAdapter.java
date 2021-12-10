package com.example.android40;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android40.model.Album;
import com.example.android40.model.Photo;
import com.example.android40.model.Tag;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder>{
    private Context context;
    private ArrayList<Tag> tags;
    private Album currentAlbum;
    private Photo currentPhoto;
    private int position;

    public TagAdapter(Context context, Album ca, int position){
        this.context = context;
        this.currentAlbum = ca;
        this.currentPhoto = ca.getPhotoList().get(position);
        this.tags = ca.getPhotoList().get(position).getSortedTags();
        this.position = position;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.TagViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.setDetails(tag);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    class TagViewHolder extends RecyclerView.ViewHolder{
        private TextView tagName;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.text_id);
        }

        void setDetails(Tag tag){
            String temp = tag.getType()+ " : " + tag.getDesc();
            tagName.setText(temp);
        }
    }

//    Photo photo;
//    Context context;
//    ArrayList<Tag> tags;
//    int position;
//    Album currentAlbum;
//
//    public TagAdapter(Context ct, Album currentAlbum, int position){
//        context = ct;
//        this.position = position;
//        this.currentAlbum = currentAlbum;
//        photo = currentAlbum.getPhotoList().get(position);
//        tags = currentAlbum.getPhotoList().get(position).getSortedTags();
//    }
//
//    @NonNull
//    @Override
//    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.from(parent.getContext())
//                .inflate(R.layout.album, parent, false);
//
//        return new TagViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
//        String temp = tags.get(position).getType() + ":" + tags.get(position).getDesc();
//        holder.tagText.setText(temp);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return tags.size();
//    }
//
//    public class TagViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tagText;
//
//        public TagViewHolder(View view) {
//            super(view);
//            tagText = view.findViewById(R.id.tag_name);
//
////            itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Intent intent = new Intent(v.getContext(), SlideShowActivity.class);
////                    intent.putExtra(PhotoViewActivity.POSITION, getAdapterPosition());
////                    context.startActivity(intent);
////                    return;
////                }
////            });
//        }
//    }
}
