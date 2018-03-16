package base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
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

import adapter.ListViewAdapter;
import utils.SharedPreferencesUtil;
import utils.ToastUtil;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    //控制栏
    private ImageView mSongIv;
    private ImageView mPreIv;
    private ImageView mNextIv;
    private ImageView mPlayAndPauseIv;
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

    private static int sSubClassCount;
    private static boolean isFirst = true;
    private static boolean hasPlayed = false;
    private static String sIcon;
    private static String sUrl;
    private static String sSongName;
    private static String sSingerName;

    protected static MusicPlayService mService;
    protected static ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayService.MyBinder binder = (MusicPlayService.MyBinder) service;
            mService = binder.getService();
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
            bindService(intent, mConnection, BIND_AUTO_CREATE);
        }
        setContentView(R.layout.activity_base);
        initBaseWidgets();
        ViewGroup container = findViewById(R.id.layout_container);
        container.removeAllViews();
        container.addView(LayoutInflater.from(this).inflate(getContainerView(), null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBaseListeners();
        initControllLayout();
        if (isFirst) isFirst = false;
        else refreshMusicStateIcon();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePlayRecord();
    }

    private void initBaseWidgets() {
        //控制栏
        mSongIv = findViewById(R.id.iv_controll_song_icon);
        mPreIv = findViewById(R.id.iv_controll_music_pre);
        mNextIv = findViewById(R.id.iv_controll_music_next);
        mPlayAndPauseIv = findViewById(R.id.iv_controll_music_play_pause);
        mSongNameTv = findViewById(R.id.tv_controll_song_name);
        mSingerNameTv = findViewById(R.id.tv_controll_singer_name);
        mControllLayout = findViewById(R.id.layout_controll);

        //滑动菜单
        drawerLayout = findViewById(R.id.draw_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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

    private void initBaseListeners() {

        //控制栏
        mPreIv.setOnClickListener(this);
        mPlayAndPauseIv.setOnClickListener(this);
        mNextIv.setOnClickListener(this);
        mControllLayout.setOnClickListener(this);

        //滑动菜单
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
        userNameTv.setOnClickListener(this);
        userLevelTv.setOnClickListener(this);
        userSignTv.setOnClickListener(this);
        mUserIconIv.setOnClickListener(this);
        mModeLayout.setOnClickListener(this);
        mSettingsLayout.setOnClickListener(this);
        mQuitLayout.setOnClickListener(this);
    }

    protected void initControllLayout() {
        String icon = SharedPreferencesUtil.getStringData(this, "icon");
        String songName = SharedPreferencesUtil.getStringData(this, "song");
        String singerName = SharedPreferencesUtil.getStringData(this, "singer");
        if (!TextUtils.isEmpty(icon)) Glide.with(this).load(icon).into(mSongIv);
        if (!TextUtils.isEmpty(songName)) mSongNameTv.setText(songName);
        if (!TextUtils.isEmpty(singerName)) mSingerNameTv.setText(singerName);
    }

    protected abstract int getContainerView();

    protected abstract void initWidgets();

    protected abstract void initListeners();

    private void refreshMusicStateIcon() {
        if (mService.isPlayerPlaying())
            mPlayAndPauseIv.setImageResource(R.drawable.music_playing);
        else
            mPlayAndPauseIv.setImageResource(R.drawable.music_pause);
    }

    public void refreshControllLayout(String icon, String url, String songName, String singerName) {
        if (!TextUtils.isEmpty(icon)) Glide.with(this).load(icon).into(mSongIv);
        if (!TextUtils.isEmpty(songName)) mSongNameTv.setText(songName);
        if (!TextUtils.isEmpty(singerName)) mSingerNameTv.setText(singerName);
        sIcon = icon;
        sUrl = url;
        sSongName = songName;
        sSingerName = singerName;
        if (!mService.isPlayerPlaying()) mPlayAndPauseIv.setImageResource(R.drawable.music_playing);
        mService.play(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //控制栏
            case R.id.iv_controll_music_pre:
                ToastUtil.showShort(this, "pre song");
                break;
            case R.id.iv_controll_music_play_pause:
                if (!mService.isPlayerPlaying())
                    mPlayAndPauseIv.setImageResource(R.drawable.music_playing);
                else
                    mPlayAndPauseIv.setImageResource(R.drawable.music_pause);
                if (!hasPlayed) {
                    hasPlayed = true;
                    mService.play(this);
                } else mService.playOrPause();
                break;
            case R.id.iv_controll_music_next:
                ToastUtil.showShort(this, "next song");
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
            mService.unbindService(mConnection);
            savePlayRecord();
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
