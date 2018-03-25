package fragment;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;
import com.spring_ballet.lovemusic.SingerInfoActivity;

import base.BaseFragment;
import bean.SingerInfo;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerInfoFragment extends BaseFragment {

    private View loadingView;
    private AnimationDrawable drawable;
    private ImageView imageView;

    @Override
    protected void lazyLoad() {
        View singerInfoLayout = view.findViewById(R.id.layout_singer_info_divider);
        TextView tvSingerInfo = singerInfoLayout.findViewById(R.id.tv_divider_name);
        tvSingerInfo.setText("歌手简介");
        loadingView = view.findViewById(R.id.layout_loading);
        imageView = loadingView.findViewById(R.id.iv_loading);
        drawable = (AnimationDrawable) imageView.getBackground();
        if (drawable != null && !drawable.isRunning())
            drawable.start();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!hasLoaded && isVisibleToUser) {
            hasLoaded = true;
            View bodyLayout = view.findViewById(R.id.layout_singer_info);
            SingerInfo singerInfo = ((SingerInfoActivity) getActivity()).getSingerInfo();
            if (singerInfo != null) {
                String info = singerInfo.getIntro();
                if (TextUtils.isEmpty(info)) info = "暂无歌手简介";
                TextView tvSingerInfoDetail = view.findViewById(R.id.tv_singer_detail_info);
                tvSingerInfoDetail.setText(info);
                if (drawable != null && drawable.isRunning()) {
                    drawable.stop();
                    loadingView.setVisibility(View.GONE);
                    bodyLayout.setVisibility(View.VISIBLE);
                }
            } else onLoadFailed();
        }
    }

    private void onLoadFailed() {
        TextView textView = loadingView.findViewById(R.id.tv_loading);
        textView.setText(R.string.loading_failed);
        ToastUtil.showShort(mContext, getResources().getString(R.string.loading_failed));
        if (drawable != null && drawable.isRunning()) {
            drawable.stop();
            imageView.setVisibility(View.GONE);
        }
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
