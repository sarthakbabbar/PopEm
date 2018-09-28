package com.userinterface.android.popballoon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;


public class SignUp extends AppCompatActivity {

    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

      // mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

    }

    private void loadRewardedVideoAd() {
       /* mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build()); // Test Ad
                */
        mRewardedVideoAd.loadAd("ca-app-pub-9895741583663083/1890430581",
                new AdRequest.Builder().build());
    }

    public void onExitOK(View view){


        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }



/*
        Intent intent = new Intent(this, StartPage.class);
        String message = "Game Resumed";
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);

*/
    }


}
