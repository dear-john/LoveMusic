package model;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import app.AppConstants;
import bean.Music;
import bean.Song_list;
import utils.OkHttpUtil;

/**
 * Created by 李君 on 2018/3/23.
 */

public class Song_listMusicDataImpl implements MusicData<Song_list> {

    private int type;
    private List<Song_list> lists = null;

    public Song_listMusicDataImpl(int type) {
        this.type = type;
    }

    @Override
    public void getMusicData(final OnDataLoadFinished<Song_list> loadFinished) {

        OkHttpUtil.loadData(AppConstants.MUSIC_LIST_API + type, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                if (data != null) {
                    Music music = JSONObject.parseObject(data, Music.class);
                    lists = music.getSong_list();
                }
                loadFinished.onLoadFinished(lists);
            }
        });
    }
}
