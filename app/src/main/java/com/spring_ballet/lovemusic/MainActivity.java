package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import base.BaseActivity;
import base.BaseFragment;
import fragment.AroundMusicFragment;
import fragment.FriendFragment;
import fragment.LocalMusicFragment;
import utils.LogUtil;
import utils.ToastUtil;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    //应用主页
    private FrameLayout menuLayout;
    private ImageView localMusicIv;
    private ImageView netMusicIv;
    private ImageView friendIv;
    private FrameLayout searchLauout;
    private ViewPager mainVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logD("main onCreate");
        initWidgets();
        initListeners();
        initViewPager();
        setSelectedItemAndPage(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.logD("main onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.logD("main onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.logD("main onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.logD("main onDestroy");
    }

    @Override
    protected void initWidgets() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        menuLayout = findViewById(R.id.main_menu);
        localMusicIv = findViewById(R.id.iv_local_music);
        netMusicIv = findViewById(R.id.iv_around_music);
        friendIv = findViewById(R.id.iv_friend);
        searchLauout = findViewById(R.id.search);
        mainVp = findViewById(R.id.main_viewpager);
    }

    @Override
    protected void initListeners() {
        menuLayout.setOnClickListener(this);
        localMusicIv.setOnClickListener(this);
        netMusicIv.setOnClickListener(this);
        friendIv.setOnClickListener(this);
        searchLauout.setOnClickListener(this);
        mainVp.addOnPageChangeListener(this);
    }

    private void initViewPager() {
        List<BaseFragment> baseFragmentList = new ArrayList<>(3);
        baseFragmentList.add(new LocalMusicFragment());
        baseFragmentList.add(new AroundMusicFragment());
        baseFragmentList.add(new FriendFragment());
        mainVp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), baseFragmentList));
        mainVp.setOffscreenPageLimit(2);
    }

    @Override
    protected int getContainerView() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_local_music:
                if (mainVp.getCurrentItem() != 0) {
                    setSelectedItemAndPage(0);
                } else {
                    ToastUtil.showShort(this, "refresh");
                }
                break;
            case R.id.iv_around_music:
                if (mainVp.getCurrentItem() != 1) {
                    setSelectedItemAndPage(1);
                } else {
                    ToastUtil.showShort(this, "refresh");
                }
                break;
            case R.id.iv_friend:
                if (mainVp.getCurrentItem() != 2) {
                    setSelectedItemAndPage(2);
                } else {
                    ToastUtil.showShort(this, "refresh");
                }
                break;
            case R.id.search:
                ToastUtil.showShort(this, "this is search");
                break;
        }
    }

    private void setSelectedItemAndPage(int index) {
        switch (index) {
            case 0:
                localMusicIv.setSelected(true);
                netMusicIv.setSelected(false);
                friendIv.setSelected(false);
                mainVp.setCurrentItem(0);
                break;
            case 1:
                localMusicIv.setSelected(false);
                netMusicIv.setSelected(true);
                friendIv.setSelected(false);
                mainVp.setCurrentItem(1);
                break;
            case 2:
                localMusicIv.setSelected(false);
                netMusicIv.setSelected(false);
                friendIv.setSelected(true);
                mainVp.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setSelectedItemAndPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
