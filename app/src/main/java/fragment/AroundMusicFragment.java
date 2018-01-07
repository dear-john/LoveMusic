package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spring_ballet.lovemusic.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import base.BaseFragment;
import utils.LogUtil;


public class AroundMusicFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager aroundMusicVp;
    private static final int PAGE_NUM = 2;
    private List<BaseFragment> baseFragmentList;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.logD("around setUserVisibleHint   " + isVisibleToUser);
    }

    @Override
    protected void lazyLoad() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        tabLayout = view.findViewById(R.id.around_music_tablayout);
        aroundMusicVp = view.findViewById(R.id.around_viewpager);
        baseFragmentList = new ArrayList<>(PAGE_NUM);
        baseFragmentList.add(new NetMusicFragment());
        baseFragmentList.add(new VideoFragment());
        List<String> titles = new ArrayList<>(PAGE_NUM);
        titles.add("音乐");
        titles.add("视频");
        aroundMusicVp.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), baseFragmentList, titles));
        aroundMusicVp.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(aroundMusicVp);
        return view;
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.around_music_frag;
    }


}
