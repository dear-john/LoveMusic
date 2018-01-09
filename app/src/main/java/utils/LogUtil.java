package utils;


import android.util.Log;

public class LogUtil {
    private static final String TAG = "app_name";

    public static void logD(String text) {
        Log.d(TAG, "LogD: " + text);
    }
}
