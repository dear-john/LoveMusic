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

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import Presenter.PlayListContract;
import Presenter.PlayListPresenter;
import adapter.PlayListAdapter;
import bean.PlaylistItem;

/**
 * Created by 李君 on 2018/3/26.
 */

public class PlaylistDialog implements PlayListContract.DialogView {

    private Dialog mDialog;
    private ImageView playModeIv;
    private TextView playModeTv;
    private TextView songNumTv;
    private PlayListAdapter adapter;
    private static PlayListContract.Presenter sPresenter;

    public PlaylistDialog init(final Context context, final List<PlaylistItem> list, int currentItem) {

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

        //列表歌曲数目
        songNumTv = playlistView.findViewById(R.id.tv_playlist_song_number);
        songNumTv.setText(String.format(Locale.CHINA, "( %d )", list.size()));

        //初始化listview
        ListView playListLv = playlistView.findViewById(R.id.lv_playlist);
        adapter = new PlayListAdapter(context, list, currentItem);
        sPresenter = new PlayListPresenter(adapter, this);
        playListLv.setAdapter(adapter);
        adapter.setListener(new PlayListAdapter.OnAddAndDeleteMusicListener() {
            @Override
            public void onAddAndDelele(int size) {
                if (size == 0) mDialog.dismiss();
                else songNumTv.setText(String.format(Locale.CHINA, "( %d )", size));
            }
        });

        //列表播放模式
        View playModeView = playlistView.findViewById(R.id.layout_playlist_play_mode);
        playModeIv = playlistView.findViewById(R.id.iv_playlist_play_mode);
        playModeTv = playlistView.findViewById(R.id.tv_playlist_play_mode);
        sPresenter.initPlayMode();
        playModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPresenter.changePlayMode();
            }
        });

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
                sPresenter.clearAdapter();
                mDialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing())
            mDialog.show();
    }

    public void destory() {
        //还要保存列表状态
        if (mDialog != null) {
            if (mDialog.isShowing())
                mDialog.dismiss();
            mDialog = null;
        }
        sPresenter = null;
        adapter = null;
    }

    @Override
    public void changePlayMode(String modeName, int modeIcon) {
        playModeIv.setImageResource(modeIcon);
        playModeTv.setText(modeName);
    }

}
