package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.ListViewAdapter;
import adapter.MyFragmentAdapter;
import base.BaseActivity;
import base.BaseFragment;
import fragment.AroundMusicFragment;
import fragment.FriendFragment;
import fragment.LocalMusicFragment;
import utils.ToastUtil;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private DrawerLayout drawerLayout;

    //主界面
    private FrameLayout menuLayout;
    private ImageView localMusicIv;
    private ImageView netMusicIv;
    private ImageView friendIv;
    private FrameLayout searchLauout;
    private ViewPager mainVp;

    //滑动菜单
    private ListView mListView;
    private ImageView mUserIconIv;
    private TextView userNameTv;
    private TextView userLevelTv;
    private TextView userSignTv;
    private View mModeLayout;
    private View mSettingsLayout;
    private View mQuitLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
        initListeners();
        initViewPager();
        setSelectedItemAndPage(1);
    }

    @Override
    protected void initWidgets() {
        drawerLayout = findViewById(R.id.draw_layout);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);

        //主界面
        menuLayout = findViewById(R.id.main_menu);
        localMusicIv = findViewById(R.id.iv_local_music);
        netMusicIv = findViewById(R.id.iv_around_music);
        friendIv = findViewById(R.id.iv_friend);
        searchLauout = findViewById(R.id.search);
        mainVp = findViewById(R.id.main_viewpager);

        //滑动菜单
        mListView = findViewById(R.id.main_listview);
        mListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.nav_head, mListView, false));
        mListView.setAdapter(new ListViewAdapter(this));
        mListView.setDivider(null);
        mUserIconIv = findViewById(R.id.nav_head_icon);
        userNameTv = findViewById(R.id.tv_user_name);
        userLevelTv = findViewById(R.id.tv_user_level);
        userSignTv = findViewById(R.id.tv_user_sign);
        mModeLayout = findViewById(R.id.layout_change_mode);
        mSettingsLayout = findViewById(R.id.layout_settings);
        mQuitLayout = findViewById(R.id.layout_quit);
    }

    @Override
    protected void initListeners() {
        //主界面
        menuLayout.setOnClickListener(this);
        localMusicIv.setOnClickListener(this);
        netMusicIv.setOnClickListener(this);
        friendIv.setOnClickListener(this);
        searchLauout.setOnClickListener(this);
        mainVp.addOnPageChangeListener(this);

        //滑动菜单
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtil.showShort(MainActivity.this, "home page");
                        break;
                    case 1:
                        ToastUtil.showShort(MainActivity.this, "msg");
                        break;
                    case 2:
                        ToastUtil.showShort(MainActivity.this, "friend");
                        break;
                    case 3:
                        ToastUtil.showShort(MainActivity.this, "stop on time");
                        break;
                    case 4:
                        ToastUtil.showShort(MainActivity.this, "scan");
                        break;
                }
                drawerLayout.closeDrawers();
            }
        });
        userNameTv.setOnClickListener(this);
        userLevelTv.setOnClickListener(this);
        userSignTv.setOnClickListener(this);
        mUserIconIv.setOnClickListener(this);
        mModeLayout.setOnClickListener(this);
        mSettingsLayout.setOnClickListener(this);
        mQuitLayout.setOnClickListener(this);
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
            case R.id.tv_user_name:
                ToastUtil.showShort(this, "user name");
                break;
            case R.id.tv_user_level:
                ToastUtil.showShort(this, "user level");
                break;
            case R.id.tv_user_sign:
                ToastUtil.showShort(this, "user sign");
                break;
            case R.id.nav_head_icon:
                ToastUtil.showShort(this, "user icon");
                break;
            case R.id.layout_change_mode:
                ToastUtil.showShort(this, "change mode");
                break;
            case R.id.layout_settings:
                ToastUtil.showShort(this, "settings");
                break;
            case R.id.layout_quit:
                finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
