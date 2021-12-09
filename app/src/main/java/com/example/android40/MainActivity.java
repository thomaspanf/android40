package com.example.android40;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android40.data.DataSource;
import com.example.android40.model.Album;
import com.example.android40.model.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "MAIN ACTIVITY";

    private static final long serialVersionUID = 458967954625L;
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private ArrayList<Album> albumList = new ArrayList<>();
    private ArrayList<Album> dataSetArrayList = new ArrayList<Album>();
    private User user;
    public static DataSource  dataSource = new DataSource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


//        try {
//            DataSource.load();
//            Log.d(TAG, "onCreate: Datasource load");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        recyclerView = findViewById(R.id.recycler_view);
        albumAdapter = new AlbumAdapter(this, dataSource.getAlbumList());
        new ItemTouchHelper(itemTouchHelperCallbackLeft).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(itemTouchHelperCallbackRight).attachToRecyclerView(recyclerView);


        Intent intent = getIntent();

        if(intent.hasExtra(AddActivity.ADD_ALBUM)){
            boolean catcher = false;
            String newAlbumTitle = intent.getStringExtra(AddActivity.ADD_ALBUM);
            for(Album a : dataSource.getAlbumList()){
                if(a.getName().equals(newAlbumTitle)){
                    catcher = true;
                }
            }
            if(!catcher){
                dataSource.addAlbum(new Album(newAlbumTitle));
                Log.d(TAG, "onCreate: " + dataSource.getAlbumList().size());
                try {
                    dataSource.save(dataSource.getAlbumList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
//        Album newAlbum = intent.getParcelableExtra(AddActivity.ADD_ALBUM);


//        try {
//            DataSource.save(albumList);
//            Log.d(TAG, "onCreate: Datasource save");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            dataSource.setAlbumList(dataSource.load());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(albumAdapter);

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallbackLeft = new ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            dataSource.getAlbumList().remove(viewHolder.getAdapterPosition());
            try {
                dataSource.save(dataSource.getAlbumList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            albumAdapter.notifyDataSetChanged();
            // TODO:

        }
    };

    ItemTouchHelper.SimpleCallback itemTouchHelperCallbackRight = new ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.d(TAG, "onSwiped: Swiping Right");
            albumAdapter.notifyDataSetChanged();
            // TODO: When we swipe right, we go to EditActivity
            // or, we click on button and edit it through GalleryActivity

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Log.d(TAG, "onOptionsItemSelected: clicked on Plus");
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}