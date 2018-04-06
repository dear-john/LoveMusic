package base;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spring_ballet.lovemusic.MusicPlayService;
import com.spring_ballet.lovemusic.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import adapter.MyFragmentAdapter;
import adapter.NavListViewAdapter;
import bean.PlaylistItem;
import fragment.AroundMusicFragment;
import fragment.FriendFragment;
import fragment.LocalMusicFragment;
import utils.PlaylistDialog;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    //应用主页
    private FrameLayout menuLayout;
    private ImageView localMusicIv;
    private ImageView netMusicIv;
    private ImageView friendIv;
    private FrameLayout searchLauout;
    private ViewPager baseVp;
    private Toolbar toolbar;
    private List<BaseFragment> mPreviousFragList;
    private BaseFragment mCurrentFrag;

    //控制栏
    private ImageView mSongIv;
    private ImageView mPlayAndPauseIv;
    private ImageView mPlayListIv;
    private TextView mSongNameTv;
    private TextView mSingerNameTv;
    private View mControllLayout;

    //滑动菜单
    protected DrawerLayout drawerLayout;
    private ListView mListView;
    private ImageView mUserIconIv;
    private TextView userNameTv;
    private TextView userLevelTv;
    private TextView userSignTv;
    private View mModeLayout;
    private View mSettingsLayout;
    private View mQuitLayout;

    //播放列表是否已经初始化
    private static boolean hasInited = false;

    //控制栏所需资源
    private static String sIcon;
    private static String sUrl;
    private static String sSongName;
    private static String sSingerName;

    private PlaylistDialog mDialog;

    protected static MusicPlayService sService;
    protected static ServiceConnection sConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayService.MyBinder binder = (MusicPlayService.MyBinder) service;
            sService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //绑定service
        Intent intent = new Intent(this, MusicPlayService.class);
        bindService(intent, sConnection, BIND_AUTO_CREATE);

        initWidgets();
        setListener();
        initViewPager();
        initControllLayout();

        mPreviousFragList = new ArrayList<>();
        mCurrentFrag = null;
    }

    //初始化控件
    private void initWidgets() {

        //Toolbar
        toolbar = findViewById(R.id.base_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowTitleEnabled(false);

        menuLayout = findViewById(R.id.base_menu);
        localMusicIv = findViewById(R.id.iv_local_music);
        netMusicIv = findViewById(R.id.iv_net_music);
        friendIv = findViewById(R.id.iv_friend);
        searchLauout = findViewById(R.id.search);

        //控制栏
        mSongIv = findViewById(R.id.iv_controll_song_icon);
        mPlayListIv = findViewById(R.id.iv_controll_playlist);
        mPlayAndPauseIv = findViewById(R.id.iv_controll_music_play_pause);
        mSongNameTv = findViewById(R.id.tv_controll_song_name);
        mSingerNameTv = findViewById(R.id.tv_controll_singer_name);
        mControllLayout = findViewById(R.id.layout_controll);

        //滑动菜单
        drawerLayout = findViewById(R.id.draw_layout);
        mListView = findViewById(R.id.main_listview);
        mListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.nav_head, mListView, false));
        mListView.setAdapter(new NavListViewAdapter(this));
        mListView.setDivider(null);
        mUserIconIv = findViewById(R.id.nav_head_icon);
        userNameTv = findViewById(R.id.tv_user_name);
        userLevelTv = findViewById(R.id.tv_user_level);
        userSignTv = findViewById(R.id.tv_user_sign);
        mModeLayout = findViewById(R.id.layout_change_mode);
        mSettingsLayout = findViewById(R.id.layout_settings);
        mQuitLayout = findViewById(R.id.layout_quit);
    }

    //设置监听
    private void setListener() {

        //应用主页
        menuLayout.setOnClickListener(this);
        localMusicIv.setOnClickListener(this);
        netMusicIv.setOnClickListener(this);
        friendIv.setOnClickListener(this);
        searchLauout.setOnClickListener(this);

        //控制栏
        mPlayListIv.setOnClickListener(this);
        mPlayAndPauseIv.setOnClickListener(this);
        mControllLayout.setOnClickListener(this);

        //滑动菜单
        userNameTv.setOnClickListener(this);
        userLevelTv.setOnClickListener(this);
        userSignTv.setOnClickListener(this);
        mUserIconIv.setOnClickListener(this);
        mModeLayout.setOnClickListener(this);
        mSettingsLayout.setOnClickListener(this);
        mQuitLayout.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtil.showShort(BaseActivity.this, "home page");
                        break;
                    case 1:
                        ToastUtil.showShort(BaseActivity.this, "msg");
                        break;
                    case 2:
                        ToastUtil.showShort(BaseActivity.this, "friend");
                        break;
                    case 3:
                        ToastUtil.showShort(BaseActivity.this, "stop on time");
                        break;
                    case 4:
                        ToastUtil.showShort(BaseActivity.this, "scan");
                        break;
                }
                drawerLayout.closeDrawers();
            }
        });
    }

    //初始化viewpager
    private void initViewPager() {
        baseVp = findViewById(R.id.vp_base_contanier);
        List<BaseFragment> baseFragmentList = new ArrayList<>(3);
        baseFragmentList.add(new LocalMusicFragment());
        baseFragmentList.add(new AroundMusicFragment());
        baseFragmentList.add(new FriendFragment());
        baseVp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), baseFragmentList));
        baseVp.setOffscreenPageLimit(2);
        baseVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
        setSelectedItemAndPage(1);
    }

    //初始化底部控制栏
    private void initControllLayout() {
        String icon = SharedPreferencesUtil.getStringData(this, "icon");
        String songName = SharedPreferencesUtil.getStringData(this, "song");
        String singerName = SharedPreferencesUtil.getStringData(this, "singer");

        //为空则代表没有播放记录，所以隐藏底部控制栏
        if (TextUtils.isEmpty(singerName) && TextUtils.isEmpty(songName))
            mControllLayout.setVisibility(View.GONE);
        else {
            if (!TextUtils.isEmpty(icon)) Glide.with(this).load(icon).into(mSongIv);
            else mSongIv.setImageResource(R.drawable.default_controll_song_bg);
            mSongNameTv.setText(songName);
            mSingerNameTv.setText(singerName);
        }
    }

    private void initPlayListDialog() {
        mDialog = new PlaylistDialog();
        List<PlaylistItem> list = new LinkedList<>();
        list.add(new PlaylistItem("dear1", "love1", "", ""));
        list.add(new PlaylistItem("dear2", "love2", "", ""));
        list.add(new PlaylistItem("dear3", "love3", "", ""));
        list.add(new PlaylistItem("dear4", "love4", "", ""));
        list.add(new PlaylistItem("dear5", "love5", "", ""));
        list.add(new PlaylistItem("dear6", "love6", "", ""));
        list.add(new PlaylistItem("dear7", "love7", "", ""));
        list.add(new PlaylistItem("dear8", "love8", "", ""));
        mDialog.init(this, list, 1);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            showDialog();
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showShort(this, "授权失败，部分功能将不能使用");
            }
        }
    }

    public void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("是否进入设置进行授权?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", BaseActivity.this.getPackageName(), null));
                BaseActivity.this.startActivity(intent);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtil.showShort(BaseActivity.this, "您取消了授权");
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void refreshAfterPlay(String icon, String url, String songName, String singerName) {
        if (!TextUtils.isEmpty(icon)) Glide.with(this).load(icon).into(mSongIv);
        else mSongIv.setImageResource(R.drawable.default_controll_song_bg);
        if (!TextUtils.isEmpty(songName)) mSongNameTv.setText(songName);
        if (!TextUtils.isEmpty(singerName)) mSingerNameTv.setText(singerName);
        //保存数据
        sIcon = icon;
        sUrl = url;
        sSongName = songName;
        sSingerName = singerName;
        //开始播放
        if (!sService.isPlayerPlaying()) mPlayAndPauseIv.setImageResource(R.drawable.music_playing);
        sService.play(url);
    }

    private void savePlayRecord() {
        if (!TextUtils.isEmpty(sUrl) && !TextUtils.isEmpty(sSongName) && !TextUtils.isEmpty(sSingerName)) {
            SharedPreferencesUtil.putStringData(this, "url", sUrl);
            SharedPreferencesUtil.putStringData(this, "song", sSongName);
            SharedPreferencesUtil.putStringData(this, "singer", sSingerName);
            SharedPreferencesUtil.putStringData(this, "icon", sIcon);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //toolbar
            case R.id.base_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_local_music:
                if (baseVp.getCurrentItem() != 0) {
                    setSelectedItemAndPage(0);
                }
                break;
            case R.id.iv_net_music:
                if (baseVp.getCurrentItem() != 1) {
                    setSelectedItemAndPage(1);
                }
                break;
            case R.id.iv_friend:
                if (baseVp.getCurrentItem() != 2) {
                    setSelectedItemAndPage(2);
                }
                break;
            case R.id.search:
                ToastUtil.showShort(this, "this is search");
                break;

            //控制栏
            case R.id.iv_controll_playlist:
                if (!hasInited) {
                    initPlayListDialog();
                    hasInited = true;
                }
                mDialog.show();
                break;
            case R.id.iv_controll_music_play_pause:
                if (!sService.isPlayerPlaying())
                    mPlayAndPauseIv.setImageResource(R.drawable.music_playing);
                else
                    mPlayAndPauseIv.setImageResource(R.drawable.music_pause);
                sService.playOrPause();
                break;
            case R.id.layout_controll:
                ToastUtil.showShort(this, "music controll");
                break;

            //滑动菜单
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

    @Override
    protected void onDestroy() {
        sService.unbindService(sConnection);
        savePlayRecord();
        mDialog.destory();
        mCurrentFrag = null;
        mPreviousFragList.clear();
        mPreviousFragList = null;
        super.onDestroy();
    }

    public final void showNewView(BaseFragment frag) {
        if (baseVp.getVisibility() == View.VISIBLE) {
            getSupportFragmentManager().beginTransaction().add(R.id.layout_base_contanier, frag).commit();
            toolbar.setVisibility(View.GONE);
            baseVp.setVisibility(View.INVISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            if (mCurrentFrag.equals(frag)) return;
            mPreviousFragList.add(mCurrentFrag);
            getSupportFragmentManager().beginTransaction().hide(mCurrentFrag)
                    .add(R.id.layout_base_contanier, frag).commit();
        }
        mCurrentFrag = frag;
    }

    public final void showOldView() {
        final int size = mPreviousFragList.size();
        if (size >= 1) {
            getSupportFragmentManager().beginTransaction().remove(mCurrentFrag)
                    .show(mCurrentFrag = mPreviousFragList.get(size - 1)).commit();
            mPreviousFragList.remove(size - 1);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            baseVp.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().remove(mCurrentFrag).commit();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
                return true;
            } else {
                if (baseVp.getVisibility() != View.VISIBLE) {
                    showOldView();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setSelectedItemAndPage(int index) {
        switch (index) {
            case 0:
                localMusicIv.setSelected(true);
                netMusicIv.setSelected(false);
                friendIv.setSelected(false);
                baseVp.setCurrentItem(0);
                break;
            case 1:
                localMusicIv.setSelected(false);
                netMusicIv.setSelected(true);
                friendIv.setSelected(false);
                baseVp.setCurrentItem(1);
                break;
            case 2:
                localMusicIv.setSelected(false);
                netMusicIv.setSelected(false);
                friendIv.setSelected(true);
                baseVp.setCurrentItem(2);
                break;
        }
    }

}