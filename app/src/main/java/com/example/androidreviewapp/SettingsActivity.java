package com.example.androidreviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidreviewapp.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
public static final String ALPHABET_PREF_MODE_SWITCH = "alphabet_switch";
public static final String LANGUAGE_PREF_CHOICE = "language";
public static final String LENGTH_PREF_MODE_SWITCH="length_switch";
public static final String STARTING_PREF_MODE_SWITCH="starting_switch";
public static final String FILTER_PREF_CHOICE = "review_sorting";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}