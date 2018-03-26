package utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import app.Apis;
import base.BaseActivity;
import bean.MusicPalyDetail;

/**
 * Created by 李君 on 2018/3/15.
 */

public class PlayOnlineMusicUtil {
    public static void playMusic(final BaseActivity activity, String songid, final String icon, final String songName, final String singerName) {
        OkHttpUtil.loadData(Apis.PLAY_MUSIC_LINK + songid,
                new OkHttpUtil.OnLoadDataFinish() {
                    @Override
                    public void loadDataFinish(String data) {
                        boolean hasData = true;
                        if (!TextUtils.isEmpty(data)) {
                            MusicPalyDetail detail = JSONObject.parseObject(data, MusicPalyDetail.class);
                            data = detail.getBitrate().getShow_link();
                            if (TextUtils.isEmpty(data))
                                data = detail.getBitrate().getFile_link();
                            if (!TextUtils.isEmpty(data))
                                activity.refreshAfterPlay(icon, data, songName, singerName);
                            else hasData = false;
                        }
                        if (!hasData)
                            ToastUtil.showShort(activity, "加载失败，请稍后重试");
                    }
                });
    }
}
