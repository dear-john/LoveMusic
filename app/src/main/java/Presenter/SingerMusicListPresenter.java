package Presenter;

import java.util.List;

import bean.SingerMusicList;
import model.MusicData;
import model.OnDataLoadFinished;
import model.SingerMusicListMusicDataImpl;

/**
 * Created by 李君 on 2018/3/25.
 */

public class SingerMusicListPresenter implements MusicContract.Presenter {

    private String uid;
    private MusicContract.View<SingerMusicList> mView;

    public SingerMusicListPresenter(String uid, MusicContract.View<SingerMusicList> view) {
        this.uid = uid;
        mView = view;
    }

    @Override
    public void loadData() {
        MusicData<SingerMusicList> data = new SingerMusicListMusicDataImpl(uid);
        data.getMusicData(new OnDataLoadFinished<SingerMusicList>() {
            @Override
            public void onLoadFinished(List<SingerMusicList> list) {
                mView.refreshView(list);
            }
        });
    }

    @Override
    public void destory() {
        mView = null;
    }
}
