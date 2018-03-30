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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spring_ballet.lovemusic.MusicPlayService;
import com.spring_ballet.lovemusic.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import adapter.NavListViewAdapter;
import bean.PlaylistItem;
import utils.PlaylistWindow;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

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

    //记录当前播放位置
    private static int currentItem;

    //子类数量
    private static int sSubClassCount;

    //判断是否需要重新加载播放栏状态
    private static boolean isFirst = true;

    //控制栏所需资源
    private static String sIcon;
    private static String sUrl;
    private static String sSongName;
    private static String sSingerName;


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
        sSubClassCount++;
        if (sSubClassCount == 1) {
            Intent intent = new Intent(this, MusicPlayService.class);
            bindService(intent, sConnection, BIND_AUTO_CREATE);
        }
        checkPermission();
        //每次都要加载，需要修改
        setContentView(R.layout.activity_base);
        initBaseWidgets();
        initBaseListeners();
        ViewGroup container = findViewById(R.id.layout_container);
        container.addView(LayoutInflater.from(this).inflate(getContainerView(), null));
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

    @Override
    protected void onResume() {
        super.onResume();
        initControllLayout();
        if (isFirst) isFirst = false;
        else initMusicStateIcon();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePlayRecord();
    }

    private void initBaseWidgets() {
        //控制栏
        mSongIv = findViewById(R.id.iv_controll_song_icon);
        mPlayListIv = findViewById(R.id.iv_controll_playlist);
        mPlayAndPauseIv = findViewById(R.id.iv_controll_music_play_pause);
        mSongNameTv = findViewById(R.id.tv_controll_song_name);
        mSingerNameTv = findViewById(R.id.tv_controll_singer_name);
        mControllLayout = findViewById(R.id.layout_controll);

        //滑动菜单
        drawerLayout = findViewById(R.id.draw_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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

    private void initBaseListeners() {
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

    private void initMusicStateIcon() {
        if (sService.isPlayerPlaying())
            mPlayAndPauseIv.setImageResource(R.drawable.music_playing);
        else
            mPlayAndPauseIv.setImageResource(R.drawable.music_pause);
    }

    protected void initControllLayout() {
        showControllLayout();
        String icon = SharedPreferencesUtil.getStringData(this, "icon");
        String songName = SharedPreferencesUtil.getStringData(this, "song");
        String singerName = SharedPreferencesUtil.getStringData(this, "singer");
        if (!TextUtils.isEmpty(icon)) Glide.with(this).load(icon).into(mSongIv);
        else mSongIv.setImageResource(R.drawable.default_controll_song_bg);
        if (!TextUtils.isEmpty(songName)) mSongNameTv.setText(songName);
        if (!TextUtils.isEmpty(singerName)) mSingerNameTv.setText(singerName);
    }

    public void refreshAfterPlay(String icon, String url, String songName, String singerName) {
        SharedPreferencesUtil.putBooleanData(this, "hasPlayed", true);
        showControllLayout();
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

    private void showControllLayout() {
        if (SharedPreferencesUtil.getBooleanData(this, "hasPlayed")) {
            if (mControllLayout.getVisibility() != View.VISIBLE) {
                mControllLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void savePlayRecord() {
        if (!TextUtils.isEmpty(sUrl) && !TextUtils.isEmpty(sSongName) && !TextUtils.isEmpty(sSingerName)) {
            SharedPreferencesUtil.putStringData(this, "url", sUrl);
            SharedPreferencesUtil.putStringData(this, "song", sSongName);
            SharedPreferencesUtil.putStringData(this, "singer", sSingerName);
            SharedPreferencesUtil.putStringData(this, "icon", sIcon);
        }
    }

    protected abstract int getContainerView();

    protected abstract void initWidgets();

    protected abstract void initListeners();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //控制栏
            case R.id.iv_controll_playlist:
                List<PlaylistItem> list = new LinkedList<>();
                list.add(new PlaylistItem("dear1", "love1", "", ""));
                list.add(new PlaylistItem("dear2", "love2", "", ""));
                list.add(new PlaylistItem("dear3", "love3", "", ""));
                list.add(new PlaylistItem("dear4", "love4", "", ""));
                list.add(new PlaylistItem("dear5", "love5", "", ""));
                list.add(new PlaylistItem("dear6", "love6", "", ""));
                list.add(new PlaylistItem("dear7", "love7", "", ""));
                list.add(new PlaylistItem("dear8", "love8", "", ""));
                new PlaylistWindow().showPlaylist(this, list, 1);
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
        super.onDestroy();
        sSubClassCount--;
        if (sSubClassCount == 0) {
            sService.unbindService(sConnection);
            savePlayRecord();
        }
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