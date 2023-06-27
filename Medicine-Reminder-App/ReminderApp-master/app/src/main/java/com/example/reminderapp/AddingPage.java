package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AddingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_page);
        getSupportActionBar().setTitle("Add Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}