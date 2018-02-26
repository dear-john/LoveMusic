package utils;


import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {
    private static final int DEFAULT_TIMEOUT = 3000;
    private static final OkHttpClient sClient = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build();
    private static String data;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void loadData(String url, final OnLoadDataFinish l) {
        Request request = new Request.Builder()
                //获取客户端标识：Build.BRAND + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE
                // 不添加请求头会报403错误
                .addHeader("User-Agent", Build.BRAND + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE)
                .url(url)
                .build();
        sClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        l.loadDataFinish(null);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                data = response.body().string();
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        l.loadDataFinish(data);
                    }
                });
            }
        });
    }

    public interface OnLoadDataFinish {
        void loadDataFinish(String data);
    }
}
