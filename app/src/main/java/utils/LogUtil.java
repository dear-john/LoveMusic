package utils;


import android.util.Log;

import com.spring_ballet.lovemusic.R;

public class LogUtil {
    private static final String TAG = "app_name";

    public static void logD(String text) {
        Log.d(TAG, "LogD: " + text);
    }
}
