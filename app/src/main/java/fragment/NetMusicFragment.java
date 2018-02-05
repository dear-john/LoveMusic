package fragment;

import com.spring_ballet.lovemusic.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import base.BaseFragment;
import utils.GetBannerImages;
import utils.GlideImageLoader;
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
}
