package utils;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {
    private static OkHttpClient sClient = new OkHttpClient();
    private static String data;

    public static String loadData(String url) {
        Request request = new Request.Builder().url(url).build();
        sClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                data = response.body().string();
            }
        });
        return data;
    }
}
