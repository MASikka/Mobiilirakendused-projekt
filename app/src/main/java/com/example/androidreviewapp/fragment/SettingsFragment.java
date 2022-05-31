package com.example.androidreviewapp.fragment;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.preference.PreferenceFragmentCompat;

import com.example.androidreviewapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}