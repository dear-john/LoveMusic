package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import adapter.LocalRecyclerViewAdapter;
import bean.LocalMusic;
import bean.MessageEvent;
import utils.LocalMusicUtil;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;

public class LocalMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private List<LocalMusic> mLocalMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        Toolbar toolbar = findViewById(R.id.local_music_toolbar);
        setSupportActionBar(toolbar);
        View backLayout = findViewById(R.id.local_music_back);
        backLayout.setOnClickListener(this);
        View searchLayout = findViewById(R.id.local_music_search);
        searchLayout.setOnClickListener(this);
        View menuLayout = findViewById(R.id.local_music_menu);
        menuLayout.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.rv_local_music);
        mLocalMusicList = LocalMusicUtil.getLocalMusicData(this);
        SharedPreferencesUtil.putIntData(this, "LocalMusicNumber", mLocalMusicList.size());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new LocalRecyclerViewAdapter(mLocalMusicList, new LocalRecyclerViewAdapter.ClickListener() {
            @Override
            public void moreListener(View view, int position) {
                ToastUtil.showShort(LocalMusicActivity.this, "this is" + mLocalMusicList.get(position).getMusicName());
            }

            @Override
            public void itemListener(View view, int position) {
                ToastUtil.showShort(LocalMusicActivity.this, "item is" + mLocalMusicList.get(position).getMusicName());
            }
        }));
    }

    @Override
    public void onClick(View v) {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageEvent event = new MessageEvent();
        event.setLocalMusicNumber(mLocalMusicList.size());
        EventBus.getDefault().post(event);
    }
}