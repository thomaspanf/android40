package com.example.android40;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android40.model.Album;
import com.example.android40.model.Photo;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class PhotoViewActivity extends AppCompatActivity {

    public static final String POSITION = "";
    private static final int SELECT_PHOTO = 1;
    private static final String TAG = "PHOTO ACTIVITY";
    private static Album currentAlbum;
    private int position;
    private TextView albumName;
    private Button addButton;
    Context context = this;
    private PhotoAdapter pa;
    RecyclerView recyclerView;
    //private Album currentAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        Intent intent = getIntent();
        if (intent.hasExtra(POSITION)){
            position = intent.getIntExtra(POSITION, 0);
        }

        albumName = findViewById(R.id.album_title);

        currentAlbum = null;
        if(MainActivity.dataSource.albumList.size() != 0){
            currentAlbum = MainActivity.dataSource.albumList.get(position);
            albumName.setText(MainActivity.dataSource.albumList.get(position).getName());
            pa = new PhotoAdapter(context, MainActivity.dataSource.albumList.get(position));
            pa.notifyDataSetChanged();
        }

        addButton = findViewById(R.id.add_button);
        addButton.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.recycler_view_photo);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, SELECT_PHOTO);
//                MainActivity.dataSource.saveToDisk(context);
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

            }
        });

        recyclerView.setAdapter(pa);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        new ItemTouchHelper(itemTouchHelperCallbackLeft).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(itemTouchHelperCallbackRight).attachToRecyclerView(recyclerView);

        //pa.notifyDataSetChanged();
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
            currentAlbum.getPhotoList().remove(viewHolder.getAdapterPosition());
            MainActivity.dataSource.saveToDisk(PhotoViewActivity.this);
            pa.notifyDataSetChanged();
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
//            Album temp = MainActivity.dataSource.getAlbumList().get(viewHolder.getAdapterPosition());
//            Intent intent = new Intent(MainActivity.this, EditActivity.class);
//            intent.putExtra(EditActivity.EDIT_ALBUM, (Parcelable) temp);
//            intent.putExtra(EditActivity.ALBUM_POSITION, "" + viewHolder.getAdapterPosition());
//            startActivity(intent);
            // TODO: When we swipe right, we go to EditActivity
            // or, we click on button and edit it through GalleryActivity
            pa.notifyDataSetChanged();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if(resultCode == RESULT_OK){

            Uri selectedImage = imageReturnedIntent.getData();

            ImageView iv = new ImageView(this);

            iv.setImageURI(selectedImage);

            BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
            Bitmap selectedImageGal = drawable.getBitmap();

            File f = new File(selectedImage.getPath());
            String pathID = f.getAbsolutePath();
            String filename = pathToFileName(selectedImage);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImageGal.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Photo photoToAdd = new Photo(pathID, filename, MainActivity.dataSource.albumList.get(position).getName(), f, stream.toByteArray());

            Bitmap image = BitmapFactory.decodeByteArray(photoToAdd.imageByteArray, 0, photoToAdd.imageByteArray.length);
            photoToAdd.setImage(selectedImageGal);

            MainActivity.dataSource.albumList.get(position).addPhoto(photoToAdd);
            MainActivity.dataSource.saveToDisk(context);

            recyclerView.setAdapter(pa);

        }
    }

    public static Album getCurrentAlbum(){
        return currentAlbum;
    }

    public static void setCurrentAlbum(Album a){
        currentAlbum = a;
    }

    private String pathToFileName(Uri selectedImage){


        String filename = "not found";

        String[] column = {MediaStore.MediaColumns.DISPLAY_NAME};

        ContentResolver contentResolver = getApplicationContext().getContentResolver();

        Cursor cursor = contentResolver.query(selectedImage, column,
                null, null, null);

        if(cursor != null) {
            try {
                if (cursor.moveToFirst()){
                    filename = cursor.getString(0);
                }
            } catch (Exception e){

            }
        }

        return filename;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
//            Log.d(TAG, "onOptionsItemSelected: clicked on Plus");
//            Intent intent = new Intent(this, AddActivity.class);
//            startActivity(intent);
//            MainActivity.dataSource.saveToDisk(PhotoViewActivity.this);
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
            return true;
        }
        if(item.getItemId() == R.id.action_search){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            MainActivity.dataSource.saveToDisk(PhotoViewActivity.this);
            return true;
        }
        if(item.getItemId() == R.id.action_refresh){
//            Intent intent = new Intent(this, PhotoViewActivity.class);
//            startActivity(intent);
            pa.notifyDataSetChanged();
            MainActivity.dataSource.saveToDisk(PhotoViewActivity.this);
            return true;
        }
        if(item.getItemId() == R.id.action_home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}