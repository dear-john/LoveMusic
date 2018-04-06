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
import Presenter.RanklistPresenter;
import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import base.BaseFragment;
import bean.Song_list;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/4/5.
 */

public class RanklistFragment extends BaseFragment implements MusicContract.View<Song_list> {

    private int type = 1;
    private View loadingView;
    private ImageView imageView;
    private TextView ranklistName;
    private AnimationDrawable drawable;
    private RecyclerView recyclerView;
    private MusicContract.Presenter mPresenter;

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
        hasLoaded = true;
        initWidgets();
        initRankListName();
        mPresenter = new RanklistPresenter(type, this);
        mPresenter.loadData();
    }

    public static RanklistFragment newInstance(int type) {
        RanklistFragment fragment = new RanklistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
    }

    protected void initWidgets() {
        view.findViewById(R.id.layout_ranklist_back).setOnClickListener(this);
        ranklistName = view.findViewById(R.id.tv_ranklist_name);
        loadingView = view.findViewById(R.id.layout_loading);
        imageView = loadingView.findViewById(R.id.iv_loading);
        drawable = (AnimationDrawable) imageView.getBackground();
        if (drawable != null && !drawable.isRunning())
            drawable.start();
        recyclerView = view.findViewById(R.id.ranklist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initRankListName() {
        switch (type) {
            case 1:
                ranklistName.setText(R.string.new_music);
                break;
            case 2:
                ranklistName.setText(R.string.hot_music);
                type = 2;
                break;
            case 16:
                ranklistName.setText(R.string.billboard_music);
                type = 16;
                break;
            case 21:
                ranklistName.setText(R.string.europe_music);
                type = 21;
                break;
            case 22:
                ranklistName.setText(R.string.classical_music);
                type = 22;
                break;
            case 24:
                ranklistName.setText(R.string.movie_music);
                type = 24;
                break;
            case 25:
                ranklistName.setText(R.string.net_music);
                type = 25;
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_ranklist;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_ranklist_back:
                ((BaseActivity) mContext).showOldView();
                break;
        }
    }

    @Override
    public void refreshView(List<Song_list> list) {
        if (list != null && list.size() > 0) {
            recyclerView.setAdapter(new MusicRecyclerViewAdapter((BaseActivity) mContext,
                    true, list, null));
            if (drawable != null && drawable.isRunning()) {
                recyclerView.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.GONE);
                drawable.stop();
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
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
