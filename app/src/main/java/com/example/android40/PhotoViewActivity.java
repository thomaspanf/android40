package com.example.android40;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PhotoViewActivity extends AppCompatActivity {

    public static final String POSITION = "";
    private int position;
    private TextView albumName;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        Intent intent = getIntent();
        if (intent.hasExtra(POSITION)){
            position = intent.getIntExtra(POSITION, 0);
        }

        albumName = findViewById(R.id.album_title);

        albumName.setText(MainActivity.dataSource.albumList.get(position).getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            //Log.d(TAG, "onOptionsItemSelected: clicked on Plus");
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}