package fragment;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spring_ballet.lovemusic.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import base.BaseFragment;
import bean.SingerInfoEvent;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerInfoFragment extends BaseFragment {

    private View loadingView;
    private AnimationDrawable drawable;
    private View bodyLayout;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSingerInfo(SingerInfoEvent event) {
        TextView tvSingerInfoDetail = view.findViewById(R.id.tv_singer_detail_info);
        tvSingerInfoDetail.setText(event.getInfo());
        if (drawable != null && drawable.isRunning()) {
            bodyLayout.setVisibility(View.VISIBLE);
            drawable.stop();
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
        hasLoaded = true;
        EventBus.getDefault().register(this);
        View singerInfoLayout = view.findViewById(R.id.layout_singer_info_divider);
        TextView tvSingerInfo = singerInfoLayout.findViewById(R.id.tv_divider_name);
        tvSingerInfo.setText("歌手简介");
        loadingView = view.findViewById(R.id.layout_loading);
        ImageView imageView = loadingView.findViewById(R.id.iv_loading);
        drawable = (AnimationDrawable) imageView.getBackground();
        if (drawable != null && !drawable.isRunning())
            drawable.start();
        bodyLayout = view.findViewById(R.id.layout_singer_info);
        if (drawable != null && !drawable.isRunning())
            drawable.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_singer_info;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
