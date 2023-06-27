package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FinalPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
        getSupportActionBar().setTitle("Your Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}