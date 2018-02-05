package com.spring_ballet.lovemusic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import base.BaseFragment;
import fragment.AroundMusicFragment;
import fragment.FriendFragment;
import fragment.LocalMusicFragment;
import utils.ToastUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    //主界面
    private FrameLayout menuLayout;
    private ImageView localMusicIv;
    private ImageView netMusicIv;
    private ImageView friendIv;
    private FrameLayout searchLauout;
    private ViewPager mainVp;
    private LinearLayout footLayout;
    private List<BaseFragment> baseFragmentList;

    //滑动菜单
    private NavigationView nav;
    private ImageView navBgIv;
    private ImageView navHeadIconIv;
    private TextView userNameTv;
    private TextView userLevelTv;
    private TextView userSignTv;
    private LinearLayout navFootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        initListeners();
        initViewPager();
        setSelectedItemAndPage(1);
    }

    private void initViewPager() {
        baseFragmentList = new ArrayList<>(3);
        baseFragmentList.add(new LocalMusicFragment());
        baseFragmentList.add(new AroundMusicFragment());
        baseFragmentList.add(new FriendFragment());
        mainVp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), baseFragmentList));
        mainVp.setOffscreenPageLimit(2);
    }

    private void initListeners() {
        //主界面
        menuLayout.setOnClickListener(this);
        localMusicIv.setOnClickListener(this);
        netMusicIv.setOnClickListener(this);
        friendIv.setOnClickListener(this);
        searchLauout.setOnClickListener(this);
        mainVp.addOnPageChangeListener(this);
        footLayout.setOnClickListener(this);

        //滑动菜单
        nav.setNavigationItemSelectedListener(this);
        navHeadIconIv.setOnClickListener(this);
        navBgIv.setOnClickListener(this);
        userNameTv.setOnClickListener(this);
        userLevelTv.setOnClickListener(this);
        userSignTv.setOnClickListener(this);
    }

    private void initWidgets() {
        drawerLayout = findViewById(R.id.draw_layout);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //主界面
        menuLayout = findViewById(R.id.main_menu);
        localMusicIv = findViewById(R.id.iv_local_music);
        netMusicIv = findViewById(R.id.iv_around_music);
        friendIv = findViewById(R.id.iv_friend);
        searchLauout = findViewById(R.id.search);
        mainVp = findViewById(R.id.main_viewpager);
        footLayout = findViewById(R.id.main_footview);

        //滑动菜单
        nav = findViewById(R.id.nav);
        View headView = nav.getHeaderView(0);
        navBgIv = headView.findViewById(R.id.nav_bg);
        navHeadIconIv = headView.findViewById(R.id.nav_head_icon);
        userNameTv = headView.findViewById(R.id.tv_user_name);
        userLevelTv = headView.findViewById(R.id.tv_user_level);
        userSignTv = headView.findViewById(R.id.tv_user_sign);
        navFootLayout = findViewById(R.id.nav_foot_layout);
        navFootLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_foot_layout:
                ToastUtil.showShort(this, "nav foot");
                break;
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
            case R.id.main_footview:
                ToastUtil.showShort(this, "this is foot");
                break;
            case R.id.nav_head_icon:
                ToastUtil.showShort(this, "this is head icon");
                break;
            case R.id.nav_bg:
                ToastUtil.showShort(this, "this is bg");
                break;
            case R.id.tv_user_name:
                ToastUtil.showShort(this, "this is name");
                break;
            case R.id.tv_user_level:
                ToastUtil.showShort(this, "this is level");
                break;
            case R.id.tv_user_sign:
                ToastUtil.showShort(this, "this is sign");
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_my_msg:
                ToastUtil.showShort(this, "msg");
                break;
            case R.id.item_my_friend:
                ToastUtil.showShort(this, "friend");
                break;
            case R.id.item_quit_ontime:
                ToastUtil.showShort(this, "quit");
                break;
            case R.id.item_scan:
                ToastUtil.showShort(this, "scan");
                break;
        }
        return true;
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
