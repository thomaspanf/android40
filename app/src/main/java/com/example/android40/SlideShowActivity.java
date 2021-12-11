package com.example.android40;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android40.model.Album;
import com.example.android40.model.Photo;
import com.example.android40.model.Tag;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlideShowActivity extends AppCompatActivity implements Serializable {

    private static final String POSITION = "";
    private ImageView imageView;
    private ImageButton leftButton;
    private ImageButton rightButton;
    private Button change;
    private TextView caption;
    private EditText editText;
    private RecyclerView tagList;
    private Button tagConfirmButton;
    private int position;
    private static Album currentAlbum;
    private ArrayAdapter<Tag> tagArrayAdapter;
    private EditText tagDescription;
    private Spinner spinner;
    private Spinner albumSpinner;
    private Button moveButton;
    private Button copyButton;
    private final Context context = this;
    private TagAdapter ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        Log.d("SLIDESHOW", "onCreate: running");
        Intent intent = getIntent();
        if (intent.hasExtra(POSITION)){
            position = intent.getIntExtra(POSITION, 0);
        }
        currentAlbum = PhotoViewActivity.getCurrentAlbum();
        if(intent.hasExtra("Searched Album")){
            for(Album a : MainActivity.dataSource.getAlbumList()) {
                if(a.getName().equals("Searched Album")){
                    currentAlbum = a;
                }
            }
            PhotoViewActivity.setCurrentAlbum(currentAlbum);
            position = intent.getIntExtra(SearchActivity.SEARCHED_ALBUM_POSITION, 0);
            //Log.d("SLIDESHOW", "onCreate: " + currentAlbum.getName());
        }

        //Log.d("SLIDESHOW", "onCreate: " + currentAlbum.getName());

        imageView = findViewById(R.id.imageView2);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        change = findViewById(R.id.change_cap_button);
        editText = findViewById(R.id.caption_text_field);
        caption = findViewById(R.id.caption_field);

        albumSpinner = findViewById(R.id.albums_spinner);
        final HashMap<String, Album> stringAlbumList = new HashMap<>();
        for(Album a : MainActivity.dataSource.getAlbumList()){
            if(a.getName() != currentAlbum.getName()){
                stringAlbumList.put(a.getName(), a);
            }
        }
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<>(stringAlbumList.keySet()));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        albumSpinner.setAdapter(spinnerAdapter);

        moveButton = findViewById(R.id.move_button);
