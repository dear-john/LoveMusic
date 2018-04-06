package fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.spring_ballet.lovemusic.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import Presenter.LocalMusicPresenter;
import Presenter.MusicContract;
import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import base.BaseFragment;
import bean.LocalMusic;
import bean.MessageEvent;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/4/5.
 */

public class LocalMusicDetailFragment extends BaseFragment implements MusicContract.View<LocalMusic> {

    private MusicContract.Presenter mPresenter;
    private List<LocalMusic> mLocalMusicList;

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
        hasLoaded = true;
        init();
        mPresenter = new LocalMusicPresenter(this, mContext);
        mPresenter.loadData();
    }

    private void init() {
        View backLayout = view.findViewById(R.id.local_music_back);
        backLayout.setOnClickListener(this);
        View searchLayout = view.findViewById(R.id.local_music_search);
        searchLayout.setOnClickListener(this);
        View menuLayout = view.findViewById(R.id.local_music_menu);
        menuLayout.setOnClickListener(this);
    }

    @Override
    public void refreshView(List<LocalMusic> list) {
        if (list != null) {
            mLocalMusicList = list;
            MessageEvent event = new MessageEvent();
            event.setLocalMusicNumber(mLocalMusicList.size());
            EventBus.getDefault().post(event);
            SharedPreferencesUtil.putIntData(mContext, "LocalMusicNumber", mLocalMusicList.size());
            RecyclerView recyclerView = view.findViewById(R.id.rv_local_music);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(new MusicRecyclerViewAdapter((BaseActivity) getActivity(),
                    false, null, list));
        } else ToastUtil.showShort(mContext, getResources().getString(R.string.loading_failed));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_local_music_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.local_music_back:
                ((BaseActivity) mContext).showOldView();
                break;
            case R.id.local_music_search:
                ToastUtil.showShort(mContext, "search");
                break;
            case R.id.local_music_menu:
                ToastUtil.showShort(mContext, "more");
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        mLocalMusicList = null;
        super.onDestroy();
    }

}
