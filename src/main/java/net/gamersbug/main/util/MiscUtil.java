package net.gamersbug.main.util;

import java.util.Random;

public class MiscUtil {
    
    public static int randInt(int min, int max) {
        
        Random rand = new Random();
        
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
        
    }

    public static String secondsToTime(Integer seconds) {

        int hr  = seconds / 3600;
        int rem = seconds % 3600;
        int min = rem / 60;
        int sec = rem % 60;
        String hrStr  = (hr < 1 ? "" : (hr < 10 ? "0" : "") + hr + ":");
        String minStr = (min < 10 ? "0" : "") + min + ":";
        String secStr = (sec < 10 ? "0" : "") + sec;

        return hrStr + minStr + secStr;

    }

    public static Float getTimeRemainingPercentage(Integer secondsLeft, Integer secondsTotal) {

        return (float) secondsLeft * 100 / secondsTotal;

    }
    
}
