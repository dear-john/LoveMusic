package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import app.CommonApis;
import base.BaseFragment;
import bean.MessageEvent;
import bean.SingerInfo;
import bean.SingerMusicList;
import fragment.SingerAlbumFragment;
import fragment.SingerInfoFragment;
import fragment.SingerMusicFragment;
import fragment.SingerVideoFragment;
import utils.OkHttpUtil;

public class SingerInfoActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private SingerMusicList mSingerMusicList;
    private SingerInfo mSingerInfo;

    public SingerMusicList getSingerMusicList() {
        return mSingerMusicList;
    }

    public SingerInfo getSingerInfo() {
        return mSingerInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_info);
        Toolbar toolbar = findViewById(R.id.singer_info_toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.iv_singer_info_back).setOnClickListener(this);
        findViewById(R.id.iv_singer_share).setOnClickListener(this);
        final TextView tvSingerName = findViewById(R.id.tv_singer_name);
        TabLayout tabLayout = findViewById(R.id.tablayout_singer_info);
        mViewPager = findViewById(R.id.vp_singer_info);
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
        final ImageView ivSingerBg = findViewById(R.id.iv_default_singer_bg);
        final String uid = getIntent().getStringExtra("data");
        OkHttpUtil.loadData(CommonApis.SINGER_MUSIC_LIST + uid, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                mSingerMusicList = JSON.parseObject(data, SingerMusicList.class);
                MessageEvent event = new MessageEvent();
                event.setMusicDataFinish(true);
                EventBus.getDefault().post(event);
                tvSingerName.setText(mSingerMusicList.getSonglist().get(0).getAuthor());
            }
        });
        OkHttpUtil.loadData(CommonApis.SINGER_INFO_API + uid, new OkHttpUtil.OnLoadDataFinish() {
            @Override
            public void loadDataFinish(String data) {
                mSingerInfo = JSON.parseObject(data, SingerInfo.class);
                Glide.with(SingerInfoActivity.this)
                        .setDefaultRequestOptions(new RequestOptions()
                                .error(R.drawable.default_artist_bg))
                        .load(mSingerInfo.getAvatar_s1000())
                        .into(ivSingerBg);
                tvSingerName.setText(mSingerInfo.getName());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_singer_info_back:
                finish();
                break;
            case R.id.iv_singer_share:
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
