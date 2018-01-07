package utils;


import android.app.Activity;
import android.widget.Toast;

public class ToastUtil {
    public static void toast(Activity activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }
}
