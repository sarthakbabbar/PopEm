package com.userinterface.android.popballoon;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.Date;
import java.util.Random;

public class PlayGame extends AppCompatActivity implements Balloon.BalloonListener {

    public ViewGroup contentView;
    private AdView adPlayGame;
    public int screenWidth, screenHeight;
    private boolean endgame;
    private int[] tintColors = new int[]{GlobalElements.RED,GlobalElements.GREEN,GlobalElements.BLUE,GlobalElements.BROWN,GlobalElements.OLIVE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Log.d("onCreate", "the page is created");
        
        //variable contentView has the same dimension as the activity play game
        contentView = findViewById(R.id.activity_play_game);

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");

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
                        startGame();
                    }
                    catch (Exception e){
                        Log.e("",e.getMessage());
                        Log.d("onCreate", "could not get height and width of the view");
                    }
                }
            });
        }




    }

    public void onGameEnd()
    {
        Intent intent = new Intent(this, StartPage.class);
        intent.putExtra("EXTRA_MESSAGE", "The game has ended");
        startActivity(intent);
        endgame = true;
    }

    public void nextLevel(){
        //Reading level data
        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        String gameLevel = settings.getString("gameLevel", "");

        GlobalElements.levelNumber = Integer.parseInt(gameLevel);
        GlobalElements.levelNumber++;
        String levelString = Integer.toString(GlobalElements.levelNumber);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gameLevel", levelString);
        editor.commit();

        startGame();

    }
    public void startGame() {
        GlobalElements.levelNumber++;
        placeRectangle();
        BalloonLauncher launcher = new BalloonLauncher();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        launcher.execute(GlobalElements.levelNumber);


    }



    public class BalloonLauncher extends AsyncTask<Integer, Integer, Void> {

        //popping balloon on touch

        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }
            //game logic
            int level = GlobalElements.levelNumber;
            int maxDelay = Math.max(GlobalElements.MIN_DELAY,
                    (GlobalElements.MAX_DELAY - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;

            int balloonsLaunched = 0;
            while (balloonsLaunched < GlobalElements.MAX_BALLOONS) {

//              Get a random horizontal position for the next balloon
                Random random = new Random(new Date().getTime());
                int xPosition = random.nextInt(screenWidth - 200);
                publishProgress(xPosition);
                //balloonsLaunched++;

//              Wait a random number of milliseconds before looping
                int delay = random.nextInt(minDelay) + minDelay;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int xPosition = values[0];
            launchBalloon(xPosition);
        }



    }

    public void launchBalloon(int x) {
        //adding colors to the balloon
        //Random r = new Random();
        int randColor = (int) (Math.random()*5);

        Balloon balloon = new Balloon(this, tintColors[randColor], 150);


//      Set balloon vertical position and dimensions, add to container
        balloon.setX(x);
        balloon.setY(screenHeight + balloon.getHeight());
        contentView.addView(balloon);

//     Balloon animation begins
        int duration = Math.max(GlobalElements.MIN_DURATION, GlobalElements.MAX_DURATION - (GlobalElements.levelNumber * 1000));
        balloon.releaseBalloon(screenHeight, duration);

    }

    @Override
    public void popBalloon(Balloon balloon, boolean userTouch, int currentColor) {
        contentView.removeView(balloon);


    }
    public void placeRectangle(){
        //populating the rectangles
        Rectangle[] rectangle = new Rectangle[5];
        int yOfRectangles = (int)(0.85 * contentView.getHeight());
        int[] xofRectangle = new int[5];
        int[] rectColor = LevelLogic.getRectangleColor();
        for (int i = 0; i < 5; i++) {
            xofRectangle[i] = (int) (((2*i+1)*0.1*contentView.getWidth()) - 80); // This number 80 needs to be changes to a logical number
            rectangle[i] = new Rectangle(this, rectColor[i], yOfRectangles, xofRectangle[i]);
            contentView.addView(rectangle[i]);

        }
    }

}
