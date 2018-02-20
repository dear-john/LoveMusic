package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import java.util.List;

import bean.LocalMusic;

/**
 * Created by 李君 on 2018/2/6.
 */

public class LocalRecyclerViewAdapter extends RecyclerView.Adapter<LocalRecyclerViewAdapter.ViewHolder> {
    private List<LocalMusic> mLocalMusicList;
    private ClickListener mListener;

    public LocalRecyclerViewAdapter(List<LocalMusic> localMusicList, ClickListener listener) {
        mLocalMusicList = localMusicList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mMusicOrderTv.setText(String.valueOf(mLocalMusicList.get(position).getMusicOrder()));
        holder.mMusicNameTv.setText(mLocalMusicList.get(position).getMusicName());
        holder.mSingerTv.setText(mLocalMusicList.get(position).getSinger());
        holder.mAlbum.setText(mLocalMusicList.get(position).getAlbum());
        holder.mMusicItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.itemListener(v, holder.getAdapterPosition());
            }
        });
        holder.mMusicMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.moreListener(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
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

    public interface ClickListener {
        void moreListener(View view, int position);

        void itemListener(View view, int position);
    }
}
