package fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import java.util.List;

import Presenter.MusicContract;
import Presenter.SingerMusicListPresenter;
import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import base.BaseFragment;
import bean.SingerMusicList;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerMusicFragment extends BaseFragment implements MusicContract.View<SingerMusicList> {

    private View loadingView;
    private AnimationDrawable drawable;
    private ImageView imageView;
    private MusicContract.Presenter mPresenter;
    private String uid;

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
        hasLoaded = true;
        loadingView = view.findViewById(R.id.layout_loading);
        imageView = loadingView.findViewById(R.id.iv_loading);
        drawable = (AnimationDrawable) imageView.getBackground();
        if (drawable != null && !drawable.isRunning())
            drawable.start();
        mPresenter = new SingerMusicListPresenter(uid, this);
        mPresenter.loadData();
    }

    public static SingerMusicFragment newInstance(String uid) {
        SingerMusicFragment fragment = new SingerMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uid = getArguments().getString("uid");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_singer_music;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void refreshView(List<SingerMusicList> list) {
        SingerMusicList singerMusicList;
        if (list != null && list.size() > 0 && (singerMusicList = list.get(0)) != null) {
            RecyclerView recyclerView = view.findViewById(R.id.rv_frag_singer_music);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(new MusicRecyclerViewAdapter((BaseActivity) getActivity(),
                    true, singerMusicList.getSonglist(), null));
            if (drawable != null && drawable.isRunning()) {
                recyclerView.setVisibility(View.VISIBLE);
                drawable.stop();
                loadingView.setVisibility(View.GONE);
            }
        } else onLoadFailed();
    }

    private void onLoadFailed() {
        TextView textView = loadingView.findViewById(R.id.tv_loading);
        textView.setText(R.string.loading_failed);
        ToastUtil.showShort(mContext, getResources().getString(R.string.loading_failed));
        if (drawable != null && drawable.isRunning()) {
            drawable.stop();
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        super.onDestroyView();
    }
}
