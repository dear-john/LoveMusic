package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import java.util.List;

import bean.PlaylistItem;

/**
 * Created by 李君 on 2018/3/5.
 */

public class PlayListViewAdapter extends BaseAdapter {

    public static final int DELETE_MUSIC_TAG = -1;
    private Context mContext;
    private List<PlaylistItem> mList;
    private int currentItem = -1;
    private PopItemClickListener mListener;

    public PlayListViewAdapter(Context context, List<PlaylistItem> list) {
        mContext = context;
        mList = list;
    }

    public void setListener(PopItemClickListener listener) {
        mListener = listener;
    }

    public int getCurrentItem() {
        return currentItem;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.playlist_listview_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else mViewHolder = (ViewHolder) convertView.getTag();

        //删除指定item
        mViewHolder.mDeleteMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
                if (mListener == null) throw new NullPointerException("listener has not been init");
                mListener.listener(DELETE_MUSIC_TAG);
            }
        });
        mViewHolder.mPlaylistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) throw new NullPointerException("listener has not been init");
                mListener.listener(position);
            }
        });

        //判断当前item是否是被选中的item
        if (currentItem == position) {
            mViewHolder.mPlaying.setVisibility(View.VISIBLE);
            mViewHolder.mDivider.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
            mViewHolder.mSingerName.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
            mViewHolder.mSongName.setTextColor(mContext.getResources().getColor(R.color.colorTheme));
        } else {
            mViewHolder.mPlaying.setVisibility(View.GONE);
            mViewHolder.mDivider.setTextColor(mContext.getResources().getColor(R.color.colorBg));
            mViewHolder.mSingerName.setTextColor(mContext.getResources().getColor(R.color.colorBg));
            mViewHolder.mSongName.setTextColor(mContext.getResources().getColor(android.R.color.black));
        }

        //判断是否因为歌名太长而需要隐藏后面内容
        if (mViewHolder.mSongName.getText().length() > mViewHolder.mSongName.getMaxEms()) {
            mViewHolder.mDivider.setVisibility(View.GONE);
            mViewHolder.mSingerName.setVisibility(View.GONE);
        }

        PlaylistItem item = mList.get(position);
        mViewHolder.mSongName.setText(item.getSongName());
        mViewHolder.mSingerName.setText(item.getSingerName());
        return convertView;
    }

    public void changeSelected(int pos) {
        if (pos != currentItem) {
            currentItem = pos;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        private View mPlaylistItem;
        private TextView mSongName;
        private TextView mSingerName;
        private ImageView mPlaying;
        private TextView mDivider;
        private ImageView mDeleteMusic;

        ViewHolder(View convertView) {
            mPlaylistItem = convertView.findViewById(R.id.layout_playlist_lv_item);
            mSongName = convertView.findViewById(R.id.tv_playlist_song_name);
            mSingerName = convertView.findViewById(R.id.tv_playlist_singer_name);
            mPlaying = convertView.findViewById(R.id.iv_playlist_playing);
            mDivider = convertView.findViewById(R.id.tv_playlist_divider);
            mDeleteMusic = convertView.findViewById(R.id.iv_playlist_delete_music);
        }
    }

    public interface PopItemClickListener {
        void listener(int position);
    }
}

