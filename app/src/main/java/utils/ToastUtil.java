package utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast sToast;

    @SuppressLint("ShowToast")
    public static void showShort(Context context, String text) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showLong(Context context, String text) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }
}
