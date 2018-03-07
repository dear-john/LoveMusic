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
import com.spring_ballet.lovemusic.SingerInfoActivity;

/**
 * Created by 李君 on 2018/2/7.
 */

public class BottomDialogUtil implements View.OnClickListener {

    private Dialog mDialog;
    private Context mContext;
    private String mTingUid;
    private String mSinger;
    private String mName;

    public void showDialog(Context context, String name, int commentNumber, String singer, String album, String tingUid) {
        mContext = context;
        mSinger = singer;
        mTingUid = tingUid;
        mName = name;
        mDialog = new Dialog(context, R.style.BottomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.music_bottom_dialog, null);
        TextView nameTv = view.findViewById(R.id.tv_popupwin_music_name);
        nameTv.setText(String.format("歌曲: %s", name));
        View nextLayout = view.findViewById(R.id.layout_next_music);
        nextLayout.setOnClickListener(this);
        View collectLayout = view.findViewById(R.id.layout_collect);
        collectLayout.setOnClickListener(this);
        TextView commentTv = view.findViewById(R.id.tv_popupwin_comment);
        commentTv.setText(String.format("评论(%s)", String.valueOf(commentNumber)));
        View commentLayout = view.findViewById(R.id.layout_comment);
        commentLayout.setOnClickListener(this);
        View shareLayout = view.findViewById(R.id.layout_share);
        shareLayout.setOnClickListener(this);
        TextView singerTv = view.findViewById(R.id.tv_popupwin_singer);
        singerTv.setText(String.format("歌手: %s", singer));
        View singerLayout = view.findViewById(R.id.layout_singer);
        singerLayout.setOnClickListener(this);
        TextView albumTv = view.findViewById(R.id.tv_popupwin_album);
        albumTv.setText(String.format("专辑: %s", album));
        View albumLayout = view.findViewById(R.id.layout_album);
        albumLayout.setOnClickListener(this);
        mDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //高度自适应，宽度设为屏幕宽度
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
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

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        switch (v.getId()) {
            case R.id.layout_next_music:
                ToastUtil.showShort(mContext, "index 1");
                break;
            case R.id.layout_collect:
                ToastUtil.showShort(mContext, "index 2");
                break;
            case R.id.layout_comment:
                ToastUtil.showShort(mContext, "index 3");
                break;
            case R.id.layout_share:
                ShareUtil.share(mContext, "分享" + mSinger + "的单曲 " + mName + " (来自@"
                        + mContext.getResources().getString(R.string.app_name) + ")");
                break;
            case R.id.layout_singer:
                if (mTingUid.equals("local"))
                    ToastUtil.showShort(mContext, "local");
                else if (!mTingUid.equals("singer")) {
                    if (mSinger.contains(",")) {
                        final String[] nameList = mSinger.split(",");
                        final String[] idList = mTingUid.split(",");
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                                .setCancelable(true)
                                .setItems(nameList, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        IntentUtil.gotoActivityWithData(mContext, SingerInfoActivity.class, idList[which]);
                                    }
                                })
                                .setTitle("请选择要查看的歌手");
                        builder.show();
                    } else
                        IntentUtil.gotoActivityWithData(mContext, SingerInfoActivity.class, mTingUid);
                }
                break;
            case R.id.layout_album:
                ToastUtil.showShort(mContext, "index 6");
                break;
        }
    }
}
