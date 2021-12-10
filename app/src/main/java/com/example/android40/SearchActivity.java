package com.example.android40;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchedPhotoList = new ArrayList<>();
        allPhotoList = new ArrayList<>();
        for (Album a : MainActivity.dataSource.getAlbumList()) {
            for(Photo p : a.getPhotoList()){
                allPhotoList.add(p);
            }
        }

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
        sa = new SearchAdapter(this, searchedPhotoList);
        searchedImage.setAdapter(sa);
        searchedImage.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        sa.notifyDataSetChanged();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchedPhotoList != null || !searchedPhotoList.isEmpty()) {
                    searchedPhotoList = new ArrayList<>();
                }

                String[] arrOfStr;
                String type;
                String desc;


                String[] tag1 = descriptionField.getText().toString().isEmpty() ?
                        new String[] { "", "" } : new String[] { spinner.getSelectedItem().toString(), descriptionField.getText().toString()};
                String[] tag2 = descriptionField2.getText().toString().isEmpty() ?
                        new String[] { "", "" } : new String[] { spinner2.getSelectedItem().toString(), descriptionField2.getText().toString()};

                String[] typeArr = new String[] { tag1[0], tag2[0] };
                String[] descArr = new String[] { tag1[1], tag2[1] };

                int matchNeeded = 1;
                if (conjuncSpinner.getSelectedItem().toString().equals("AND")) {
                    matchNeeded = 2;
                }

                for(int i = 0; i < allPhotoList.size(); i ++){
                    int numMatch = 0;
                    for (int j = 0; j < typeArr.length; j++){
                        if (typeArr[i].isEmpty()) {
                            numMatch++;
                            continue;
                        }
                        for(int k = 0; k < allPhotoList.get(i).getSortedTags().size(); k++){
                            int res = isSubstring(descArr[j], allPhotoList.get(i).getSortedTags().get(k).getDesc());
                            if(res == -1){
                                continue;
                            }
                            boolean containsType = allPhotoList.get(i).getSortedTags().get(k).getType().equals(typeArr[i]);
                            if(res != -1 && containsType){
                                numMatch++;
                            }
                        }
                    }
                    if (numMatch >= matchNeeded) {
                        searchedPhotoList.add(allPhotoList.get(i));

                        //addPhotoToList(p);
                    }
                }
//                for (Photo p : allPhotoList) {
//                    int numMatch = 0;
//                    for (int i = 0; i < typeArr.length; i++) {
//                        // If its blank consider it to be a match always
//                        if (typeArr[i].isEmpty()) {
//                            numMatch++;
//                            continue;
//                        }
////                        int res = isSubstring(descArr[i], p.getTagHashMap().)
//                        // If not blank, check the photos
//                        boolean containsDescription = p.getTagHashMap().containsKey(descArr[i]);
//                        if (!containsDescription) {
//                            continue;
//                        }
//                        boolean containsType = p.getTagHashMap().get(descArr[i]).getDesc().equals(typeArr[i]);
//                        if (containsDescription && containsType) {
//                            numMatch++;
//                        }
//                    }
//                    if (numMatch >= matchNeeded) {
//                        searchedPhotoList.add(p);
//
//                        //addPhotoToList(p);
//                    }
//                }


                if(searchedPhotoList.size() == 0){
                    Toast.makeText(SearchActivity.this, "No matches found", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SearchActivity.this, "Found matches", Toast.LENGTH_LONG).show();
                }
                searchedImage = findViewById(R.id.recyclerViewSearch);
                searchedImage.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                sa = new SearchAdapter(SearchActivity.this, searchedPhotoList);
                searchedImage.setAdapter(sa);
                searchedImage.addItemDecoration(new DividerItemDecoration(SearchActivity.this, LinearLayoutManager.VERTICAL));
                sa.notifyDataSetChanged();

            }
        });


    }

    static int isSubstring(
            String s1, String s2)
    {
        int M = s1.length();
        int N = s2.length();

        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N - M; i++) {
            int j;

            /* For current index i, check for
            pattern match */
            for (j = 0; j < M; j++)
                if (s2.charAt(i + j)
                        != s1.charAt(j))
                    break;

            if (j == M)
                return i;
        }

        return -1;
    }
}