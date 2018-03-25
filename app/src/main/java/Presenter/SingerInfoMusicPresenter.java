package Presenter;

import java.util.List;

import bean.SingerInfo;
import model.MusicData;
import model.OnDataLoadFinished;
import model.SingerInfoMusicDataImpl;

/**
 * Created by 李君 on 2018/3/25.
 */

public class SingerInfoMusicPresenter implements MusicContract.Presenter {

    private String uid;
    private MusicContract.View<SingerInfo> mView;

    public SingerInfoMusicPresenter(String uid, MusicContract.View<SingerInfo> view) {
        this.uid = uid;
        mView = view;
    }

    @Override
    public void loadData() {
        MusicData<SingerInfo> data = new SingerInfoMusicDataImpl(uid);
        data.getMusicData(new OnDataLoadFinished<SingerInfo>() {
            @Override
            public void onLoadFinished(List<SingerInfo> list) {
                mView.refreshView(list);
            }
        });
    }

    @Override
    public void destory() {
        mView = null;
    }
}
