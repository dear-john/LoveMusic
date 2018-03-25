package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 李君 on 2018/2/6.
 */

public class SharedPreferencesUtil {
    private static SharedPreferences sPreferences;
    private static SharedPreferences.Editor sEditor;
    private static final String DATA_BASE_NAME = "MyDataBase";
    public static final int DEFAULT_INT_VALUE = -1;

    public static void putStringData(Context context, String key, String value) {
        sPreferences = context.getApplicationContext().getSharedPreferences(DATA_BASE_NAME, 0);
        sEditor = sPreferences.edit();
        sEditor.putString(key, value);
        sEditor.apply();
    }

    public static void putIntData(Context context, String key, int value) {
        sPreferences = context.getApplicationContext().getSharedPreferences(DATA_BASE_NAME, 0);
        sEditor = sPreferences.edit();
        sEditor.putInt(key, value);
        sEditor.apply();
    }

    public static String getStringData(Context context, String key) {
        sPreferences = context.getApplicationContext().getSharedPreferences(DATA_BASE_NAME, 0);
        return sPreferences.getString(key, "");
    }

    public static int getIntData(Context context, String key) {
        sPreferences = context.getApplicationContext().getSharedPreferences(DATA_BASE_NAME, 0);
        return sPreferences.getInt(key, DEFAULT_INT_VALUE);
    }

    public static void putBooleanData(Context context, String key, Boolean value) {
        sPreferences = context.getApplicationContext().getSharedPreferences(DATA_BASE_NAME, 0);
        sEditor = sPreferences.edit();
        sEditor.putBoolean(key, value);
        sEditor.apply();
    }

    public static Boolean getBooleanData(Context context, String key) {
        sPreferences = context.getApplicationContext().getSharedPreferences(DATA_BASE_NAME, 0);
        return sPreferences.getBoolean(key, false);
    }
}
