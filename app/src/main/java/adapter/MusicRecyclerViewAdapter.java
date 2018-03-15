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
import bean.Song_list;
import utils.BottomDialogUtil;
import utils.PlayOnlineMusicUtil;

/**
 * Created by 李君 on 2018/3/15.
 */

public class MusicRecyclerViewAdapter extends RecyclerView.Adapter<MusicRecyclerViewAdapter.ViewHolder> {
    private List<LocalMusic> mLocalMusicList;
    private List<Song_list> mSongLists;
    private boolean isNetMusic;
    private BaseActivity mBaseActivity;

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
        if (isNetMusic) {
            holder.mMusicOrderTv.setText(String.valueOf(position + 1));
            holder.mMusicNameTv.setText(mSongLists.get(position).getTitle());
            String singer = mSongLists.get(position).getArtist_name();
            if (TextUtils.isEmpty(singer))
                singer = mSongLists.get(position).getAuthor();
            holder.mSingerTv.setText(singer);
            holder.mAlbum.setText(mSongLists.get(position).getAlbum_title());
            holder.mMusicItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song_list songList = mSongLists.get(holder.getAdapterPosition());
                    PlayOnlineMusicUtil.playMusic(mBaseActivity, songList.getSong_id(),
                            songList.getPic_small(), songList.getTitle(), songList.getAuthor());
                }
            });
            holder.mMusicMoreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Song_list songList = mSongLists.get(holder.getAdapterPosition());
                    new BottomDialogUtil().showDialog(mBaseActivity, songList.getTitle(),
                            new Random().nextInt(10000) + 100,
                            songList.getArtist_name(), songList.getAlbum_title(), songList.getAll_artist_ting_uid());
                }
            });
        } else {
            holder.mMusicOrderTv.setText(String.valueOf(mLocalMusicList.get(position).getMusicOrder()));
            holder.mMusicNameTv.setText(mLocalMusicList.get(position).getMusicName());
            holder.mSingerTv.setText(mLocalMusicList.get(position).getSinger());
            holder.mAlbum.setText(mLocalMusicList.get(position).getAlbum());
            holder.mMusicItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalMusic localMusic = mLocalMusicList.get(holder.getAdapterPosition());
                    mBaseActivity.refreshControllLayout(null, localMusic.getPath(), localMusic.getMusicName(), localMusic.getSinger());
                }
            });
            holder.mMusicMoreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalMusic localMusic = mLocalMusicList.get(holder.getAdapterPosition());
                    new BottomDialogUtil().showDialog(mBaseActivity, localMusic.getMusicName(),
                            new Random().nextInt(10000) + 100, localMusic.getSinger(),
                            localMusic.getAlbum(), "local");
                }
            });
        }
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
