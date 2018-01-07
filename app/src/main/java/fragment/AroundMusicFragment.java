package fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.spring_ballet.lovemusic.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import base.BaseFragment;


public class AroundMusicFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager aroundMusicVp;
    private static final int PAGE_NUM = 2;
    private List<BaseFragment> baseFragmentList;

    @Override
    protected void lazyLoad() {
        tabLayout = activity.findViewById(R.id.around_music_tablayout);
        aroundMusicVp = activity.findViewById(R.id.around_viewpager);
        baseFragmentList = new ArrayList<>(PAGE_NUM);
        baseFragmentList.add(new NetMusicFragment());
        baseFragmentList.add(new VideoFragment());
        List<String> titles = new ArrayList<>(PAGE_NUM);
        titles.add("音乐");
        titles.add("视频");
        aroundMusicVp.setAdapter(new MyFragmentAdapter(getChildFragmentManager(), baseFragmentList, titles));
        aroundMusicVp.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(aroundMusicVp);
    }


    @Override
    protected void refresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.around_music_frag;
    }


}
