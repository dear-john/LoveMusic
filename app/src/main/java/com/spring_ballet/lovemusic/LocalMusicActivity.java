package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import bean.LocalMusic;
import bean.MessageEvent;
import utils.LocalMusicUtil;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;

public class LocalMusicActivity extends BaseActivity {

    private List<LocalMusic> mLocalMusicList;
    private View backLayout;
    private View searchLayout;
    private View menuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalMusicList = LocalMusicUtil.getLocalMusicData(this);
        initWidgets();
        initListeners();
        SharedPreferencesUtil.putIntData(this, "LocalMusicNumber", mLocalMusicList.size());
    }

    @Override
    protected int getContainerView() {
        return R.layout.activity_local_music;
    }

    @Override
    protected void initListeners() {
        backLayout.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        menuLayout.setOnClickListener(this);
    }

    @Override
    protected void initWidgets() {
        Toolbar toolbar = findViewById(R.id.local_music_toolbar);
        setSupportActionBar(toolbar);
        backLayout = findViewById(R.id.local_music_back);
        searchLayout = findViewById(R.id.local_music_search);
        menuLayout = findViewById(R.id.local_music_menu);
        RecyclerView recyclerView = findViewById(R.id.rv_local_music);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MusicRecyclerViewAdapter(LocalMusicActivity.this,
                false, null, mLocalMusicList));
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
    protected void onDestroy() {
        super.onDestroy();
        MessageEvent event = new MessageEvent();
        event.setLocalMusicNumber(mLocalMusicList.size());
        EventBus.getDefault().post(event);
    }
}
