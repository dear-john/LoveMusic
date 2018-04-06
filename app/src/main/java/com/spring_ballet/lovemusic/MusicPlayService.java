package com.spring_ballet.lovemusic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

import utils.SharedPreferencesUtil;

public class MusicPlayService extends Service {

    private static MediaPlayer sMediaPlayer;

    public MusicPlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        sMediaPlayer = new MediaPlayer();
        return new MyBinder();
    }

    public void play(String uri) {
        if (sMediaPlayer == null) sMediaPlayer = new MediaPlayer();
        sMediaPlayer.reset();
        try {
            sMediaPlayer.setDataSource(uri);
            sMediaPlayer.prepare();
            sMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerPlaying() {
        return sMediaPlayer.isPlaying();
    }

    public void playOrPause() {
        if (sMediaPlayer == null) sMediaPlayer = new MediaPlayer();
        if (sMediaPlayer.isPlaying())
            sMediaPlayer.pause();
        else sMediaPlayer.start();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        sMediaPlayer.stop();
        sMediaPlayer.release();
        sMediaPlayer = null;
    }

    public class MyBinder extends Binder {
        public MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }
}
