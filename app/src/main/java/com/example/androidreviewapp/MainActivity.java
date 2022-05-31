package com.example.androidreviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
//import androidx.webkit.WebSettingsCompat;
//import androidx.webkit.WebViewFeature;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;


//import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView themeTV;
    //WebViewFeature webViewFeature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this,R.xml.root_preferences,false);


        //
        //if(WebViewFeature.isFeatureSupported(webViewFeature.FORCE_DARK)) {
        //            WebSettingsCompat.getForceDark()
        //        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //getSupportFragmentManager().popBackStack();
    }
}