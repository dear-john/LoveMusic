package utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by 李君 on 2018/3/6.
 */

public class ShareUtil {
    public static void share(Context context, String data) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, data);
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfoList.size() > 0) {
            context.startActivity(Intent.createChooser(intent, "分享到:"));
        } else ToastUtil.showShort(context, "没有可分享的应用");
    }
}
