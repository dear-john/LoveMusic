package model;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import app.Apis;
import bean.SingerInfo;
import utils.OkHttpUtil;

/**
 * Created by 李君 on 2018/3/23.
 */

public class SingerInfoMusicDataImpl implements MusicData<SingerInfo> {

    private String uid;
    private SingerInfo mSingerInfo = null;

    public SingerInfoMusicDataImpl(String uid) {
        this.uid = uid;
    }

    @Override
    public void getMusicData(final OnDataLoadFinished<SingerInfo> loadFinished) {
        OkHttpUtil.loadData(Apis.SINGER_INFO_API + uid, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                if (data != null) mSingerInfo = JSON.parseObject(data, SingerInfo.class);
                List<SingerInfo> singerInfos = new ArrayList<>(1);
                singerInfos.add(mSingerInfo);
                loadFinished.onLoadFinished(singerInfos);
            }
        });
    }
}
