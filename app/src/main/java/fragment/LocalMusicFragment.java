package fragment;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;
import utils.LogUtil;


public class LocalMusicFragment extends BaseFragment {

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.logD("local setUserVisibleHint   "+isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.local_music_frag;
    }
}
