package com.example.android40;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
