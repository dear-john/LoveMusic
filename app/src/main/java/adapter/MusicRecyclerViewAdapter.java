package adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import java.util.List;
import java.util.Random;

import base.BaseActivity;
import bean.LocalMusic;
import bean.PlaylistItem;
import bean.Song_list;
import utils.MoreAboutMusicDialog;
import utils.PlayOnlineMusicUtil;

/**
 * Created by 李君 on 2018/3/15.
 */

public class MusicRecyclerViewAdapter extends RecyclerView.Adapter<MusicRecyclerViewAdapter.ViewHolder> {

    private List<LocalMusic> mLocalMusicList;
    private List<Song_list> mSongLists;

    //true表示网络歌曲，false为本地歌曲
    private boolean isNetMusic;

    private BaseActivity mBaseActivity;

    public static final String LOCAL_MUSIC_TING_UID = "LOCAL_MUSIC_TING_UID";

    public MusicRecyclerViewAdapter(BaseActivity activity, boolean isNetMusic, List<Song_list> songLists, List<LocalMusic> localMusicList) {
        mBaseActivity = activity;
        this.isNetMusic = isNetMusic;
        mSongLists = songLists;
        mLocalMusicList = localMusicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //作为dialog和下一首播放的数据源
        final int commentNumber;
        final String songName;
        final String singerName;
        final String url;
        final String icon;
        final String albumName;
        final String tingUid;

        if (isNetMusic) {
            //网络歌曲
            holder.mMusicOrderTv.setText(String.valueOf(position + 1));
            holder.mMusicNameTv.setText(songName = mSongLists.get(position).getTitle());
            String singer = mSongLists.get(position).getArtist_name();
            if (TextUtils.isEmpty(singer))
                singer = mSongLists.get(position).getAuthor();
            holder.mSingerTv.setText(singerName = singer);
            holder.mAlbum.setText(albumName = mSongLists.get(position).getAlbum_title());

            //点击某一个item
            holder.mMusicItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song_list songList = mSongLists.get(holder.getAdapterPosition());
                    PlayOnlineMusicUtil.playMusic(mBaseActivity, songList.getSong_id(),
                            songList.getPic_small(), songList.getTitle(), songList.getAuthor());
                }
            });

            commentNumber = new Random().nextInt(10000) + 100;
            icon = mSongLists.get(position).getPic_small();
            url = "";
            tingUid = mSongLists.get(position).getAll_artist_ting_uid();
        } else {
            //本地歌曲
            holder.mMusicOrderTv.setText(String.valueOf(mLocalMusicList.get(position).getMusicOrder()));
            holder.mMusicNameTv.setText(songName = mLocalMusicList.get(position).getMusicName());
            holder.mSingerTv.setText(singerName = mLocalMusicList.get(position).getSinger());
            holder.mAlbum.setText(albumName = mLocalMusicList.get(position).getAlbum());

            //点击某一个item
            holder.mMusicItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalMusic localMusic = mLocalMusicList.get(holder.getAdapterPosition());
                    mBaseActivity.refreshAfterPlay(null, localMusic.getPath(), localMusic.getMusicName(), localMusic.getSinger());
                }
            });

            commentNumber = new Random().nextInt(10000) + 100;
            icon = "";
            url = mLocalMusicList.get(position).getPath();
            tingUid = LOCAL_MUSIC_TING_UID;
        }

        //点击右侧菜单
        holder.mMusicMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MoreAboutMusicDialog().showDialog(mBaseActivity, new PlaylistItem
                        (songName, singerName, url, icon), commentNumber, albumName, tingUid);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (isNetMusic) return mSongLists.size();
        return mLocalMusicList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mMusicItemLayout;
        TextView mMusicOrderTv;
        TextView mMusicNameTv;
        TextView mSingerTv;
        TextView mAlbum;
        View mMusicMoreLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mMusicItemLayout = itemView.findViewById(R.id.layout_music_item);
            mMusicOrderTv = itemView.findViewById(R.id.tv_music_order);
            mMusicNameTv = itemView.findViewById(R.id.tv_music_name);
            mSingerTv = itemView.findViewById(R.id.tv_singer);
            mAlbum = itemView.findViewById(R.id.tv_music_album);
            mMusicMoreLayout = itemView.findViewById(R.id.layout_music_more);
        }
    }

}
