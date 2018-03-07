package utils;


import android.content.Context;
import android.util.Log;

import com.spring_ballet.lovemusic.R;

public class LogUtil {
    private Context mContext;

    public LogUtil(Context context) {
        mContext = context;
    }

    public void logD(String text) {
        if (mContext != null) {
            Log.d(mContext.getResources().getString(R.string.app_name), text);
        }
    }
}
