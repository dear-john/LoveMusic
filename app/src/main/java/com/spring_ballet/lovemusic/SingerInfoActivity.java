package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import Presenter.MusicContract;
import Presenter.SingerInfoMusicPresenter;
import adapter.MyFragmentAdapter;
import base.BaseActivity;
import base.BaseFragment;
import bean.SingerInfo;
import fragment.SingerAlbumFragment;
import fragment.SingerInfoFragment;
import fragment.SingerMusicFragment;
import fragment.SingerVideoFragment;
import utils.ShareUtil;
import utils.ToastUtil;

public class SingerInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MusicContract.View<SingerInfo> {

    private ViewPager mViewPager;
    private SingerInfo mSingerInfo;
    private TabLayout tabLayout;
    private ImageView ivSingerBg;
    private TextView tvSingerName;
    private String uid;
    private SingerInfoMusicPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = getIntent().getStringExtra("data");
        initWidgets();
        initListeners();
        initViewPager();
        mPresenter = new SingerInfoMusicPresenter(uid, this);
        mPresenter.loadData();
    }

    @Override
    protected int getContainerView() {
        return R.layout.activity_singer_info;
    }

    @Override
    protected void initWidgets() {
        Toolbar toolbar = findViewById(R.id.singer_info_toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.iv_singer_info_back).setOnClickListener(this);
        findViewById(R.id.iv_singer_share).setOnClickListener(this);
        tvSingerName = findViewById(R.id.tv_singer_name);
        tabLayout = findViewById(R.id.tablayout_singer_info);
        mViewPager = findViewById(R.id.vp_singer_info);
        ivSingerBg = findViewById(R.id.iv_default_singer_bg);
    }

    @Override
    protected void initListeners() {

    }

    private void initViewPager() {
        List<BaseFragment> baseFragmentList = new ArrayList<>(4);
        baseFragmentList.add(new SingerMusicFragment());
        baseFragmentList.add(new SingerAlbumFragment());
        baseFragmentList.add(new SingerVideoFragment());
        baseFragmentList.add(new SingerInfoFragment());
        List<String> titleList = new ArrayList<>(4);
        titleList.add("单曲");
        titleList.add("专辑");
        titleList.add("视频");
        titleList.add("歌手信息");
        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), baseFragmentList, titleList));
        mViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public String getUid() {
        return uid;
    }

    public SingerInfo getSingerInfo() {
        return mSingerInfo;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_singer_info_back:
                finish();
                break;
            case R.id.iv_singer_share:
                ShareUtil.share(SingerInfoActivity.this, "分享歌手" + mSingerInfo.getName()
                        + " " + mSingerInfo.getUrl() + "(来自@"
                        + SingerInfoActivity.this.getResources().getString(R.string.app_name) + ")");
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void refreshView(List<SingerInfo> list) {
        if (list != null && list.size() > 0 && (mSingerInfo = list.get(0)) != null) {
            tvSingerName.setText(mSingerInfo.getName());
            String url = mSingerInfo.getAvatar_s1000();
            if (TextUtils.isEmpty(url)) {
                url = mSingerInfo.getAvatar_s500();
                if (TextUtils.isEmpty(url))
                    url = mSingerInfo.getAvatar_big();
            }
            if (!TextUtils.isEmpty(url))
                Glide.with(SingerInfoActivity.this).load(url).into(ivSingerBg);
        } else ToastUtil.showShort(this, getResources().getString(R.string.loading_failed));
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
