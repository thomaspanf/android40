package com.example.android40;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android40.model.Album;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    public static final String ADD_ALBUM = "com.example.android40.ADD_ALBUM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText editText = findViewById(R.id.add_edit_text);
        Button addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String newAlbumTitle = editText.getText().toString();
                if (newAlbumTitle.isEmpty()) {
                    return;
                }
//                Album newAlbum = new Album(newAlbumTitle);

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.putExtra(ADD_ALBUM, newAlbumTitle);

                if(editText.getText().toString().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Album name cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                for(Album a : MainActivity.dataSource.getAlbumList()){
                    if(a.getName().equals((editText.getText().toString()))) {
                        Toast.makeText(AddActivity.this, "Album name already exists", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Log.d(TAG, "Clicked add button");
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(AddActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
        //super.onBackPressed();
    }

}