package com.userinterface.android.popballoon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class StartPage extends AppCompatActivity {

    private TextView appName;
    private AdView adStartPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        //adding banner add
        try {
            MobileAds.initialize(this, "ca-app-pub-2511496555353949~6166449183");
            adStartPage = findViewById(R.id.adViewBanner);
            AdRequest adRequest = new AdRequest.Builder().build();
            adStartPage.loadAd(adRequest);
        }
        catch (Exception e){
            Log.d("onCreate", "could not show ad");
            Log.e("",e.getMessage());
        }
        // linking the variable with text View to change the app name
        appName = findViewById(R.id.appNameText);

        try {
            appName.setText(GlobalElements.APP_NAME);
        }
        catch (Exception e){
            Log.d("onCreate", "could not create the page");
            Log.e("",e.getMessage());
        }

        Log.d("On Create","the page is loaded ");


    }
    public void onPlay(View view){
        Intent intent = new Intent(this, PlayGame.class);
        String message = "Game Resumed";
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);
        //will send intent to start the game from the last activity left

    }

    public void onReset(View view){
        // Variable settings is created to get SharedPreferences.
        // Name of the preference is MyStorage can be accessed from anywhere else.
        //writing to SharedPreferences
        // Here the level resets and starts from 0
        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gameLevel", "1");
        try {
            //trying to commit the change
            editor.commit();
        }
        catch (Exception e){
            Log.e("onReset",e.getMessage());
            Log.d("onReset", "could not write to shared preferences");
        }
        //sending the intent to go to the next activity
        Intent intent = new Intent(this, PlayGame.class);
        String message = "Game Reset";
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);

        Log.d("onReset", "on Click event completed");


    }
    public void onResume() {
        super.onResume();
        if (adStartPage != null) {
            adStartPage.resume();
        }
        Log.d("onResume","The app was resumed");

    }
}
