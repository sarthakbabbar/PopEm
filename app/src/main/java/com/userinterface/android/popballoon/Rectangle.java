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
        this.setX(screenWidth);
        this.setY(screenHeight);
        this.setImageResource(R.drawable.rectangle);
        this.setColorFilter(color);

    }

}
