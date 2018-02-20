package fragment;

import android.view.View;

import com.spring_ballet.lovemusic.R;
import com.spring_ballet.lovemusic.RanklistActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import base.BaseFragment;
import utils.GetBannerImages;
import utils.GlideImageLoader;
import utils.IntentUtil;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/1/6.
 */

public class NetMusicFragment extends BaseFragment {
    private Banner mBanner;

    @Override
    protected void lazyLoad() {
        //轮播图
        mBanner = view.findViewById(R.id.net_music_banner);
        mBanner.setImageLoader(new GlideImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setImages(GetBannerImages.getImagesUrl())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ToastUtil.showShort(mContext, "mBanner: " + position);
                    }
                })
                .start();

        //加载成功后，设置为true避免下次重复加载
        hasLoaded = true;

        view.findViewById(R.id.layout_new_music_list).setOnClickListener(this);
        view.findViewById(R.id.layout_hot_music_list).setOnClickListener(this);
        view.findViewById(R.id.layout_popular_music_list).setOnClickListener(this);
        view.findViewById(R.id.layout_net_music_list).setOnClickListener(this);
        view.findViewById(R.id.layout_classical_music_list).setOnClickListener(this);
        view.findViewById(R.id.layout_movie_music_list).setOnClickListener(this);
        view.findViewById(R.id.layout_europe_music_list).setOnClickListener(this);
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.net_music_frag;
    }

    @Override
    public void onClick(View v) {
        int type = 1;
        switch (v.getId()) {
            case R.id.layout_new_music_list:
                type = 1;
                break;
            case R.id.layout_hot_music_list:
                type = 2;
                break;
            case R.id.layout_popular_music_list:
                type = 16;
                break;
            case R.id.layout_net_music_list:
                type = 25;
                break;
            case R.id.layout_classical_music_list:
                type = 22;
                break;
            case R.id.layout_movie_music_list:
                type = 24;
                break;
            case R.id.layout_europe_music_list:
                type = 21;
                break;
        }
        IntentUtil.gotoActivityWithData(mContext, RanklistActivity.class, "" + type);
    }
}
