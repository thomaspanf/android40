package com.example.android40;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android40.model.Album;
import com.example.android40.model.Photo;
import com.example.android40.model.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinner;
    private RecyclerView searchedImage;
    private Button searchButton;
    private EditText descriptionField;
    private ArrayList<Photo> searchedPhotoList;
    private ArrayList<Photo> allPhotoList;
    private Spinner spinner2;
    private EditText descriptionField2;
    private Spinner conjuncSpinner;
    private SearchAdapter sa;
    private Button clearButton;
    public Album searchedAlbum;
    public final static String SEARCHED_ALBUM = "com.example.android40.SEARCHED_ALBUM";
    public final static String SEARCHED_ALBUM_POSITION = "com.example.android40.SEARCHED_ALBUM_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchedAlbum = new Album("Searched Album");

        searchedPhotoList = new ArrayList<>();
        allPhotoList = new ArrayList<>();
        for (Album a : MainActivity.dataSource.getAlbumList()) {
            for(Photo p : a.getPhotoList()){
                allPhotoList.add(p);
            }
        }

        clearButton = findViewById(R.id.clear_button);

        searchButton = findViewById(R.id.search_button);

        spinner = findViewById(R.id.tag_type_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        descriptionField = findViewById(R.id.editTextTextPersonName);

        spinner2 = findViewById(R.id.tag_type_spinner2);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        descriptionField2 = findViewById(R.id.editTextTextPersonName2);

        conjuncSpinner = findViewById(R.id.conjunc_spinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.conjunc_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conjuncSpinner.setAdapter(adapter3);

        searchedImage = findViewById(R.id.recyclerViewSearch);
        searchedImage.setLayoutManager(new LinearLayoutManager(this));
        sa = new SearchAdapter(this, searchedPhotoList, "Searched Album");
        searchedImage.setAdapter(sa);
        searchedImage.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        sa.notifyDataSetChanged();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sa.clear();

                searchedAlbum.setPhotoList(new ArrayList<Photo>());

                if (searchedPhotoList != null || !searchedPhotoList.isEmpty()) {
                    searchedPhotoList = new ArrayList<>();
                }

                String[] arrOfStr;
                String type;
                String desc;
                String[] tag1;
                String[] tag2;
//                String[] tag1 = descriptionField.getText().toString().isEmpty() ?
//                        new String[] { "", "" } : new String[] { spinner.getSelectedItem().toString(), descriptionField.getText().toString()};
//                String[] tag2 = descriptionField2.getText().toString().isEmpty() ?
//                        new String[] { "", "" } : new String[] { spinner2.getSelectedItem().toString(), descriptionField2.getText().toString()};

                if(descriptionField.getText().toString().isEmpty()){
                    tag1 = new String[] { "", "" };
                } else {
                    tag1 = new String[] {spinner.getSelectedItem().toString(), descriptionField.getText().toString().toLowerCase()};
                }

                if(descriptionField2.getText().toString().isEmpty()){
                    tag2 = new String[] { "", "" };
                } else {
                    tag2 = new String[] {spinner2.getSelectedItem().toString(), descriptionField2.getText().toString().toLowerCase()};
                }

                String[] typeArr = new String[] { tag1[0], tag2[0] };
                String[] descArr = new String[] { tag1[1], tag2[1] };

                int matchNeeded = 1;
                if (conjuncSpinner.getSelectedItem().toString().equals("AND")) {
                    matchNeeded = 2;
                }
                //Toast.makeText(SearchActivity.this, ""+matchNeeded, Toast.LENGTH_SHORT).show();

                for(int i = 0; i < allPhotoList.size(); i ++){
                    int numMatch = 0;
                    for (int j = 0; j < typeArr.length; j++){
                        if (typeArr[j].isEmpty()) {
                            numMatch++;
                            continue;
                        }
                        for(int k = 0; k < allPhotoList.get(i).getSortedTags().size(); k++){
                            int res = isSubstring(descArr[j], allPhotoList.get(i).getSortedTags().get(k).getDesc());
                            if(res == -1){
                                continue;
                            }
                            boolean containsType = allPhotoList.get(i).getSortedTags().get(k).getType().equals(typeArr[j]);
                            if(res != -1 && containsType){
                                numMatch++;
                            }
                        }
                    }
                    if (numMatch >= matchNeeded) {
                        searchedPhotoList.add(allPhotoList.get(i));
                        searchedAlbum.addPhoto(allPhotoList.get(i));
                    }
                }

                MainActivity.dataSource.addAlbum(searchedAlbum);

                if(searchedPhotoList.size() == 0){
                    Toast.makeText(SearchActivity.this, "No matches found", Toast.LENGTH_LONG).show();
                    return;
                }
                searchedImage = findViewById(R.id.recyclerViewSearch);
                searchedImage.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                sa = new SearchAdapter(SearchActivity.this, searchedPhotoList, "Searched Album");
                searchedImage.setAdapter(sa);
                searchedImage.addItemDecoration(new DividerItemDecoration(SearchActivity.this, LinearLayoutManager.VERTICAL));
                //MainActivity.dataSource.saveToDisk(SearchActivity.this);
                //Toast.makeText(SearchActivity.this, searchedAlbum.getName()+ " " +searchedAlbum.getPhotoList().size(), Toast.LENGTH_LONG).show();
                sa.notifyDataSetChanged();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sa.clear();
//                for(Album a: MainActivity.dataSource.getAlbumList()) {
//                    if(a.getName().equals("Searched Album")){
//                        MainActivity.dataSource.getAlbumList().remove(a);
//                    }
//                }
            }
        });


    }

    static int isSubstring(String s1, String s2)
    {
        int M = s1.length();
        int N = s2.length();

        for (int i = 0; i <= N - M; i++) {
            int j;

            for (j = 0; j < M; j++)
                if (s2.charAt(j)
                        != s1.charAt(j))
                    break;

            if (j == M)
                return i;
        }

        return -1;
    }

    @Override
    public void onBackPressed() {
        //.makeText(SearchActivity.this, "Back was pressed", Toast.LENGTH_SHORT).show();
        for(Album a: MainActivity.dataSource.getAlbumList()) {
            if(a.getName().equals("Searched Album")){
                MainActivity.dataSource.getAlbumList().remove(a);
            }
        }
        super.onBackPressed();
    }

}