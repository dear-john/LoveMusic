package utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 李君 on 2018/2/6.
 */

public class IntentUtil {
    public static void gotoActivity(Context from, Class target) {
        Intent intent = new Intent(from, target);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        from.startActivity(intent);
    }

    public static void gotoActivityWithData(Activity from, Class target, String data) {
        Intent intent = new Intent(from, target);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.putExtra("data", data);
        from.startActivity(intent);
    }
}
