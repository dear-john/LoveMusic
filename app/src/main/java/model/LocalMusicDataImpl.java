package model;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import bean.LocalMusic;

/**
 * Created by 李君 on 2018/3/23.
 */

public class LocalMusicDataImpl implements MusicData<LocalMusic> {

    private Context mContext;

    public LocalMusicDataImpl(Context context) {
        mContext = context;
    }

    @Override
    public void getMusicData(OnDataLoadFinished<LocalMusic> loadFinished) {
        int musicOrder = 1;
        List<LocalMusic> localMusicList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            LocalMusic localMusic;
            while (cursor.moveToNext()) {
                localMusic = new LocalMusic();
                localMusic.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                localMusic.setMusicName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                localMusic.setSinger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                localMusic.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                localMusic.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                localMusic.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                localMusic.setMusicOrder(musicOrder++);
                localMusicList.add(localMusic);
            }
            cursor.close();
        }
        mContext = null;
        loadFinished.onLoadFinished(localMusicList);
    }
}
