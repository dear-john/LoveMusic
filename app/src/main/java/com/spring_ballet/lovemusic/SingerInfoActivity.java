package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import app.CommonApis;
import base.BaseActivity;
import base.BaseFragment;
import bean.MessageEvent;
import bean.SingerInfo;
import bean.SingerMusicList;
import fragment.SingerAlbumFragment;
import fragment.SingerInfoFragment;
import fragment.SingerMusicFragment;
import fragment.SingerVideoFragment;
import utils.OkHttpUtil;
import utils.ShareUtil;
import utils.ToastUtil;

public class SingerInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private SingerMusicList mSingerMusicList;
    private SingerInfo mSingerInfo;
    private TextView tvSingerName;
    private TabLayout tabLayout;
    private ImageView ivSingerBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
        initListeners();
        initViewPager();
        loadData();
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

    private void loadData() {
        final String uid = getIntent().getStringExtra("data");
        OkHttpUtil.loadData(CommonApis.SINGER_MUSIC_LIST + uid, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                if (data != null) {
                    mSingerMusicList = JSON.parseObject(data, SingerMusicList.class);
                    MessageEvent event = new MessageEvent();
                    event.setMusicDataFinish(true);
                    EventBus.getDefault().post(event);
                } else ToastUtil.showShort(SingerInfoActivity.this, "music load error");
            }
        });

        OkHttpUtil.loadData(CommonApis.SINGER_INFO_API + uid, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                boolean hasData = true;
                if (data != null) {
                    mSingerInfo = JSON.parseObject(data, SingerInfo.class);
                    String url = mSingerInfo.getAvatar_s1000();
                    if (TextUtils.isEmpty(url)) {
                        url = mSingerInfo.getAvatar_s500();
                        if (TextUtils.isEmpty(url)) {
                            url = mSingerInfo.getAvatar_big();
                            if (TextUtils.isEmpty(url)) {
                                url = mSingerInfo.getAvatar_s180();
                            } else hasData = false;
                        }
                    }
                    if (hasData) Glide.with(SingerInfoActivity.this).load(url).into(ivSingerBg);
                    else ToastUtil.showShort(SingerInfoActivity.this, "歌手信息加载失败，请稍后重试");
                    tvSingerName.setText(mSingerInfo.getName());
                }
            }
        });
    }

    public SingerMusicList getSingerMusicList() {
        return mSingerMusicList;
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
}
