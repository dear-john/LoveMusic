package utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import java.util.List;
import java.util.Locale;

import adapter.PlayListViewAdapter;
import bean.PlaylistItem;

/**
 * Created by 李君 on 2018/3/26.
 */

public class PlaylistWindow {

    private Dialog mDialog;
    private ImageView playModeIv;
    private TextView playModeTv;

    private static int currentPlayMode = -1;

    private static final int PLAY_MODE_NUMBER = 3;
    private static final String[] PLAY_MODE_NAME = {"列表循环", "随机播放", "单曲循环"};
    private static final int[] PLAY_MODE_ICON = {R.drawable.play_icon_loop, R.drawable.play_icon_random, R.drawable.play_icon_one};

    public void showPlaylist(final Context context, final List<PlaylistItem> list, int currentItem) {

        //加载播放列表
        View playlistView = LayoutInflater.from(context).inflate(R.layout.layout_playlist, null);

        //初始化dialog
        mDialog = new Dialog(context, R.style.BottomDialog);
        mDialog.setContentView(playlistView);
        mDialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = playlistView.getLayoutParams();
        layoutParams.height = (int) context.getResources().getDimension(R.dimen.playlist_dialog_height);
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        playlistView.setLayoutParams(layoutParams);
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.BottomDialog_Animation);
        }
        mDialog.show();

        //列表播放模式
        View playModeView = playlistView.findViewById(R.id.layout_playlist_play_mode);
        playModeIv = playlistView.findViewById(R.id.iv_playlist_play_mode);
        playModeTv = playlistView.findViewById(R.id.tv_playlist_play_mode);
        playModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayMode();
            }
        });

        //初始化当前播放模式
        //类似于当前播放状态的读取与设置
        changePlayMode();

        //列表歌曲数目
        final TextView songNumTv = playlistView.findViewById(R.id.tv_playlist_song_number);
        songNumTv.setText(String.format(Locale.CHINA, "( %d )", list.size()));

        //初始化listview
        final ListView playListLv = playlistView.findViewById(R.id.lv_playlist);
        final PlayListViewAdapter adapter = new PlayListViewAdapter(context, list);
        adapter.changeSelected(currentItem);
        adapter.setListener(new PlayListViewAdapter.PopItemClickListener() {
            @Override
            public void listener(int position) {
                if (position == PlayListViewAdapter.DELETE_MUSIC_TAG) {
                    if (list.size() == 0) {
                        mDialog.dismiss();
                        return;
                    }
                    position = adapter.getCurrentItem();
                    if (position > list.size() - 1) position = 0;
                    adapter.changeSelected(position);
                    songNumTv.setText(String.format(Locale.CHINA, "( %d )", list.size()));
                    return;
                }
                adapter.changeSelected(position);
            }
        });
        playListLv.setAdapter(adapter);

        //收藏列表全部音乐
        View collectView = playlistView.findViewById(R.id.layout_playlist_collect);
        collectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(context, "collect all music");
            }
        });

        //删除列表全部音乐
        ImageView deleteIv = playlistView.findViewById(R.id.iv_playlist_delete_all);
        deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    playListLv.setAdapter(adapter);
                }
            }
        });
    }

    private void changePlayMode() {
        currentPlayMode = (currentPlayMode + 1) % PLAY_MODE_NUMBER;
        playModeIv.setImageResource(PLAY_MODE_ICON[currentPlayMode]);
        playModeTv.setText(PLAY_MODE_NAME[currentPlayMode]);
    }
}
