package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import Presenter.LocalMusicPresenter;
import Presenter.MusicContract;
import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import bean.LocalMusic;
import bean.MessageEvent;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;

public class LocalMusicActivity extends BaseActivity implements MusicContract.View<LocalMusic> {

    private View backLayout;
    private View searchLayout;
    private View menuLayout;
    private LocalMusicPresenter mPresenter;
    private List<LocalMusic> mLocalMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
        initListeners();
        mPresenter = new LocalMusicPresenter(this, this);
        mPresenter.loadData();
    }

    @Override
    protected void initWidgets() {
        Toolbar toolbar = findViewById(R.id.local_music_toolbar);
        setSupportActionBar(toolbar);
        backLayout = findViewById(R.id.local_music_back);
        searchLayout = findViewById(R.id.local_music_search);
        menuLayout = findViewById(R.id.local_music_menu);
    }

    @Override
    protected void initListeners() {
        backLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        menuLayout.setOnClickListener(this);
    }

    @Override
    protected int getContainerView() {
        return R.layout.activity_local_music;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MessageEvent event = new MessageEvent();
        event.setLocalMusicNumber(mLocalMusicList.size());
        EventBus.getDefault().post(event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.local_music_back:
                finish();
                break;
            case R.id.local_music_search:
                ToastUtil.showShort(this, "search");
                break;
            case R.id.local_music_menu:
                ToastUtil.showShort(this, "more");
                break;
        }
    }

    @Override
    public void refreshView(List<LocalMusic> list) {
        if (list != null) {
            mLocalMusicList = list;
            SharedPreferencesUtil.putIntData(this, "LocalMusicNumber", mLocalMusicList.size());
            RecyclerView recyclerView = findViewById(R.id.rv_local_music);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicRecyclerViewAdapter(LocalMusicActivity.this,
                    false, null, list));
        } else ToastUtil.showShort(this, getResources().getString(R.string.loading_failed));
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        mLocalMusicList = null;
        super.onDestroy();
    }

}
