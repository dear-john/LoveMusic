package model;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import app.CommonApis;
import bean.SingerMusicList;
import utils.OkHttpUtil;

/**
 * Created by 李君 on 2018/3/23.
 */

public class SingerMusicListMusicDataImpl implements MusicData<SingerMusicList> {

    private String uid;
    private SingerMusicList mSingerMusicList = null;

    public SingerMusicListMusicDataImpl(String uid) {
        this.uid = uid;
    }

    @Override
    public void getMusicData(final OnDataLoadFinished<SingerMusicList> loadFinished) {
        OkHttpUtil.loadData(CommonApis.SINGER_MUSIC_LIST + uid, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                if (data != null) mSingerMusicList = JSON.parseObject(data, SingerMusicList.class);
                List<SingerMusicList> lists = new ArrayList<>(1);
                lists.add(mSingerMusicList);
                loadFinished.onLoadFinished(lists);
            }
        });
    }
}
