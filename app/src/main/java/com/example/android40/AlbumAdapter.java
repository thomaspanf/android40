package com.example.android40;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android40.model.Album;
import com.example.android40.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> implements Serializable {
    private ArrayList<Album> dataSet;

    private static final long serialVersionUID = -3532483932502449961L;
    public AlbumAdapter(Context context, ArrayList<Album> albumList){
        this.dataSet = albumList;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
//        if (dataSet.isEmpty())
//            return;
//
        holder.getButtonView().setText(dataSet.get(position).getName());
//        holder.getButtonView().setText("Hello");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final Button button;

        public AlbumViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            button = view.findViewById(R.id.album_title);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = view.getContext();
                    Intent openPhotoViewer = new Intent(view.getContext(), PhotoViewActivity.class);
                    openPhotoViewer.putExtra(PhotoViewActivity.POSITION, getAdapterPosition());
                    context.startActivity(openPhotoViewer);
                }
            });
        }

        public Button getButtonView() {
            return button;
        }
    }
}