//        copyButton = findViewById(R.id.copy_button);



        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumName = albumSpinner.getSelectedItem().toString();
                if(stringAlbumList.containsKey(albumName)){

                    stringAlbumList.get(albumName).addPhoto(currentAlbum.getPhotoList().get(position));

                    currentAlbum.getPhotoList().remove(position);
                    //position = 0;

                    Intent intent = new Intent(SlideShowActivity.this, MainActivity.class);
                    context.startActivity(intent);
                    MainActivity.dataSource.saveToDisk(context);

                    ta.notifyDataSetChanged();
                }
            }
        });

        tagConfirmButton = findViewById(R.id.tag_confirm_button);
        tagDescription = findViewById(R.id.add_tag_tf);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        tagList = findViewById(R.id.tag_list);
        tagList.setLayoutManager(new LinearLayoutManager(this));
        //tagArrayAdapter = new ArrayAdapter<Tag>(this, R.layout.album, currentAlbum.getPhotoList().get(position).getSortedTags());
        ta = new TagAdapter(this, currentAlbum, position);
        tagList.setAdapter(ta);
        tagList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        createListData();
        ta.notifyDataSetChanged();

        tagConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tagDescription.getText().toString().isEmpty()){
                    Toast.makeText(SlideShowActivity.this, "Please enter a valid tag", Toast.LENGTH_LONG).show();
                    return;
                }
                for(Tag t : currentAlbum.getPhotoList().get(position).getSortedTags()) {
                    if(t.getType().equals(spinner.getSelectedItem().toString())
                            && t.getDesc().equals(tagDescription.getText().toString()) ){
                        Toast.makeText(SlideShowActivity.this, "Tag already exist", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Tag newTag = new Tag(spinner.getSelectedItem().toString(), tagDescription.getText().toString().toLowerCase());
                currentAlbum.getPhotoList().get(position).addTag(newTag);
                MainActivity.dataSource.saveToDisk(SlideShowActivity.this);
                ta.notifyDataSetChanged();
                //tagListView.notify();
            }
        });

        new ItemTouchHelper(itemTouchHelperCallbackLeft).attachToRecyclerView(tagList);

        caption.setText(currentAlbum.getPhotoList().get(position).getCaption());
        editText.setText(currentAlbum.getPhotoList().get(position).getCaption());
        //imageView.setImageURI(Uri.fromFile(new File(currentAlbum.getPhotoList().get(position).getName())));
        Bitmap image = BitmapFactory.decodeByteArray(currentAlbum.getPhotoList().get(position).imageByteArray, 0, currentAlbum.getPhotoList().get(position).imageByteArray.length);
        imageView.setImageBitmap(image);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCaption = editText.getText().toString();
                if(newCaption.isEmpty()) {
                    Toast.makeText(SlideShowActivity.this, "Caption should not be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                currentAlbum.getPhotoList().get(position).setCaption(newCaption);
                caption.setText(currentAlbum.getPhotoList().get(position).getCaption());
                editText.setText(currentAlbum.getPhotoList().get(position).getCaption());
                MainActivity.dataSource.saveToDisk(SlideShowActivity.this);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > 0){
                    position--;
                } else {
                    position = currentAlbum.getPhotoList().size()-1;
                }
                imageView.setImageURI(Uri.fromFile(new File(currentAlbum.getPhotoList().get(position).getName())));
                Bitmap image = BitmapFactory.decodeByteArray(currentAlbum.getPhotoList().get(position).imageByteArray, 0, currentAlbum.getPhotoList().get(position).imageByteArray.length);
                imageView.setImageBitmap(image);
                caption.setText(currentAlbum.getPhotoList().get(position).getCaption());
                editText.setText(currentAlbum.getPhotoList().get(position).getCaption());
                ta = new TagAdapter(SlideShowActivity.this, currentAlbum, position);
                tagList.setAdapter(ta);
                tagList.addItemDecoration(new DividerItemDecoration(SlideShowActivity.this, LinearLayoutManager.VERTICAL));
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < currentAlbum.getPhotoList().size() - 1){
                    position++;
                }else {
                    position = 0;
                }
                imageView.setImageURI(Uri.fromFile(new File(currentAlbum.getPhotoList().get(position).getName())));
                Bitmap image = BitmapFactory.decodeByteArray(currentAlbum.getPhotoList().get(position).imageByteArray, 0, currentAlbum.getPhotoList().get(position).imageByteArray.length);
                imageView.setImageBitmap(image);
                caption.setText(currentAlbum.getPhotoList().get(position).getCaption());
                editText.setText(currentAlbum.getPhotoList().get(position).getCaption());
                ta = new TagAdapter(SlideShowActivity.this, currentAlbum, position);
                tagList.setAdapter(ta);
                tagList.addItemDecoration(new DividerItemDecoration(SlideShowActivity.this, LinearLayoutManager.VERTICAL));
            }
        });

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
            currentAlbum.getPhotoList().get(position).getSortedTags().remove(viewHolder.getAdapterPosition());
            MainActivity.dataSource.saveToDisk(SlideShowActivity.this);
            ta.notifyDataSetChanged();
            // TODO:
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.action_search){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createListData(){

        //For adding data into recycler

    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(SlideShowActivity.this, "Back was pressed", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        Intent intent = new Intent(SlideShowActivity.this, MainActivity.class);
        startActivity(intent);
        if(currentAlbum.getName().equals("Searched Album")){
            MainActivity.dataSource.deleteAlbum(currentAlbum);
            MainActivity.dataSource.saveToDisk(context);
        }

//        Intent intent = new Intent(SlideShowActivity.this, PhotoViewActivity.class);
//        startActivity(intent);

    }

}