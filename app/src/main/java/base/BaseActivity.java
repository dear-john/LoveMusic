package base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spring_ballet.lovemusic.MusicPlayService;
import com.spring_ballet.lovemusic.R;

import utils.SharedPreferencesUtil;
import utils.ToastUtil;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mSongIv;
    private ImageView mPreIv;
    private ImageView mNextIv;
    private ImageView mPlayAndPauseIv;
    private TextView mSongNameTv;
    private TextView mSingerNameTv;
    private View mControllLayout;

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
        mSongIv = findViewById(R.id.iv_controll_song_icon);
        mPreIv = findViewById(R.id.iv_controll_music_pre);
        mNextIv = findViewById(R.id.iv_controll_music_next);
        mPlayAndPauseIv = findViewById(R.id.iv_controll_music_play_pause);
        mSongNameTv = findViewById(R.id.tv_controll_song_name);
        mSingerNameTv = findViewById(R.id.tv_controll_singer_name);
        mControllLayout = findViewById(R.id.layout_controll);
    }

    private void initBaseListeners() {
        mPreIv.setOnClickListener(this);
        mPlayAndPauseIv.setOnClickListener(this);
        mNextIv.setOnClickListener(this);
        mControllLayout.setOnClickListener(this);
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

    protected void refreshControllLayout(String icon, String url, String songName, String singerName) {
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

}
