package fragment;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;


public class LocalMusicFragment extends BaseFragment {

    @Override
    protected void lazyLoad() {
        //加载成功后，设置为true避免下次重复加载
        hasLoaded=true;
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.local_music_frag;
    }
}
