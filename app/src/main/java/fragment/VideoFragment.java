package fragment;


import android.view.View;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;

/**
 * Created by 李君 on 2018/1/6.
 */

public class VideoFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {
        hasLoaded = true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_frag;
    }

    @Override
    public void onClick(View v) {

    }
}
