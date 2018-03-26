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

import Presenter.MusicContract;
import Presenter.RanklistPresenter;
import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import bean.Song_list;
import utils.ToastUtil;


public class RanklistActivity extends BaseActivity implements MusicContract.View<Song_list> {

    private int type;
    private View loadingView;
    private ImageView imageView;
    private TextView ranklistName;
    private AnimationDrawable drawable;
    private RecyclerView recyclerView;
    private RanklistPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
        initRankListName();
        initListeners();
        mPresenter = new RanklistPresenter(type, this);
        mPresenter.loadData();
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
        loadingView = findViewById(R.id.layout_loading);
         imageView = loadingView.findViewById(R.id.iv_loading);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.layout_ranklist_back:
                finish();
                break;
        }
    }

    @Override
    public void refreshView(List<Song_list> list) {
        if (list != null && list.size() > 0) {
            recyclerView.setAdapter(new MusicRecyclerViewAdapter(RanklistActivity.this,
                    true, list, null));
            if (drawable != null && drawable.isRunning()) {
                loadingView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                drawable.stop();
            }
        } else onLoadFailed();
    }

    private void onLoadFailed() {
        TextView textView = loadingView.findViewById(R.id.tv_loading);
        textView.setText(R.string.loading_failed);
        ToastUtil.showShort(this, getResources().getString(R.string.loading_failed));
        if (drawable != null && drawable.isRunning()) {
            drawable.stop();
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        super.onDestroy();
    }

}
