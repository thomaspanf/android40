package com.example.android40;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android40.model.Album;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements Serializable {

    public static final String ALBUM_POSITION = "com.example.android40.ALBUM_POSITION";
    private static final long serialVersionUID = 498476467424L;
    private static final String TAG = "EditActivity";
    public static final String EDIT_ALBUM = "com.example.android40.EDIT_ALBUM";
    public EditText editText;
    public Button editButton;
    public Button deleteButton;
    public TextView currentAlbumText;
    private Album currentAlbum;
    private ArrayList<Album> currentAlbumList;
    private String position;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();

        currentAlbumText = findViewById(R.id.current_album_name);

        editText = findViewById(R.id.edit_album);

        editButton = findViewById(R.id.edit_button);

        if(intent.hasExtra(EDIT_ALBUM)){
            currentAlbum = intent.getParcelableExtra(EditActivity.EDIT_ALBUM);
            if(currentAlbum != null){
                currentAlbumText.setText(currentAlbum.getName());
            }

        }

        if(intent.hasExtra(ALBUM_POSITION)){
            position = intent.getStringExtra(ALBUM_POSITION);
        }

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //check for dupes
                currentAlbumList = MainActivity.dataSource.getAlbumList();

                if(editText.getText().toString().isEmpty()) {
                    Toast.makeText(EditActivity.this, "Album name cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                for(Album a: currentAlbumList){
                    if(editText.getText().toString().equals(a.getName())){
                        Toast.makeText(EditActivity.this, "Album with this name already exist", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                currentAlbum.setName(editText.getText().toString());
                currentAlbumText.setText(currentAlbum.getName());
                MainActivity.dataSource.saveToDisk(context);
                Intent goBack = new Intent(EditActivity.this, MainActivity.class);
                goBack.putExtra(ALBUM_POSITION, position);
                goBack.putExtra(EDIT_ALBUM, (Parcelable) currentAlbum);
                startActivity(goBack);
            }
        });

    }


}