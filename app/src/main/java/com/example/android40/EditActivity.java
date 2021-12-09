package com.example.android40;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity implements Serializable {

    private static final long serialVersionUID = 498476467424L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


    }


}