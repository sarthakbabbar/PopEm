package com.userinterface.android.popballoon;

import java.util.LinkedHashSet;
import java.util.Set;

public class LevelLogic {

    public static int[] getRectangleColor()
    {
        int levelGroup =  (GlobalElements.levelNumber/10)+1;
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

    public static String lvlColorMessage()
    {
        // Creating colors for the hint message
        String lvlMsgBalloonColor = "";
        int[] lvlLogicBalloonColor = LevelLogic.getRectangleColor();
        Set<Integer> setUniqueNumbers = new LinkedHashSet<Integer>();
        for(int x : lvlLogicBalloonColor) {
            setUniqueNumbers.add(x);
        }

        for(Integer x : setUniqueNumbers) {
            if (x==GlobalElements.RED)
            {
                if (lvlMsgBalloonColor == "") {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + "Red";
                }
                else
                {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + " & Red";
                }
            }
            if (x==GlobalElements.GREEN)
            {
                if (lvlMsgBalloonColor == "") {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + "Green";
                }
                else
                {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + " & Green";
                }
            }
            if (x==GlobalElements.BLUE)
            {
                if (lvlMsgBalloonColor == "") {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + "Blue";
                }
                else
                {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + " & Blue";
                }
            }
            if (x==GlobalElements.OLIVE)
            {
                if (lvlMsgBalloonColor == "") {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + "Olive";
                }
                else
                {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + " & Olive";
                }
            }
            if (x==GlobalElements.BROWN)
            {
                if (lvlMsgBalloonColor == "") {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + "Brown";
                }
                else
                {
                    lvlMsgBalloonColor = lvlMsgBalloonColor + " & Brown";
                }
            }

        }
        return lvlMsgBalloonColor;
    }

    public static boolean checkPopColor(int balloonColor){

        boolean poppedBalloonColor = false;
        int[] colorCheck = getRectangleColor();
        for (int i: colorCheck)
        {
            if (i== balloonColor) {
                poppedBalloonColor = true;
            }
        }

        return poppedBalloonColor;

    }


    public static int getBalloonSpeed(){
        int levelGroup =  (GlobalElements.levelNumber/10)+1;
        int balloonSpeed = 0;
        switch (levelGroup){
            case 1:
                balloonSpeed = GlobalElements.levelNumber+3;
                break;

            case 2:
                balloonSpeed = GlobalElements.levelNumber-7;
                break;

            case 3:
                balloonSpeed = GlobalElements.levelNumber-17;
                break;

            case 4:
                balloonSpeed = GlobalElements.levelNumber-27;
                break;

            case 5:
                balloonSpeed = GlobalElements.levelNumber-37;
                break;

            default:
                balloonSpeed = 5;

        }
        return balloonSpeed;
    }

    public static int getBalloonDelay()
    {
        int balloonDelay = Math.max(GlobalElements.MIN_DELAY, (GlobalElements.MAX_DELAY - ((getBalloonSpeed() - 1) * 500)));
        //int balloonDelay = (int) ((Math.random()*(GlobalElements.MAX_DELAY - GlobalElements.MAX_DELAY)) + GlobalElements.MIN_DELAY);
        return balloonDelay;
    }
}
