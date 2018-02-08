package utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

/**
 * Created by 李君 on 2018/2/7.
 */

public class BottomDialogUtil {

    public static void showDialog(final Context context, String name, int commentNumber, String singer, String album, final DialogItemClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.BottomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.music_bottom_dialog, null);
        TextView nameTv = view.findViewById(R.id.tv_popupwin_music_name);
        nameTv.setText(String.format("歌曲: %s", name));
        View nextLayout = view.findViewById(R.id.layout_next_music);
        nextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.OnItemClickListener(1);
            }
        });
        View collectLayout = view.findViewById(R.id.layout_collect);
        collectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.OnItemClickListener(2);
            }
        });
        TextView commentTv = view.findViewById(R.id.tv_popupwin_comment);
        commentTv.setText(String.format("评论(%s)", String.valueOf(commentNumber)));
        commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.OnItemClickListener(3);
            }
        });
        View shareLayout = view.findViewById(R.id.layout_share);
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.OnItemClickListener(4);
            }
        });
        TextView singerTv = view.findViewById(R.id.tv_popupwin_singer);
        singerTv.setText(String.format("歌手: %s", singer));
        singerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.OnItemClickListener(5);
            }
        });
        TextView albumTv = view.findViewById(R.id.tv_popupwin_album);
        albumTv.setText(String.format("专辑: %s", album));
        albumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.OnItemClickListener(6);
            }
        });
        dialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //高度自适应，宽度设为屏幕宽度
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.BottomDialog_Animation);
        }
        //点击空白dialog消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public interface DialogItemClickListener {
        void OnItemClickListener(int index);
    }
}
