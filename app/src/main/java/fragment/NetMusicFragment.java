package fragment;

import com.spring_ballet.lovemusic.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import base.BaseFragment;
import utils.GetBannerImages;
import utils.GlideImageLoader;
import utils.LogUtil;
import utils.ToastUtil;

/**
 * Created by 李君 on 2018/1/6.
 */

public class NetMusicFragment extends BaseFragment {
    private Banner banner;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.logD("netmusic setUserVisibleHint   "+isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {
        banner = view.findViewById(R.id.net_music_banner);
        banner.setImageLoader(new GlideImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setImages(GetBannerImages.getImagesUrl())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ToastUtil.toast(getActivity(), "banner: " + position);
                    }
                })
                .start();
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.net_music_frag;
    }
}
