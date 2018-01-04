package com.userinterface.android.popballoon;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * Created by sarthakbabbar on 03/01/18.
 */

public class Balloon extends android.support.v7.widget.AppCompatImageView implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    public ValueAnimator balloonAnimator;
    private BalloonListener touchListener;
    private boolean popped;
    private int intrensicBalloonColor;
    public Balloon(Context context) {
        super(context);
    }

    // creating balloon and adding the colors to the balloon
    public Balloon(Context context, int color, int rawHeight) {
        super(context);
        touchListener = (BalloonListener) context;
        this.setImageResource(R.drawable.balloon);
        this.setColorFilter(color);
        intrensicBalloonColor = color;
        int rawWidth = rawHeight / 2;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(rawWidth, rawHeight);
    }
    // animation
    public void releaseBalloon(int screenHeight, int duration){
        balloonAnimator = new ValueAnimator();
        balloonAnimator.setDuration(duration);
        balloonAnimator.setFloatValues(screenHeight, 0f);
        balloonAnimator.setInterpolator(new LinearInterpolator());
        balloonAnimator.setTarget(this);
        balloonAnimator.addListener(this);
        balloonAnimator.addUpdateListener(this);
        balloonAnimator.start();



    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        setY((Float) valueAnimator.getAnimatedValue());

    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        //put if else state to end the game if the animation ends for the balloon that is supposed to be popped
        if(!popped){
            touchListener.popBalloon(this,false, intrensicBalloonColor);

        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
    // stop the balloon animation on touch
    @Override
    public boolean onTouchEvent(MotionEvent event)


    {
        if (!popped && event.getAction() == MotionEvent.ACTION_DOWN){
            touchListener.popBalloon(this,true, intrensicBalloonColor);

            popped = true;
            balloonAnimator.cancel();
            boolean returnValue = super.onTouchEvent(event);
            return returnValue;

        }
        return !super.onTouchEvent(event);
    }
    // checks if the balloon is touched
    public interface BalloonListener{
        void popBalloon(Balloon balloon, boolean userTouch, int currentColor);

    }
}
