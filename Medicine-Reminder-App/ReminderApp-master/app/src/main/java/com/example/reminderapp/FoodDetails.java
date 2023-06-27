package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FoodDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        getSupportActionBar().setTitle("Food Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}