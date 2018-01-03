package com.userinterface.android.popballoon;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.ExecutionException;

public class PlayGame extends AppCompatActivity {
    //A ViewGroup is a special view that can contain other views
    private ViewGroup contentView;
    private AdView adPlayGame;
    private int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GlobalElements.TINT_COLORS[0] = Color.argb(120,255,0,0);
        GlobalElements.TINT_COLORS[1] = Color.argb(180,0,255,0);
        GlobalElements.TINT_COLORS[2] = Color.argb(190,0,0,255);
        GlobalElements.TINT_COLORS[3] = Color.argb(210,130,125,0);
        GlobalElements.TINT_COLORS[4] = Color.argb(200,0,125,130);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Log.d("onCreate", "the page is created");
        
        //variable contentView has the same dimension as the activity play game
        contentView = findViewById(R.id.activity_play_game);

        //this is used to register listeners that can be notified of changes
        //details of  ViewGroup contentView are assigned to the variable
        ViewTreeObserver viewTreeObserver = contentView.getViewTreeObserver();

        if (viewTreeObserver.isAlive()) {
            //Register a callback to be invoked when the global layout state or the visibility of views within the view tree changes
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    contentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    try {
                        screenWidth = contentView.getWidth();
                        screenHeight = contentView.getHeight();
                        start();
                    }
                    catch (Exception e){
                        Log.e("",e.getMessage());
                        Log.d("onCreate", "could not get height and width of the view");
                    }
                }
            });
        }

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();

        //adding banner ad to the activity
        try {
            MobileAds.initialize(this, "ca-app-pub-2511496555353949~6166449183");
            adPlayGame = findViewById(R.id.adViewBanner);
            AdRequest adRequest = new AdRequest.Builder().build();
            adPlayGame.loadAd(adRequest);
        }
        catch (Exception e){
            Log.d("onCreate", "could not show ad");
            Log.e("",e.getMessage());
        }

    }
    public void start(){
        Rectangle.placeRectangle(400,200,GlobalElements.TINT_COLORS[0],contentView,this);
    }

}
