package utils;


import android.util.Log;

public class LogUtil {

    private static final String tag = "mytag";

    public static void logD(String text) {
        Log.d(tag, text);
    }
}
