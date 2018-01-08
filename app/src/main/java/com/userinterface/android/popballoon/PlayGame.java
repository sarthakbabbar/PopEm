package com.userinterface.android.popballoon;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class PlayGame extends AppCompatActivity implements Balloon.BalloonListener {

    public ViewGroup contentView;
    private AdView adPlayGame;
    public int screenWidth, screenHeight;
    private int[] tintColors = new int[]{GlobalElements.RED,GlobalElements.GREEN,GlobalElements.BLUE,GlobalElements.BROWN,GlobalElements.OLIVE};
    private int balloonsPoppedThisLevel;
    TextView levelDisplay;
    BalloonLauncher launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Log.d("onCreate", "the page is created");
        
        //variable contentView has the same dimension as the activity play game
        contentView = findViewById(R.id.activity_play_game);
        levelDisplay = findViewById(R.id.textLevelNumber);

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        LayoutInflater inflater = getLayoutInflater();
        // Customizing the toast and showing it
        try {
            //Inflate the layout
            View layout = inflater.inflate(R.layout.custom_layout,
                    (ViewGroup) findViewById(R.id.custom_toast_layout));

            TextView text =  layout.findViewById(R.id.textToShow);
            // Set the Text to show in TextView

            if (GlobalElements.levelNumber == 1){
                text.setText("HINT: Rectangle colors tell the balloons to be popped");
            }else {text.setText(message);}

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, -400);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
        catch (Exception e){
            Log.d("onCreate", "could not show the toast");
            Log.e("",e.getMessage());
        }

        //adding banner ad to the activity
        try {
            MobileAds.initialize(this, "ca-app-pub-9895741583663083~4462085894");
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
        Log.d("onCreate", "On create for the play game completed");
    }


    public void endGame()
    {

        GlobalElements.boolEndGame = true;
       try {
           while (!launcher.isCancelled()) {
               launcher.cancel(true);
           }

           while (launcher.getStatus().toString() == "RUNNING") {
               launcher.cancel(true);


           }
       }
       catch (Exception e ){
           Log.d("onEndgame", "could not end the game ");
           Log.e("",e.getMessage());
       }

       if (launcher.getStatus().toString() == "FINISHED")
       {
         /*Intent intent = new Intent(this, StartPage.class);
           //intent.putExtra("EXTRA_MESSAGE", "Game Over");
           intent.putExtra("EXTRA_MESSAGE", launcher.getStatus().toString());
           startActivity(intent);*/

       }

       finish();
       Log.d("startGame", "Ending the game");


    }

    public void nextLevel(){
        GlobalElements.boolEndGame = false;
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

        try {
            //starting Game here
            startGame();}
        catch (Exception e){
            Log.d("nextLevel", "could not start the game");
            Log.e("",e.getMessage());
        }

        Log.d("nextLevel", "next level is completed");
    }
    public void startGame() {
        GlobalElements.boolEndGame = false;
        //GlobalElements.levelNumber++;
        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        String gameLevel = settings.getString("gameLevel", "");
        if (gameLevel != "") { //This is to set a value the first time that the app is launched.
            GlobalElements.levelNumber = Integer.parseInt(gameLevel);
        }
        else
        {
            GlobalElements.levelNumber = 1;
            String levelString = Integer.toString(GlobalElements.levelNumber);
            // Writing data to SharedPreferences
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("gameLevel", levelString);
            editor.commit();
            gameLevel = "1";
        }
        levelDisplay.setText("Level " + gameLevel);
        launcher = new BalloonLauncher();
        balloonsPoppedThisLevel = 0;

        try {
            placeRectangle();
            launcher.execute(GlobalElements.levelNumber);
        }
        catch (Exception e) {
            Log.d("startGame", "launching ballons and placing rectangles");
            Log.e("",e.getMessage());
        }
    }

    public class BalloonLauncher extends AsyncTask<Integer, Integer, Void> {

        //popping balloon on touch

        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }

            int balloonsLaunched = 0;
            while (balloonsLaunched < GlobalElements.MAX_BALLOONS) {

//              Get a random horizontal position for the next balloon
                int xPosition = (int) (Math.random()*(0.8*screenWidth)); // 0.8 is to avoid the balloon getting cut on the right side.
                publishProgress(xPosition);
                balloonsLaunched++;

//              Wait a random number of milliseconds before looping
                int delay = LevelLogic.getBalloonDelay();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("doInBackGround","Delay is not working");
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
        int duration = Math.max(GlobalElements.MIN_DURATION, GlobalElements.MAX_DURATION - (LevelLogic.getBalloonSpeed() * 1000));
        balloon.releaseBalloon(screenHeight, duration);

        Log.d("launch balloon", "launching the balloon");
    }

    @Override
    public void popBalloon(Balloon balloon, boolean userTouch, int currentColor) {
        if ((userTouch && !LevelLogic.checkPopColor(currentColor) || (!userTouch && LevelLogic.checkPopColor(currentColor)))){
            endGame();
        }
        contentView.removeView(balloon);
        balloonsPoppedThisLevel++;
        if (balloonsPoppedThisLevel == GlobalElements.MAX_BALLOONS && GlobalElements.boolEndGame!= true)
        {
            LayoutInflater inflater = getLayoutInflater();
            // Inflate the Layout
            View layout = inflater.inflate(R.layout.custom_layout,
                    (ViewGroup) findViewById(R.id.custom_toast_layout));

            TextView text =  layout.findViewById(R.id.textToShow);
            // Set the Text to show in TextView
            text.setText("Leveling Up");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, -400);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            nextLevel();
        }

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
