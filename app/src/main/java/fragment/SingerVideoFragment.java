package fragment;

import android.view.View;

import com.spring_ballet.lovemusic.R;

import base.BaseFragment;

/**
 * Created by 李君 on 2018/2/23.
 */

public class SingerVideoFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {
        hasLoaded = true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_singer_video;
    }

    @Override
    public void onClick(View v) {

    }
}
