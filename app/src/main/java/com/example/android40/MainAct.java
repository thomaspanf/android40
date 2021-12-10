package com.example.android40;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.android40.model.Album;
import com.example.android40.model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainAct extends AppCompatActivity implements Serializable {
    private static final String TAG = "MAIN ACTIVITY";
    public static final String storedFile = "data.dat";
    final Context context = MainAct.this;
    private static final long serialVersionUID = 458967954625L;
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    //    private ArrayAdapter<Album> albumList = new ArrayAdapter<>();
//    private ArrayAdapter<Album> dataSetArrayList = new ArrayAdapter<>();
    ArrayAdapter<Album> arrayAdapter;
    private User user;
    public static DataSource dataSource = new DataSource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            dataSource = DataSource.loadFromDisk(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(dataSource == null){
            dataSource = new DataSource();
        }
        if(dataSource.albumList == null){
            dataSource.albumList = new ArrayList<Album>();
        }

//        try {
//            load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        if(dataSource.albumList == null) {
//            try {
//                dataSource.albumList = load();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        albumAdapter = new AlbumAdapter(this, dataSource.getAlbumList());
        arrayAdapter = new ArrayAdapter<Album>(context, R.layout.list_item, dataSource.albumList);


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
                dataSource.saveToDisk(context);
            }

        }
        if (intent.hasExtra(EditActivity.EDIT_ALBUM)) {
            Album album = intent.getParcelableExtra(EditActivity.EDIT_ALBUM);
            Log.d(TAG, "onCreate: " + album);
            int position = Integer.parseInt(intent.getStringExtra(EditActivity.ALBUM_POSITION));
            dataSource.getAlbumList().get(position).setName(album.getName());
            dataSource.saveToDisk(context);
            albumAdapter.notifyDataSetChanged();

        }
//        Album newAlbum = intent.getParcelableExtra(AddActivity.ADD_ALBUM);


//        try {
//            DataSource.save(albumList);
//            Log.d(TAG, "onCreate: Datasource save");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            dataSource.setAlbumList(dataSource.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

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
            dataSource.saveToDisk(context);
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
            Album temp = dataSource.getAlbumList().get(viewHolder.getAdapterPosition());
            Intent intent = new Intent(MainAct.this, EditActivity.class);
            intent.putExtra(EditActivity.EDIT_ALBUM, (Parcelable) temp);
            intent.putExtra(EditActivity.ALBUM_POSITION, "" + viewHolder.getAdapterPosition());
            startActivity(intent);
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

    public void save(ArrayList<Album> arrayList){
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(storedFile, MODE_PRIVATE);
            //fos.write(stringBuilder.toString().getBytes());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(arrayList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public ArrayList<Album> load() throws IOException, ClassNotFoundException {
        //DataSource ds = new DataSource();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(storedFile);
            ois = new ObjectInputStream(fis);

        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return (ArrayList<Album>) ois.readObject();
    }
}