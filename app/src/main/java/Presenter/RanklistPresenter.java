package Presenter;

import java.util.List;

import bean.Song_list;
import model.MusicData;
import model.OnDataLoadFinished;
import model.Song_listMusicDataImpl;

/**
 * Created by 李君 on 2018/3/26.
 */

public class RanklistPresenter implements MusicContract.Presenter {

    private int type;
    private MusicContract.View<Song_list> mView;

    public RanklistPresenter(int type, MusicContract.View<Song_list> view) {
        this.type = type;
        mView = view;
    }

    @Override
    public void loadData() {
        MusicData<Song_list> data = new Song_listMusicDataImpl(type);
        data.getMusicData(new OnDataLoadFinished<Song_list>() {
            @Override
            public void onLoadFinished(List<Song_list> list) {
                mView.refreshView(list);
            }
        });
    }

    @Override
    public void destory() {
        mView = null;
    }
}
