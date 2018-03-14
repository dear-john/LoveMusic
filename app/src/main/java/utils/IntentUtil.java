package utils;

import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by 李君 on 2018/2/6.
 */

public class IntentUtil {
    public static void gotoActivity(Context from, Class target) {
        Intent intent = new Intent(from, target);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }

    public static void gotoActivityWithData(Context from, Class target, String data) {
        Intent intent = new Intent(from, target);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("data", data);
        from.startActivity(intent);
    }
}
