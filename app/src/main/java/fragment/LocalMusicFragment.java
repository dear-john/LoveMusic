package fragment;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;
import utils.LogUtil;
import utils.ToastUtil;


public class LocalMusicFragment extends BaseFragment {
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
