package fragment;

import android.view.View;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;


public class FriendFragment extends BaseFragment {

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
        return R.layout.friend_frag;
    }

    @Override
    public void onClick(View v) {

    }
}
