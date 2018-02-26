package fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;
import com.spring_ballet.lovemusic.SingerInfoActivity;

import base.BaseFragment;
import bean.SingerInfo;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerInfoFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {
        hasLoaded = true;
        View singerInfoLayout = view.findViewById(R.id.layout_singer_info);
        TextView tvSingerInfo = singerInfoLayout.findViewById(R.id.tv_divider_name);
        tvSingerInfo.setText("歌手简介");
//        View familiarSingerLayout = view.findViewById(R.id.layout_familiar_singer);
//        TextView tvFamiliarSinger = familiarSingerLayout.findViewById(R.id.tv_divider_name);
//        tvFamiliarSinger.setText("相似歌手");
        SingerInfo singerInfo = ((SingerInfoActivity) getActivity()).getSingerInfo();
        TextView tvSingerInfoDetail = view.findViewById(R.id.tv_singer_detail_info);
        String info = singerInfo.getIntro();
        if (TextUtils.isEmpty(info)) info = "暂无歌手简介";
        tvSingerInfoDetail.setText(info);
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_singer_info;
    }

    @Override
    public void onClick(View v) {

    }
}
