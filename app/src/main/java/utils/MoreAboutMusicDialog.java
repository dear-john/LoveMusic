package utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import Presenter.PlayListPresenter;
import adapter.MusicRecyclerViewAdapter;
import base.BaseActivity;
import bean.PlaylistItem;
import fragment.SingerIntroduceFragment;

/**
 * Created by 李君 on 2018/2/7.
 */

public class MoreAboutMusicDialog implements View.OnClickListener {

    private Dialog mDialog;
    private Context mContext;
    private View view;
    private String mTingUid;
    private String mSingerName;
    private String mSongName;
    private String mSongUrl;
    private String mSongIcon;

    public void showDialog(Context context, PlaylistItem item, int commentNumber, String albumName, String tingUid) {

        mContext = context;
        mSongName = item.getSongName();
        mSingerName = item.getSingerName();
        mSongUrl = item.getUrl();
        mSongIcon = item.getIcon();
        mTingUid = tingUid;

        initWidgetsAndSetListeners(commentNumber, albumName);

        mDialog = new Dialog(context, R.style.BottomDialog);
        mDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //宽度为屏幕宽度,高度自适应
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        Window window = mDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.BottomDialog_Animation);
        }
        //点击空白dialog消失
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    private void initWidgetsAndSetListeners(int commentNumber, String album) {

        //加载布局
        view = LayoutInflater.from(mContext).inflate(R.layout.music_bottom_dialog, null);

        //设置数据
        TextView nameTv = view.findViewById(R.id.tv_popupwin_music_name);
        nameTv.setText(String.format("歌曲: %s", mSongName));

        TextView commentTv = view.findViewById(R.id.tv_popupwin_comment);
        commentTv.setText(String.format("评论(%s)", String.valueOf(commentNumber)));

        TextView singerTv = view.findViewById(R.id.tv_popupwin_singer);
        singerTv.setText(String.format("歌手: %s", mSingerName));

        TextView albumTv = view.findViewById(R.id.tv_popupwin_album);
        albumTv.setText(String.format("专辑: %s", album));

        //设置监听
        View nextLayout = view.findViewById(R.id.layout_next_music);
        nextLayout.setOnClickListener(this);

        View collectLayout = view.findViewById(R.id.layout_collect);
        collectLayout.setOnClickListener(this);

        View commentLayout = view.findViewById(R.id.layout_comment);
        commentLayout.setOnClickListener(this);

        View shareLayout = view.findViewById(R.id.layout_share);
        shareLayout.setOnClickListener(this);

        View singerLayout = view.findViewById(R.id.layout_singer);
        singerLayout.setOnClickListener(this);

        View albumLayout = view.findViewById(R.id.layout_album);
        albumLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        switch (v.getId()) {
            case R.id.layout_next_music:
                PlayListPresenter.addItemAsNext(new PlaylistItem(mSongName, mSingerName, mSongUrl, mSongIcon));
                break;
            case R.id.layout_collect:
                ToastUtil.showShort(mContext, "index 2");
                break;
            case R.id.layout_comment:
                ToastUtil.showShort(mContext, "index 3");
                break;
            case R.id.layout_share:
                ShareUtil.share(mContext, "分享" + mSingerName + "的单曲 " + mSongName + " (来自@"
                        + mContext.getResources().getString(R.string.app_name) + ")");
                break;
            case R.id.layout_singer:
                if (mTingUid.equals(MusicRecyclerViewAdapter.LOCAL_MUSIC_TING_UID))
                    ToastUtil.showShort(mContext, "local");
                else if (!mTingUid.equals("singer")) {
                    if (mSingerName.contains(",")) {
                        final String[] nameList = mSingerName.split(",");
                        final String[] idList = mTingUid.split(",");
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                                .setCancelable(true)
                                .setItems(nameList, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((BaseActivity) mContext).showNewView(SingerIntroduceFragment.newInstance(idList[which]));
                                    }
                                })
                                .setTitle("请选择要查看的歌手");
                        builder.show();
                    } else
                        ((BaseActivity) mContext).showNewView(SingerIntroduceFragment.newInstance(mTingUid));
                }
                break;
            case R.id.layout_album:
                ToastUtil.showShort(mContext, "index 6");
                break;
        }
    }
}
