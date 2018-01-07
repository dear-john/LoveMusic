package fragment;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;
import utils.LogUtil;


public class FriendFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {
        LogUtil.logD("FriendFragment");
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.friend_frag;
    }
}
