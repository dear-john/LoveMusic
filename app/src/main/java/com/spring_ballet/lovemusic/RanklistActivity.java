package com.spring_ballet.lovemusic;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.Random;

import adapter.NetRecyclerViewAdapter;
import app.CommonApis;
import base.BaseActivity;
import bean.Music;
import bean.Song_list;
import utils.BottomDialogUtil;
import utils.OkHttpUtil;
import utils.PlayOnlineMusicUtil;


public class RanklistActivity extends BaseActivity {

    private int type;
    private View loadingView;
    private TextView ranklistName;
    private AnimationDrawable drawable;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
        initListeners();
        initData();
    }

    @Override
    protected int getContainerView() {
        return R.layout.activity_ranklist;
    }

    @Override
    protected void initWidgets() {
        Toolbar toolbar = findViewById(R.id.ranklist_toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_ranklist_back).setOnClickListener(this);
        ranklistName = findViewById(R.id.tv_ranklist_name);
        initRankListName();
        loadingView = findViewById(R.id.layout_loading);
        ImageView imageView = loadingView.findViewById(R.id.iv_loading);
        drawable = (AnimationDrawable) imageView.getBackground();
        if (drawable != null && !drawable.isRunning())
            drawable.start();
        recyclerView = findViewById(R.id.ranklist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initRankListName() {
        type = 1;
        switch (Integer.parseInt(getIntent().getStringExtra("data"))) {
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
    protected void initListeners() {

    }

    private void initData() {
        OkHttpUtil.loadData(CommonApis.MUSIC_LIST_API + type, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                final Music music = JSONObject.parseObject(data, Music.class);
                recyclerView.setAdapter(new NetRecyclerViewAdapter(music.getSong_list(), new NetRecyclerViewAdapter.ClickListener() {
                    @Override
                    public void moreListener(View view, int position) {
                        final Song_list songList = music.getSong_list().get(position);
                        new BottomDialogUtil().showDialog(RanklistActivity.this, songList.getTitle(),
                                new Random().nextInt(10000) + 100,
                                songList.getArtist_name(), songList.getAlbum_title(), songList.getAll_artist_ting_uid());
                    }

                    @Override
                    public void itemListener(View view, int position) {
                        Song_list songList = music.getSong_list().get(position);
                        PlayOnlineMusicUtil.playMusic(RanklistActivity.this, songList.getSong_id(),
                                songList.getPic_small(), songList.getTitle(), songList.getAuthor());
                    }
                }));
                if (drawable != null && drawable.isRunning()) {
                    drawable.stop();
                    loadingView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_ranklist_back:
                finish();
                break;
        }
    }

}
