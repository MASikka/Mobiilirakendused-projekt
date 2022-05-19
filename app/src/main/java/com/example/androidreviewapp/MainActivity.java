package com.example.androidreviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Comment by Roald login test
        // This is a new comment
    }

    public void conflictText(){
        int v = 1;
        v++;
    }
}