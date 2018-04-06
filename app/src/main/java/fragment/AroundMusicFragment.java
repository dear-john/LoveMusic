package fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.spring_ballet.lovemusic.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentAdapter;
import base.BaseFragment;


public class AroundMusicFragment extends BaseFragment {

    private static final int PAGE_NUM = 2;

    @Override
    protected void lazyLoad() {
        TabLayout tabLayout = view.findViewById(R.id.around_music_tablayout);
        ViewPager aroundMusicVp = view.findViewById(R.id.around_viewpager);
        List<BaseFragment> baseFragmentList = new ArrayList<>(PAGE_NUM);
        baseFragmentList.add(new NetMusicFragment());
        baseFragmentList.add(new VideoFragment());
        List<String> titles = new ArrayList<>(PAGE_NUM);
        titles.add("音乐");
        titles.add("视频");
        aroundMusicVp.setAdapter(new MyFragmentAdapter(getFragmentManager(), baseFragmentList, titles));
        aroundMusicVp.setOffscreenPageLimit(2);
        aroundMusicVp.setCurrentItem(0);
        tabLayout.setupWithViewPager(aroundMusicVp);
        //加载成功后，设置为true避免下次重复加载
        hasLoaded = true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.around_music_frag;
    }


    @Override
    public void onClick(View v) {

    }
}
