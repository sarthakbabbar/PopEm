package com.userinterface.android.popballoon;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sarthakbabbar on 02/01/18.
 */

public class Rectangle extends android.support.v7.widget.AppCompatImageView {
    public Rectangle(Context context, int color, int screenHeight, int screenWidth) {
        super(context);
        //this.setImageResource(R.drawable.rectangle);
        //this.setColorFilter(color);
        //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, screenHeight);
        //this.setX(screenWidth);
        //this.setY(screenHeight);
        this.setX(600);
        this.setY(1400);
        this.setImageResource(R.drawable.rectangle);
        this.setColorFilter(GlobalElements.TINT_COLORS[0]);

    }
   // public void placeRectangle(int height, int width,int color, ViewGroup contentView, Context context){
      public void placeRectangle (ViewGroup contentView){
        //Rectangle rectangle = new Rectangle(context, GlobalElements.TINT_COLORS[color],400,200);
        //rectangle.setX(width);
        //rectangle.setY(height);
        //contentView.addView(this);
    }
}
