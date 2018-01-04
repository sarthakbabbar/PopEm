package com.userinterface.android.popballoon;

/**
 * Created by sarthakbabbar on 04/01/18.
 */

public class LevelLogic {

    public static int[] getRectangleColor()
    {
        int levelGroup = (int) (GlobalElements.levelNumber/10)+1;
        int[] assRectColor;

        switch (levelGroup){
            case 1:
                assRectColor = new int[] {GlobalElements.RED,GlobalElements.RED,GlobalElements.RED,GlobalElements.RED,GlobalElements.RED};
                break;

            case 2:
                assRectColor = new int[] {GlobalElements.RED,GlobalElements.GREEN,GlobalElements.RED,GlobalElements.GREEN,GlobalElements.RED};
                break;

            case 3:
                assRectColor = new int[] {GlobalElements.RED,GlobalElements.GREEN,GlobalElements.BLUE,GlobalElements.RED,GlobalElements.GREEN};
                break;

            case 4:
                assRectColor = new int[] {GlobalElements.RED,GlobalElements.GREEN,GlobalElements.BLUE,GlobalElements.BROWN,GlobalElements.RED};
                break;

            case 5:
                assRectColor = new int[] {GlobalElements.RED,GlobalElements.GREEN,GlobalElements.BLUE,GlobalElements.BROWN,GlobalElements.OLIVE};
                break;

            default:
                assRectColor = new int[] {GlobalElements.OLIVE,GlobalElements.OLIVE,GlobalElements.OLIVE,GlobalElements.OLIVE,GlobalElements.RED};

        }
     return assRectColor;
    }

}
