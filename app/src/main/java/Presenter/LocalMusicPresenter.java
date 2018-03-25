package Presenter;

import android.content.Context;

import java.util.List;

import bean.LocalMusic;
import model.LocalMusicDataImpl;
import model.MusicData;
import model.OnDataLoadFinished;

/**
 * Created by 李君 on 2018/3/25.
 */

public class LocalMusicPresenter implements MusicContract.Presenter {

    private MusicContract.View<LocalMusic> mView;
    private Context mContext;

    public LocalMusicPresenter(MusicContract.View<LocalMusic> view, Context context) {
        mView = view;
        mContext = context;
    }

    @Override
    public void loadData() {
        MusicData<LocalMusic> musicData = new LocalMusicDataImpl(mContext);
        musicData.getMusicData(new OnDataLoadFinished<LocalMusic>() {
            @Override
            public void onLoadFinished(List<LocalMusic> list) {
                mView.refreshView(list);
            }
        });
    }

    @Override
    public void destory() {
        mView = null;
        mContext = null;
    }
}
