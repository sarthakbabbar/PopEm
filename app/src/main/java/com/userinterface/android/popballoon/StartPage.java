package com.userinterface.android.popballoon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class StartPage extends AppCompatActivity {

    private TextView appName;
    private AdView adStartPage;
    private TextView highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);


        highScore = findViewById(R.id.txtHighScore);

        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        String strHighScore = settings.getString("highScore", "");
        if (strHighScore.equals(""))
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("highScore", "0");
            editor.apply();
            GlobalElements.highScore = 0;
        }
        else {
            GlobalElements.highScore = Integer.parseInt(strHighScore);
        }

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
            highScore.setText("High Score : " + String.valueOf(GlobalElements.highScore));
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

        //Button Tap Animation

        ImageButton button = (ImageButton)findViewById(R.id.imageBtnPlay);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);


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


    public void onSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        String message = "Game Resumed";
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);
        //will send intent to start the game from the last activity left
    }



        public void onResume(){
        super.onResume();
        if (adStartPage != null) {
            adStartPage.resume();
        }






        Log.d("onResume","The app was resumed");
        if (GlobalElements.boolEndGame == true) {

            // Updating the score after endgame
            highScore = findViewById(R.id.txtHighScore);
            highScore.setText("High Score : " + String.valueOf(GlobalElements.highScore));

            LayoutInflater inflater = getLayoutInflater();
            // Inflate the Layout
            View layout = inflater.inflate(R.layout.custom_layout,
                    (ViewGroup) findViewById(R.id.custom_toast_layout));

            TextView text =  layout.findViewById(R.id.textToShow);
            text.setText("Game Over");
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, -400);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

        }


    }
}
