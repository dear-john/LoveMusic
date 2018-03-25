package com.spring_ballet.lovemusic;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import bean.Song_list;
import model.MusicData;
import model.OnDataLoadFinished;
import model.Song_listMusicDataImpl;
import utils.ToastUtil;


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
        loadData();
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

    private void loadData() {
        MusicData<Song_list> data = new Song_listMusicDataImpl(type);
        data.getMusicData(new OnDataLoadFinished<Song_list>() {
            @Override
            public void onLoadFinished(List<Song_list> list) {
                if (list != null && list.size() > 0) {
                    recyclerView.setAdapter(new MusicRecyclerViewAdapter(RanklistActivity.this,
                            true, list, null));
                    if (drawable != null && drawable.isRunning()) {
                        drawable.stop();
                        loadingView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (drawable != null && drawable.isRunning())
                        drawable.stop();
                    ToastUtil.showShort(RanklistActivity.this, "榜单信息加载失败，请稍后重试");
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
